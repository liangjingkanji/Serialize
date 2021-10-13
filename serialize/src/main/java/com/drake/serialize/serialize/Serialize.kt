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

import android.os.Parcelable
import com.tencent.mmkv.MMKV
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

//<editor-fold desc="写入">
fun serialize(vararg params: Pair<String, Any?>) = defaultMMKV().serialize(*params)

fun MMKV.serialize(vararg params: Pair<String, Any?>) {
    params.forEach {
        when (val value = it.second) {
            null -> remove(it.first)
            is Parcelable -> encode(it.first, value)
            else -> encode(it.first, value)
        }
        return@forEach
    }
}
//</editor-fold>

//<editor-fold desc="读取">

inline fun <reified T> deserialize(name: String): T =
    defaultMMKV().deserialize(name, T::class.java)

inline fun <reified T> deserialize(name: String, defValue: T?): T =
    defaultMMKV().deserialize(name, T::class.java, defValue)

inline fun <reified T> MMKV.deserialize(name: String): T = deserialize(name, T::class.java)

inline fun <reified T> MMKV.deserialize(name: String, defValue: T?): T =
    deserialize(name, T::class.java, defValue)

@PublishedApi
internal fun <T> MMKV.deserialize(name: String, clazz: Class<T>): T {
    return when {
        Parcelable::class.java.isAssignableFrom(clazz) -> {
            decodeParcelable(name, clazz as Class<Parcelable>) as? T
        }
        else -> decode<T>(name)
    } ?: null as T
}

@PublishedApi
internal fun <T> MMKV.deserialize(name: String, clazz: Class<T>, defValue: T?): T {
    return when {
        Parcelable::class.java.isAssignableFrom(clazz) -> {
            decodeParcelable(
                name, clazz as Class<Parcelable>,
                defValue as Parcelable
            ) as? T
        }
        else -> decode(name, defValue)
    } ?: null as T
}
//</editor-fold>

//<editor-fold desc="对象">
private fun MMKV.encode(name: String, obj: Any?) {
    if (obj == null) {
        remove(name)
        return
    }
    try {
        ByteArrayOutputStream().use { byteOutput ->
            ObjectOutputStream(byteOutput).use { objOutput ->
                objOutput.writeObject(obj)
                encode(name, byteOutput.toByteArray())
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

private fun <T> MMKV.decode(name: String): T? {
    val bytes = decodeBytes(name) ?: return null
    return try {
        var obj: Any?
        ByteArrayInputStream(bytes).use { byteInput ->
            ObjectInputStream(byteInput).use { objInput ->
                obj = objInput.readObject()
            }
        }
        obj as? T
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

private fun <T> MMKV.decode(name: String, defValue: T): T {
    val bytes = decodeBytes(name) ?: return defValue
    return try {
        var obj: Any?
        ByteArrayInputStream(bytes).use { byteInput ->
            ObjectInputStream(byteInput).use { objInput ->
                obj = objInput.readObject()
            }
        }
        obj as T
    } catch (e: Exception) {
        defValue
    }
}
//</editor-fold>

/**
 * 框架已经自动初始化MMKV，一般不会抛出异常
 */
fun defaultMMKV(): MMKV =
    MMKV.defaultMMKV() ?: throw IllegalStateException("MMKV.defaultMMKV() == null, handle == 0 ")