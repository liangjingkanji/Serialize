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
/**
 * 将键值数据序列化存储到磁盘
 * @throws IllegalStateException MMKV.defaultMMKV() == null
 */
fun serialize(vararg params: Pair<String, Any?>) {
    val mmkv = MMKV.defaultMMKV()
        ?: throw IllegalStateException("MMKV.defaultMMKV() == null, handle == 0 ")
    mmkv.serialize(*params)
}

/**
 * 将键值数据序列化存储到磁盘
 */
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
/**
 * 根据[name]读取磁盘数据, 即使读取的是基础类型磁盘不存在的话也会返回null
 * @throws IllegalStateException MMKV.defaultMMKV() == null
 */
inline fun <reified T> deserialize(name: String): T {
    val mmkv = MMKV.defaultMMKV()
        ?: throw IllegalStateException("MMKV.defaultMMKV() == null, handle == 0 ")
    return mmkv.deserialize(name, T::class.java)
}

/**
 * 根据[name]读取磁盘数据, 假设磁盘没有则返回[defValue]指定的默认值
 * @throws IllegalStateException MMKV.defaultMMKV() == null
 */
inline fun <reified T> deserialize(name: String, defValue: T?): T {
    val mmkv = MMKV.defaultMMKV()
        ?: throw IllegalStateException("MMKV.defaultMMKV() == null, handle == 0 ")
    return mmkv.deserialize(name, T::class.java, defValue)
}

/** 根据[name]读取磁盘数据, 即使读取的是基础类型磁盘不存在的话也会返回null */
inline fun <reified T> MMKV.deserialize(name: String): T {
    return this.deserialize(name, T::class.java)
}

/** 根据[name]读取磁盘数据, 假设磁盘没有则返回[defValue]指定的默认值 */
inline fun <reified T> MMKV.deserialize(name: String, defValue: T?): T {
    return this.deserialize(name, T::class.java, defValue)
}

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

//<editor-fold desc="序列化对象">
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