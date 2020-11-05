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
 * @param default 默认值
 * @param name 键名, 默认使用 "当前类名.字段名", 顶层字段没有类名
 * @param kv MMKV实例
 */
inline fun <reified T> serial(
    default: T? = null,
    name: String? = null,
    kv: MMKV? = null
) = object : ReadWriteProperty<Any?, T> {

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        val className = thisRef?.javaClass?.name
        var adjustKey = name ?: property.name
        if (className != null) adjustKey = "${className}.${adjustKey}"
        serialize(kv, adjustKey to value)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val className = thisRef?.javaClass?.name
        var adjustKey = name ?: property.name
        if (className != null) adjustKey = "${className}.${adjustKey}"
        return if (default == null) {
            deserialize(adjustKey, kv)
        } else {
            deserialize<T>(adjustKey, default, kv)
        }
    }
}