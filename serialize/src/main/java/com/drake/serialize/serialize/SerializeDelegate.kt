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
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 自动写入和读取自本地
 * 线程安全
 * @param default 默认值
 * @param name 键名, 默认使用 "当前类名.字段名", 顶层字段没有类名
 * @param kv MMKV实例
 * @throws NullPointerException 字段如果属于不可空, 但是读取本地失败会导致抛出异常
 */
inline fun <reified V> serial(
    default: V? = null,
    name: String? = null,
    kv: MMKV? = null
) = object : ReadWriteProperty<Any?, V> {

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        val className = thisRef?.javaClass?.name
        var adjustKey = name ?: property.name
        if (className != null) adjustKey = "${className}.${adjustKey}"
        serialize(kv, adjustKey to value)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): V {
        val className = thisRef?.javaClass?.name
        var adjustKey = name ?: property.name
        if (className != null) adjustKey = "${className}.${adjustKey}"
        return if (default == null) {
            deserialize(adjustKey, kv)
        } else {
            deserialize<V>(adjustKey, default, kv)
        }
    }
}

/**
 * 自动写入和读取自本地
 * 写入即时, 读取属于懒加载形式, 即不会每次读取都读取自本地, 当存在缓存值即返回不存在则读取本地数据
 * 线程安全
 * @param default 默认值
 * @param name 键名, 默认使用 "当前类名.字段名", 顶层字段没有类名
 * @param kv MMKV实例
 * @throws NullPointerException 字段如果属于不可空, 但是读取本地失败会导致抛出异常
 */
@Suppress("UNREACHABLE_CODE")
inline fun <reified V> serialLazy(
    default: V? = null,
    name: String? = null,
    kv: MMKV? = null
) = object : ReadWriteProperty<Any?, V> {
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
                        deserialize(adjustKey, kv)
                    } else {
                        deserialize<V>(adjustKey, default, kv)
                    }
                }
                value as V
            } else value as V
        }
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        this.value = value
        val className = thisRef?.javaClass?.name
        var adjustKey = name ?: property.name
        if (className != null) adjustKey = "${className}.${adjustKey}"
        serialize(kv, adjustKey to value)
    }
}