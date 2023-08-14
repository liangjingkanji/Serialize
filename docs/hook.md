!!! failure " 强烈建议"
    使用自定义序列化, 可以避免读取旧数据失败问题

所有数据的序列化/反序列化都会经过`SerializeHook`转换, 在字节数组/对象之间

```kotlin
Serialize.hook = ProtobufSerializeHook()
```

??? example "SerializeHook"
    ```kotlin
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
        }

        /**
         * 根据mmapID获取mmkv实例
         */
        fun mmkvWithID(mmapID: String, mode: Int, cryptKey: String?): MMKV {
            return MMKV.mmkvWithID(mmapID, mode, cryptKey, null)
        }
    }
    ```

参考示例

1. [JsonSerializeHook](https://github.com/liangjingkanji/Serialize/blob/master/app/src/main/java/com/drake/serialize/sample/hook/JsonSerializeHook.kt)
1. [ProtobufSerializeHook](https://github.com/liangjingkanji/Serialize/blob/master/app/src/main/java/com/drake/serialize/sample/hook/ProtobufSerializeHook.kt)


!!! note "加密本地数据"
    MMKV支持加密, 不需要使用`SerializeHook`, 请自行搜索实现