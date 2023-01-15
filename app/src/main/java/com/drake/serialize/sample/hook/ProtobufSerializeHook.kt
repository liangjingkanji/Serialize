@file:OptIn(ExperimentalSerializationApi::class)

package com.drake.serialize.sample.hook

import com.drake.serialize.serialize.SerializeHook
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.protobuf.ProtoBuf
import kotlinx.serialization.serializer

/**
 * 本地序列化存储的数据结构为Protobuf
 */
class ProtobufSerializeHook : SerializeHook {

    override fun <T> serialize(name: String, type: Class<T>, data: Any): ByteArray {
        return ProtoBuf.encodeToByteArray(ProtoBuf.serializersModule.serializer(type), data)
    }

    override fun <T> deserialize(name: String, type: Class<T>, bytes: ByteArray): Any {
        return ProtoBuf.decodeFromByteArray(ProtoBuf.serializersModule.serializer(type), bytes)
    }
}