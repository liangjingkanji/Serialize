/*
 * Copyright (C) 2018 Drake, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.drake.serialize.serialize

import com.tencent.mmkv.MMKV
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @param default 默认值
 * @param name 键名, 默认使用 "当前类名.字段名", 顶层字段没有类名
 * @throws NullPointerException 字段如果属于不可空, 但是读取本地失败会导致抛出异常
 * @throws IllegalStateException MMKV.defaultMMKV() == null
 */
inline fun <reified V> serial(
    default: V? = null,
    name: String? = null,
    kv: MMKV = MMKV.defaultMMKV()
        ?: throw IllegalStateException("MMKV.defaultMMKV() == null, handle == 0 ")
): ReadWriteProperty<Any?, V> = SerialDelegate(default, V::class.java, name, kv)

/**
 * @param default 默认值
 * @param name 键名, 默认使用 "当前类名.字段名", 顶层字段没有类名
 * @throws NullPointerException 字段如果属于不可空, 但是读取本地失败会导致抛出异常
 * @throws IllegalStateException MMKV.defaultMMKV() == null
 */
inline fun <reified V> serialLazy(
    default: V? = null,
    name: String? = null,
    kv: MMKV = MMKV.defaultMMKV()
        ?: throw IllegalStateException("MMKV.defaultMMKV() == null, handle == 0 ")
): ReadWriteProperty<Any?, V> = SerialLazyDelegate(default, V::class.java, name, kv)

/**
 * 自动写入和读取自本地  线程安全
 * 精简了inline代码量
 */
@PublishedApi
internal class SerialDelegate<V>(
    private val default: V?,
    private val clazz: Class<V>,
    private val name: String?,
    private val kv: MMKV
) : ReadWriteProperty<Any?, V> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): V {
        val className = thisRef?.javaClass?.name
        var adjustKey = name ?: property.name
        if (className != null) adjustKey = "${className}.${adjustKey}"
        return if (default == null) {
            kv.deserialize(adjustKey, clazz)
        } else {
            kv.deserialize<V>(adjustKey, clazz, default)
        }
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        val className = thisRef?.javaClass?.name
        var adjustKey = name ?: property.name
        if (className != null) adjustKey = "${className}.${adjustKey}"
        kv.serialize(adjustKey to value)
    }

}

/**
 * 自动写入和读取自本地  线程安全
 * 多了一层memory cache，优化了读取速度，写入磁盘操作在子线程进行
 * 精简了inline代码量
 */
@PublishedApi
internal class SerialLazyDelegate<V>(
    private val default: V?,
    private val clazz: Class<V>,
    private val name: String?,
    private val kv: MMKV
) : ReadWriteProperty<Any?, V> {
    @Volatile
    private var value: V? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): V {
        return synchronized(this) {
            if (value == null) {
                value = run {
                    val className = thisRef?.javaClass?.name
                    var adjustKey = name ?: property.name
                    if (className != null) adjustKey = "${className}.${adjustKey}"
                    if (default == null) {
                        kv.deserialize(adjustKey, clazz)
                    } else {
                        kv.deserialize(adjustKey, clazz, default)
                    }
                }
                value as V
            } else value as V
        }
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        this.value = value
        //写入本地在子线程处理，单一线程保证了写入顺序
        taskExecutor.execute {
            val className = thisRef?.javaClass?.name
            var adjustKey = name ?: property.name
            if (className != null) adjustKey = "${className}.${adjustKey}"
            kv.serialize(adjustKey to value)
        }
    }

    companion object {
        /** 单一线程 无界队列  保证任务按照提交顺序来执行 **/
        private val taskExecutor = Executors.newSingleThreadExecutor(ThreadFactory {
            val thread = Thread(it)
            thread.name = "Thread for MMKV encode()"
            return@ThreadFactory thread
        })
    }

}