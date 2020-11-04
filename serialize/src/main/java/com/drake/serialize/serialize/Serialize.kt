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
fun serialize(vararg params: Pair<String, Any?>) = serialize(null, *params)

fun serialize(kv: MMKV? = null, vararg params: Pair<String, Any?>) {
    val serialize = kv ?: MMKV.defaultMMKV()
    params.forEach {
        when (val value = it.second) {
            null -> serialize.remove(it.first)
            is Parcelable -> serialize.encode(it.first, value)
            else -> serialize.encode(it.first, value)
        }
        return@forEach
    }
}
//</editor-fold>

//<editor-fold desc="读取">
inline fun <reified T> deserialize(name: String, kv: MMKV? = null): T {
    val serialize = kv ?: MMKV.defaultMMKV()
    return when {
        Parcelable::class.java.isAssignableFrom(T::class.java) -> {
            serialize.decodeParcelable(name, T::class.java as Class<Parcelable>) as? T
        }
        else -> serialize.decode<T>(name)
    } ?: null as T
}

inline fun <reified T> deserialize(
    name: String,
    defValue: T?,
    kv: MMKV? = null
): T {
    val serialize = kv ?: MMKV.defaultMMKV()

    return when {
               Parcelable::class.java.isAssignableFrom(T::class.java) -> {
                   serialize.decodeParcelable(name, T::class.java as Class<Parcelable>, defValue as Parcelable) as? T
               }
               else -> serialize.decode(name, defValue)
           } ?: null as T
}
//</editor-fold>

//<editor-fold desc="对象">
fun MMKV.encode(name: String, obj: Any?) {
    if (obj == null) {
        remove(name)
        return
    }
    try {
        val byteOutput = ByteArrayOutputStream()
        val objOutput = ObjectOutputStream(byteOutput)
        objOutput.writeObject(obj)
        encode(name, byteOutput.toByteArray())
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

inline fun <reified T> MMKV.decode(name: String): T? {
    return try {
        val bytes = decodeBytes(name) ?: return null
        val byteInput = ByteArrayInputStream(bytes)
        val objInput = ObjectInputStream(byteInput)
        val obj = objInput.readObject()
        obj as? T
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

inline fun <reified T> MMKV.decode(name: String, defValue: T): T {
    return try {
        val bytes = decodeBytes(name) ?: return defValue
        val byteInput = ByteArrayInputStream(bytes)
        val objInput = ObjectInputStream(byteInput)
        val obj = objInput.readObject()
        obj as T
    } catch (e: Exception) {
        defValue
    }
}
//</editor-fold>