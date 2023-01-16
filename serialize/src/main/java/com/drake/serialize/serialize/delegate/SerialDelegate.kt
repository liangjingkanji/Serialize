package com.drake.serialize.serialize.delegate

import com.drake.serialize.serialize.deserialize
import com.drake.serialize.serialize.serialize
import com.tencent.mmkv.MMKV
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 构建自动映射到本地磁盘的委托属性
 */
@PublishedApi
internal class SerialDelegate<V>(
    private val default: V?,
    private val type: Class<V>,
    private val name: String?,
    private val kv: MMKV
) : ReadWriteProperty<Any, V> {

    override fun getValue(thisRef: Any, property: KProperty<*>): V {
        val key = "${thisRef.javaClass.name}.${name ?: property.name}"
        return if (default == null) {
            kv.deserialize(type, key)
        } else {
            kv.deserialize(type, key, default)
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: V) {
        val key = "${thisRef.javaClass.name}.${name ?: property.name}"
        kv.serialize(key to value)
    }

}