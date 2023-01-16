package com.drake.serialize.serialize

import android.os.Parcel
import android.os.Parcelable
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.reflect.Field

/**
 * 序列化接口
 *
 * 接口默认实现支持读写实现[Serializable][Parcelable]接口对象或基础类型, 并支持其类型的集合/数组
 * 实现该接口可以自定义序列化方式支持更多数据类型, 例如Json/ProtoBuf等数据协议存储, 亦或加密存储数据
 */
interface SerializeHook {

    /** 使用[Parcelable]/[Serializable]序列化方案 */
    companion object DEFAULT : SerializeHook

    /**
     * 序列化字段
     *
     * @param name 字段名
     * @param type 字段类型
     * @param data 字段实例
     *
     * @return 存储字节数组, 如果返回null则将删除该字段
     */
    fun <T> serialize(name: String, type: Class<T>, data: Any): ByteArray? {
        when {
            Parcelable::class.java.isAssignableFrom(type) -> {
                val source = Parcel.obtain()
                (data as Parcelable).writeToParcel(source, 0)
                val bytes = source.marshall()
                source.recycle()
                return bytes
            }
            else -> {
                ByteArrayOutputStream().use { byteOutput ->
                    ObjectOutputStream(byteOutput).use { objOutput ->
                        objOutput.writeObject(data)
                        return byteOutput.toByteArray()
                    }
                }
            }
        }
    }

    /**
     * 反序列化字段
     *
     * @param name 字段名
     * @param type 字段类型
     * @param bytes 字段字节码
     *
     * @return 反序列化后的对象, 如果返回null则将使用默认值(存在的话)
     */
    fun <T> deserialize(name: String, type: Class<T>, bytes: ByteArray): Any? {
        when {
            Parcelable::class.java.isAssignableFrom(type) -> {
                val source = Parcel.obtain()
                source.unmarshall(bytes, 0, bytes.size)
                source.setDataPosition(0)
                return try {
                    val f: Field = type.getField("CREATOR")
                    val creator: Parcelable.Creator<*> = f.get(null) as Parcelable.Creator<*>
                    creator.createFromParcel(source)
                } catch (var16: Exception) {
                    null
                } finally {
                    source.recycle()
                }
            }
            else -> {
                ByteArrayInputStream(bytes).use { byteInput ->
                    ObjectInputStream(byteInput).use { objInput ->
                        return objInput.readObject()
                    }
                }
            }
        }
    }
}