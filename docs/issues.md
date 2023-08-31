## 赋值无效

```kotlin
// 要求修改UserData字段, 然后保存到本地
object UserConfig {
    var userData:UserData by serial()
}
```

错误示例
```kotlin
UserConfig.userData.name = "new name"
UserConfig.userData = UserConfig.userData
```
如果使用serialLazy以上方式有效(因为他自带临时变量)

!!! failure "修改无效"
    修改变量里面的字段并不会更新本地数据, 下次读取的还是旧的值

解决办法, 使用临时变量

```kotlin
val userData = UserConfig.userData
userData.name = "new name"
UserConfig.userData = userData
```

## 读取旧数据崩溃

如果开发者未[自定义SerializeHook](hook.md)而使用默认序列化, 很可能导致危险数据

!!! failure "数据损坏"
    增删字段可能导致无法读取数据, 由于`Serializable`和`Parcelable`本身局限导致

    解决办法是自定义`SerializeHook`, 使用`Json/Protobuf`等序列化框架实现数据存储


*Serializable*

1. 增删字段导致读取失败, 使用`serialVersionUUID`可解决
2. 但新增字段默认值将为零值或null, 而不是声明的默认值

*Parcelable*

1. 读取新增的非空类型字段会崩溃(如果不存在)
2. 字段顺序被打乱会导致读取失败

## 序列化函数类型

一些序列化框架不支持包含函数类型, 例如`kotlin-serialization`, 添加`@Transient`即可

```kotlin
@Serializable
class Data(var name: String, @Transient var unit: () -> Unit)
```

## 迁移旧数据

1. 以前使用MMKV, 由于本项目基于MMKV所以不需要迁移

2. 以前使用SharedPreferences, 可以使用MMKV迁移方法

    ```kotlin
    MMKV.defaultMMKV().importFromSharedPreferences(getSharedPreferences("sp", MODE_PRIVATE))
    ```

更多数据迁移需求请实现`SerializeHook`接口来自定义