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
import com.drake.serialize.serialize.annotation.SerializeConfig
import com.drake.serialize.serialize.delegate.SerialDelegate
import com.drake.serialize.serialize.delegate.SerialLazyDelegate
import com.drake.serialize.serialize.delegate.SerializeLiveDataDelegate
import com.tencent.mmkv.MMKV
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty

/**
 * 其修饰的属性字段的读写都会自动映射到本地磁盘
 * 线程安全
 * 本函数属于阻塞函数, 同步读写磁盘
 * @param default 默认值
 * @param name 指定字段名, 最终存储key为`配置名称.字段名`, 配置名称默认为当前全路径类名([SerializeConfig]注解可以指定配置名称), 例如 `com.example.Class.field`
 * 请注意如果完全不指定配置名称或字段名情况下重命名包/类/字段名称会导致无法读取旧值
 * @throws NullPointerException 字段如果属于不可空, 但是读取本地失败会导致抛出异常
 */
inline fun <reified V> serial(
    default: V? = null,
    name: String? = null,
    kv: MMKV? = null
): ReadWriteProperty<Any, V> = SerialDelegate(default, V::class.java, { name }, kv)


/**
 * 其修饰的属性字段的读写都会自动映射到本地磁盘
 * 线程安全
 * 本函数属于阻塞函数, 同步读写磁盘
 * @param default 默认值
 * @param name 指定字段名, 最终存储key为`配置名称.字段名`, 配置名称默认为当前全路径类名([SerializeConfig]注解可以指定配置名称), 例如 `com.example.Class.field`
 * 请注意如果完全不指定配置名称或字段名情况下重命名包/类/字段名称会导致无法读取旧值
 * @throws NullPointerException 字段如果属于不可空, 但是读取本地失败会导致抛出异常
 */
inline fun <reified V> serial(
    default: V? = null,
    noinline name: () -> String?,
    kv: MMKV? = null
): ReadWriteProperty<Any, V> = SerialDelegate(default, V::class.java, name, kv)

/**
 * 可观察的数据来源[MutableLiveData]
 * 其修饰的属性字段的读写都会自动映射到本地磁盘
 * 和[serial]不同的是通过内存/磁盘双通道读写来优化读写性能
 * 其修饰的属性字段第一次会读取磁盘数据, 然后拷贝到内存中, 后续都是直接读取内存中的拷贝
 * 写入会优先写入到内存中的拷贝份, 然后通过子线程异步写入到磁盘
 * 线程安全
 *
 * @param default 默认值, 默认值会在订阅时触发一次
 * @param name 指定字段名, 最终存储key为`配置名称.字段名`, 配置名称默认为当前全路径类名([SerializeConfig]注解可以指定配置名称), 例如 `com.example.Class.field`
 * 请注意如果完全不指定配置名称或字段名情况下重命名包/类/字段名称会导致无法读取旧值
 * @throws NullPointerException 字段如果属于不可空, 但是读取本地失败会导致抛出异常
 */
inline fun <reified V> serialLiveData(
    default: V? = null,
    name: String? = null,
    kv: MMKV? = null
): ReadOnlyProperty<Any, MutableLiveData<V>> = SerializeLiveDataDelegate(default, V::class.java, { name }, kv)

/**
 * 可观察的数据来源[MutableLiveData]
 * 其修饰的属性字段的读写都会自动映射到本地磁盘
 * 和[serial]不同的是通过内存/磁盘双通道读写来优化读写性能
 * 其修饰的属性字段第一次会读取磁盘数据, 然后拷贝到内存中, 后续都是直接读取内存中的拷贝
 * 写入会优先写入到内存中的拷贝份, 然后通过子线程异步写入到磁盘
 * 线程安全
 *
 * @param default 默认值, 默认值会在订阅时触发一次
 * @param name 指定字段名, 最终存储key为`配置名称.字段名`, 配置名称默认为当前全路径类名([SerializeConfig]注解可以指定配置名称), 例如 `com.example.Class.field`
 * 请注意如果完全不指定配置名称或字段名情况下重命名包/类/字段名称会导致无法读取旧值
 * @throws NullPointerException 字段如果属于不可空, 但是读取本地失败会导致抛出异常
 */
inline fun <reified V> serialLiveData(
    default: V? = null,
    noinline name: () -> String?,
    kv: MMKV? = null
): ReadOnlyProperty<Any, MutableLiveData<V>> = SerializeLiveDataDelegate(default, V::class.java, name, kv)

/**
 * 其修饰的属性字段的读写都会自动映射到本地磁盘
 * 和[serial]不同的是通过内存/磁盘双通道读写来优化读写性能
 * 其修饰的属性字段第一次会读取磁盘数据, 然后拷贝到内存中, 后续都是直接读取内存中的拷贝
 * 写入会优先写入到内存中的拷贝份, 然后通过子线程异步写入到磁盘
 * 线程安全
 *
 * @param default 默认值
 * @param name 指定字段名, 最终存储key为`配置名称.字段名`, 配置名称默认为当前全路径类名([SerializeConfig]注解可以指定配置名称), 例如 `com.example.Class.field`
 * 请注意如果完全不指定配置名称或字段名情况下重命名包/类/字段名称会导致无法读取旧值
 * @throws NullPointerException 字段如果属于不可空, 但是读取本地失败会导致抛出异常
 */
inline fun <reified V> serialLazy(
    default: V? = null,
    name: String? = null,
    kv: MMKV? = null
): ReadWriteProperty<Any, V> = SerialLazyDelegate(default, V::class.java, { name }, kv)

/**
 * 其修饰的属性字段的读写都会自动映射到本地磁盘
 * 和[serial]不同的是通过内存/磁盘双通道读写来优化读写性能
 * 其修饰的属性字段第一次会读取磁盘数据, 然后拷贝到内存中, 后续都是直接读取内存中的拷贝
 * 写入会优先写入到内存中的拷贝份, 然后通过子线程异步写入到磁盘
 * 线程安全
 *
 * @param default 默认值
 * @param name 指定字段名, 最终存储key为`配置名称.字段名`, 配置名称默认为当前全路径类名([SerializeConfig]注解可以指定配置名称), 例如 `com.example.Class.field`
 * 请注意如果完全不指定配置名称或字段名情况下重命名包/类/字段名称会导致无法读取旧值
 * @throws NullPointerException 字段如果属于不可空, 但是读取本地失败会导致抛出异常
 */
inline fun <reified V> serialLazy(
    default: V? = null,
    noinline name: () -> String?,
    kv: MMKV? = null
): ReadWriteProperty<Any, V> = SerialLazyDelegate(default, V::class.java, name, kv)

