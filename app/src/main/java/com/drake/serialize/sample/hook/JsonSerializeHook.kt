@file:OptIn(ExperimentalSerializationApi::class)

package com.drake.serialize.sample.hook

import com.drake.serialize.serialize.SerializeHook
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.protobuf.ProtoBuf
import kotlinx.serialization.serializer

/**
 * 本地序列化存储的数据结构为Json
 */
class JsonSerializeHook : SerializeHook {

    private val json = Json {
        ignoreUnknownKeys = true // JSON和数据模型字段可以不匹配
        coerceInputValues = true // 如果JSON字段是Null则使用默认值
    }

    override fun <T> serialize(name: String, type: Class<T>, data: Any): ByteArray {
        return json.encodeToString(ProtoBuf.serializersModule.serializer(type), data).toByteArray()
    }

    override fun <T> deserialize(name: String, type: Class<T>, bytes: ByteArray): Any {
        return json.decodeFromString(ProtoBuf.serializersModule.serializer(type), bytes.decodeToString())
    }
}