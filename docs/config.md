## 单例配置

为类添加注解`@SerializeConfig`可指定其所有`serialXX()`字段的[MMKV实例配置](https://github.com/Tencent/MMKV/wiki/android_advance)

```kotlin
@SerializeConfig(mmapID = "app_config")
object AppConfig {
    var isFirstLaunch: Boolean by serial()
}
```

!!! failure "同名覆盖"
    当`mmapID`和字段名相同情况下会覆盖值, 甚至引起读取类型错误导致崩溃

## 全局配置

```kotlin
Serialize.mmkv = MMKV.mmkvWithID("app_config")
```


## 多账户存储

使用`SerializeHook`动态指定`mmapID`, 常见场景为根据账号使用不同用户数据

创建用户数据类
```kotlin
@SerializeConfig(mmapID = "user_config") // 指定mmapID可以避免字段名重复情况下导致的错误
object UserConfig {
    var userId: String by serialLazy()
}
```

实现`SerializeHook`
```kotlin
class ProtobufSerializeHook : SerializeHook {

    // ...

    override fun mmkvWithID(mmapID: String, mode: Int, cryptKey: String?): MMKV {
        // 当存储为用户数据时, 添加当前账户到mmapID中, 请注意不要循环调用
        if (mmapID == "user_config") {
            return super.mmkvWithID(mmapID + "_" + AppConfig.currentAccount, mode, cryptKey)
        }
        return super.mmkvWithID(mmapID, mode, cryptKey)
    }
}
```

## 指定存储目录/日志等级

默认情况下，MMKV 将文件存储在`$(FilesDir)/mmkv/`

使用MMKV方法可指定自定义存储目录或日志输出等级

```kotlin
MMKV.initialize(cacheDir.absolutePath, MMKVLogLevel.LevelInfo) // 存储路径, 日志等级
```

## 清除数据

两种方式清除数据

1. 赋值为null
    ```kotlin
    var userId :String by serial()
    userId = null
    ```


2. 指定`MMKV实例`删除
    ```kotlin
    MMKV.defaultMMKV().remove("字段名")
    MMKV.defaultMMKV().clearAll()
    MMKV.mmkvWithID("app_config").clearAll()
    ```
