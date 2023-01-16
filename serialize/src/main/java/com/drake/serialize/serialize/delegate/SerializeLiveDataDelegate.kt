package com.drake.serialize.serialize.delegate

import androidx.lifecycle.MutableLiveData
import com.drake.serialize.serialize.deserialize
import com.drake.serialize.serialize.serialize
import com.tencent.mmkv.MMKV
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * 构建自动映射到本地磁盘的可观察数据[MutableLiveData]委托属性
 * 内存/磁盘双通道读写
 */
@PublishedApi
internal class SerializeLiveDataDelegate<V>(
    private val default: V?,
    private val type: Class<V>,
    private val name: String?,
    private val kv: MMKV,
) : ReadOnlyProperty<Any, MutableLiveData<V>>, MutableLiveData<V>() {

    private lateinit var thisRef: Any
    private lateinit var property: KProperty<*>

    override fun getValue(): V? = synchronized(this) {
        var value = super.getValue()
        if (value == null) {
            val key = "${thisRef.javaClass.name}.${name ?: property.name}"
            value = if (default == null) {
                kv.deserialize(type, key)
            } else {
                kv.deserialize(type, key, default)
            }
        }
        value
    }

    override fun getValue(
        thisRef: Any,
        property: KProperty<*>
    ): MutableLiveData<V> = synchronized(this) {
        this.thisRef = thisRef
        this.property = property
        val value = value
        if (super.getValue() == null && value != null) {
            super.setValue(value)
        }
        this
    }

    override fun setValue(value: V) {
        super.setValue(value)
        asyncSerialize(value)
    }

    override fun postValue(value: V) {
        super.postValue(value)
        asyncSerialize(value)
    }

    /** 写入本地在子线程处理，单一线程保证了写入顺序 */
    private fun asyncSerialize(value: V) {
        taskExecutor.execute {
            val key = "${thisRef.javaClass.name}.${name ?: property.name}"
            kv.serialize(key to value)
        }
    }

    companion object {
        /** 单一线程 无界队列  保证任务按照提交顺序来执行 **/
        private val taskExecutor = Executors.newSingleThreadExecutor(ThreadFactory {
            val thread = Thread(it)
            thread.name = "SerializeLiveDataDelegate"
            return@ThreadFactory thread
        })
    }
}