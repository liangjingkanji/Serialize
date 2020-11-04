package com.drake.serialize

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 自动写入和读取自本地
 * @param default 默认值
 * @param name 键名, 默认使用 "当前类名.字段名", 顶层字段没有类名
 */
inline fun <reified T> delegateSerialize(
    default: T? = null,
    name: String? = null,
    model: Int? = null,
    cryptKey: String? = null
): ReadWriteProperty<Any?, T> {

    return object : ReadWriteProperty<Any?, T> {

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            val className = thisRef?.javaClass?.name
            var adjustKey = name ?: property.name
            if (className != null) adjustKey = "${className}.${adjustKey}"
            serialize(model, cryptKey, adjustKey to value)
        }

        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            val className = thisRef?.javaClass?.name
            var adjustKey = name ?: property.name
            if (className != null) adjustKey = "${className}.${adjustKey}"
            return if (default == null) {
                deserialize(adjustKey, model, cryptKey)
            } else {
                deserialize<T>(adjustKey, default, model, cryptKey)
            }
        }
    }
}