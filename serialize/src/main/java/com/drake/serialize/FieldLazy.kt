package com.drake.serialize

import kotlin.reflect.KProperty


fun <T> lazyField(block: Any?.(KProperty<*>) -> T) = FieldLazy(block)

@Suppress("UNCHECKED_CAST")
class FieldLazy<T>(var block: Any?.(KProperty<*>) -> T) {

    @Volatile
    private var value: T? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return synchronized(this) {
            if (value == null) {
                value = block(thisRef, property)
                value as T
            } else value as T
        }
    }
}