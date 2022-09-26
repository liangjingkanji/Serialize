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

import androidx.lifecycle.MutableLiveData
import com.tencent.mmkv.MMKV
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 其修饰的属性字段的读写都会自动映射到本地磁盘
 * 线程安全
 * 本函数属于阻塞函数, 同步读写磁盘
 * @param default 默认值
 * @param name 键名, 默认使用 "当前全路径类名.字段名", 顶层字段没有类名. 全路径类名即: 包名+类名. 请注意重命名包名/类名/字段名都会导致无法读取旧值
 * @throws NullPointerException 字段如果属于不可空, 但是读取本地失败会导致抛出异常
 * @throws IllegalStateException MMKV.defaultMMKV() == null
 */
inline fun <reified V> serial(
    default: V? = null,
    name: String? = null,
    kv: MMKV = MMKV.defaultMMKV()
        ?: throw IllegalStateException("MMKV.defaultMMKV() == null, handle == 0 "),
): ReadWriteProperty<Any, V> = SerialDelegate(default, V::class.java, name, kv)

/**
 * 默认值采用函数传递进来，但是类型也需要外部传入
 *
 * @param V
 * @param clazz
 * @param name
 * @param kv
 * @param default
 * @return
 *
 * @see serial
 */
fun <V> serial(
    clazz: Class<V>,
    name: String? = null,
    kv: MMKV = MMKV.defaultMMKV()
        ?: throw IllegalStateException("MMKV.defaultMMKV() == null, handle == 0 "),
    default: (() -> V)? = null,
): ReadWriteProperty<Any, V> = SerialDelegate2(default, clazz, name, kv)

/**
 * 可观察的数据来源[MutableLiveData]
 * 其修饰的属性字段的读写都会自动映射到本地磁盘
 * 和[serial]不同的是通过内存/磁盘双通道读写来优化读写性能
 * 其修饰的属性字段第一次会读取磁盘数据, 然后拷贝到内存中, 后续都是直接读取内存中的拷贝
 * 写入会优先写入到内存中的拷贝份, 然后通过子线程异步写入到磁盘
 * 线程安全
 * tip: 不支持跨进程使用
 *
 * @param default 默认值, 默认值会在订阅时触发一次
 * @param name 键名, 默认使用 "当前全路径类名.字段名", 顶层字段没有类名. 全路径类名即: 包名+类名. 请注意重命名包名/类名/字段名都会导致无法读取旧值
 * @throws NullPointerException 字段如果属于不可空, 但是读取本地失败会导致抛出异常
 * @throws IllegalStateException MMKV.defaultMMKV() == null
 */
inline fun <reified V> serialLiveData(
    default: V? = null,
    name: String? = null,
    kv: MMKV = MMKV.defaultMMKV()
        ?: throw IllegalStateException("MMKV.defaultMMKV() == null, handle == 0 "),
): ReadOnlyProperty<Any, MutableLiveData<V>> = SerializeLiveDataDelegate(default, V::class.java, name, kv)

/**
 * 默认值采用函数传递进来，但是类型也需要外部传入
 *
 * @param V
 * @param clazz
 * @param name
 * @param kv
 * @param default
 * @return
 *
 * @see serialLiveData
 */
fun <V> serialLiveData(
    clazz: Class<V>,
    name: String? = null,
    kv: MMKV = MMKV.defaultMMKV()
        ?: throw IllegalStateException("MMKV.defaultMMKV() == null, handle == 0 "),
    default: (() -> V)? = null,
): ReadOnlyProperty<Any, MutableLiveData<V>> = SerializeLiveDataDelegate2(default, clazz, name, kv)

/**
 * 其修饰的属性字段的读写都会自动映射到本地磁盘
 * 和[serial]不同的是通过内存/磁盘双通道读写来优化读写性能
 * 其修饰的属性字段第一次会读取磁盘数据, 然后拷贝到内存中, 后续都是直接读取内存中的拷贝
 * 写入会优先写入到内存中的拷贝份, 然后通过子线程异步写入到磁盘
 * 线程安全
 * tip: 不支持跨进程使用
 *
 * @param default 默认值
 * @param name 键名, 默认使用 "当前全路径类名.字段名", 顶层字段没有类名. 全路径类名即: 包名+类名. 请注意重命名包名/类名/字段名都会导致无法读取旧值
 * @throws NullPointerException 字段如果属于不可空, 但是读取本地失败会导致抛出异常
 * @throws IllegalStateException MMKV.defaultMMKV() == null
 */
