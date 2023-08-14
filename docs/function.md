通过函数读写数据, 方式比较原始

```kotlin
serialize("name" to "名称", "年龄" to 30) // 写

val firstName:String = deserialize("firstName") // 读
val lastName:String = deserialize("lastName", "默认值") // 读取失败返回默认值
val username:String = MMKV.mmkvWithID("User").deserialize("name") // 指定mmapID/数据隔离
```

!!! warning "不推荐"
    代码可读性差, 且容易误写, 不便于统一管理