inline fun <reified V> serialLazy(
    default: V? = null,
    name: String? = null,
    kv: MMKV = MMKV.defaultMMKV()
        ?: throw IllegalStateException("MMKV.defaultMMKV() == null, handle == 0 "),
): ReadWriteProperty<Any, V> = SerialLazyDelegate(default, V::class.java, name, kv)

/**
 * 默认值采用函数传递进来，但是类型也需要外部传入
 *
 * @param V
 * @param clazz
 * @param name
 * @param kv
 * @param default
 * @return
 *
 * @see serialLazy
 */
fun <V> serialLazy(
    clazz: Class<V>,
    name: String? = null,
    kv: MMKV = MMKV.defaultMMKV()
        ?: throw IllegalStateException("MMKV.defaultMMKV() == null, handle == 0 "),
    default: (() -> V)? = null,
): ReadWriteProperty<Any, V> = SerialLazyDelegate2(default, clazz, name, kv)

/**
 * 构建自动映射到本地磁盘的委托属性
 * @see serial
 */
@PublishedApi
internal class SerialDelegate<V>(
    private val default: V?,
    private val clazz: Class<V>,
    private val name: String?,
    private val kv: MMKV,
) : ReadWriteProperty<Any, V> {

    override fun getValue(thisRef: Any, property: KProperty<*>): V {
        val key = "${thisRef.javaClass.name}.${name ?: property.name}"
        return if (default == null) {
            kv.deserialize(key, clazz)
        } else {
            kv.deserialize(key, clazz, default)
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: V) {
        val key = "${thisRef.javaClass.name}.${name ?: property.name}"
        kv.serialize(key to value)
    }
}

@PublishedApi
internal class SerialDelegate2<V>(
    private val default: (() -> V)?,
    private val clazz: Class<V>,
    private val name: String?,
    private val kv: MMKV,
) : ReadWriteProperty<Any, V> {

    override fun getValue(thisRef: Any, property: KProperty<*>): V {
        val key = "${thisRef.javaClass.name}.${name ?: property.name}"
        return if (default == null) {
            kv.deserialize(key, clazz)
        } else {
            kv.deserialize(key, clazz, default)
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: V) {
        val key = "${thisRef.javaClass.name}.${name ?: property.name}"
        kv.serialize(key to value)
    }
}

/**
 * 构建自动映射到本地磁盘的可观察数据[MutableLiveData]委托属性
 * 内存/磁盘双通道读写
 * @see serialLazy
 */
@PublishedApi
internal class SerializeLiveDataDelegate<V>(
    private val default: V?,
    private val clazz: Class<V>,
    private val name: String?,
    private val kv: MMKV,
) : ReadOnlyProperty<Any, MutableLiveData<V>>, MutableLiveData<V>() {

    private lateinit var thisRef: Any
    private lateinit var property: KProperty<*>

    override fun getValue(): V? = synchronized(this) {
        var value = super.getValue()
        if (value == null) {
            val key = "${thisRef.javaClass.name}.${name ?: property.name}"
            value = if (default == null) {
                kv.deserialize(key, clazz)
            } else {
                kv.deserialize(key, clazz, default)
            }
        }
        value
    }

    override fun getValue(
        thisRef: Any,
        property: KProperty<*>,
    ): MutableLiveData<V> = synchronized(this) {
        this.thisRef = thisRef
        this.property = property
        val value = value
        if (super.getValue() == null && value != null) {
            super.setValue(value)
        }
        this
    }

    override fun setValue(value: V) {
        super.setValue(value)
        asyncSerialize(value)
    }

    override fun postValue(value: V) {
        super.postValue(value)
        asyncSerialize(value)
    }

    /** 写入本地在子线程处理，单一线程保证了写入顺序 */
    private fun asyncSerialize(value: V) {
        taskExecutor.execute {
            val key = "${thisRef.javaClass.name}.${name ?: property.name}"
            kv.serialize(key to value)
        }
    }

    companion object {
        /** 单一线程 无界队列  保证任务按照提交顺序来执行 **/
        private val taskExecutor = Executors.newSingleThreadExecutor(ThreadFactory {
            val thread = Thread(it)
            thread.name = "SerializeLiveDataDelegate"
            return@ThreadFactory thread
        })
    }
}

@PublishedApi
internal class SerializeLiveDataDelegate2<V>(
    private val default: (() -> V)?,
    private val clazz: Class<V>,
    private val name: String?,
    private val kv: MMKV,
) : ReadOnlyProperty<Any, MutableLiveData<V>>, MutableLiveData<V>() {

    private lateinit var thisRef: Any
    private lateinit var property: KProperty<*>

    override fun getValue(): V? = synchronized(this) {
        var value = super.getValue()
        if (value == null) {
            val key = "${thisRef.javaClass.name}.${name ?: property.name}"
            value = if (default == null) {
                kv.deserialize(key, clazz)
            } else {
                kv.deserialize(key, clazz, default)
            }
        }
        value
    }

    override fun getValue(
        thisRef: Any,
        property: KProperty<*>,
    ): MutableLiveData<V> = synchronized(this) {
        this.thisRef = thisRef
        this.property = property
        val value = value
        if (super.getValue() == null && value != null) {
            super.setValue(value)
        }
        this
    }

    override fun setValue(value: V) {
        super.setValue(value)
        asyncSerialize(value)
    }

    override fun postValue(value: V) {
        super.postValue(value)
        asyncSerialize(value)
    }

    /** 写入本地在子线程处理，单一线程保证了写入顺序 */
    private fun asyncSerialize(value: V) {
        taskExecutor.execute {
            val key = "${thisRef.javaClass.name}.${name ?: property.name}"
            kv.serialize(key to value)
        }
    }

    companion object {
        /** 单一线程 无界队列  保证任务按照提交顺序来执行 **/
        private val taskExecutor = Executors.newSingleThreadExecutor(ThreadFactory {
            val thread = Thread(it)
            thread.name = "SerializeLiveDataDelegate"
            return@ThreadFactory thread
        })
    }
}

/**
 * 构建自动映射到本地磁盘的委托属性
 * 内存/磁盘双通道读写
 * @see serialLazy
 */
@PublishedApi
internal class SerialLazyDelegate<V>(
    private val default: V?,
    private val clazz: Class<V>,
    private val name: String?,
    private val kv: MMKV,
) : ReadWriteProperty<Any, V> {
    @Volatile
    private var value: V? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): V = synchronized(this) {
        if (value == null) {
            value = run {
                val key = "${thisRef.javaClass.name}.${name ?: property.name}"
                if (default == null) {
                    kv.deserialize(key, clazz)
                } else {
                    kv.deserialize(key, clazz, default)
                }
            }
        }
        value as V
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: V) {
        this.value = value
        //写入本地在子线程处理，单一线程保证了写入顺序
        taskExecutor.execute {
            val key = "${thisRef.javaClass.name}.${name ?: property.name}"
            kv.serialize(key to value)
        }
    }

    companion object {
        /** 单一线程 无界队列  保证任务按照提交顺序来执行 **/
        private val taskExecutor = Executors.newSingleThreadExecutor(ThreadFactory {
            val thread = Thread(it)
            thread.name = "SerialLazyDelegate"
            return@ThreadFactory thread
        })
    }

}

@PublishedApi
internal class SerialLazyDelegate2<V>(
    private val default: (() -> V)?,
    private val clazz: Class<V>,
    private val name: String?,
    private val kv: MMKV,
) : ReadWriteProperty<Any, V> {
    @Volatile
    private var value: V? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): V = synchronized(this) {
        if (value == null) {
            value = run {
                val key = "${thisRef.javaClass.name}.${name ?: property.name}"
                if (default == null) {
                    kv.deserialize(key, clazz)
                } else {
                    kv.deserialize(key, clazz, default)
                }
            }
        }
        value as V
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: V) {
        this.value = value
        //写入本地在子线程处理，单一线程保证了写入顺序
        taskExecutor.execute {
            val key = "${thisRef.javaClass.name}.${name ?: property.name}"
            kv.serialize(key to value)
        }
    }

    companion object {
        /** 单一线程 无界队列  保证任务按照提交顺序来执行 **/
        private val taskExecutor = Executors.newSingleThreadExecutor(ThreadFactory {
            val thread = Thread(it)
            thread.name = "SerialLazyDelegate"
            return@ThreadFactory thread
        })
    }

}