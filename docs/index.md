### 初始化

内部使用的MMKV所以需要初始化一下

```kotlin
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this, MMKVLogLevel.LevelNone) // LevelNone即关闭日志
    }
}
```

### 创建序列化字段

```kotlin
private var name: String? by serial()
private var model: ModelSerializable by serial()
private var simple: String by serial("默认值", "自定义键名")
```

之后这个字段读写都会自动读取和写入自本地

```kotlin
name = "吴彦祖" // 写入本地

Log.d("日志", "name = ${name}") // 读取本地字段
```

在这里可以自己声明可空和不可空, 即是否添加`?`符号, 假设字段在本地不存在而你为添加`?`则会导致抛出空指针异常, 而添加`?`则仅仅会抛出异常

> 内部使用腾讯的[MMKV](https://github.com/Tencent/MMKV)实现, 因为其比SharePreference速度快多, 可以有效解决ANR

目前支持类型

| 类型 | 描述 |
|-|-|
| 任何集合 | 集合的泛型自己注意匹配正确, 否则会get时抛出`ClassCastException`类型转换异常, 官方也是如此 |
| 任何基础类型 | 基础类型如果在不指定默认值情况下读取不到会返回Null, 这是为了符合Kotlin |
| Serializable | 实现Serializable的类 |
| Parcelable |  实现Parcelable的类 |

## 手动序列化

```kotlin
serialize("name" to "吴彦祖") // 写

val name:String = deserialize("name") // 读
val nameB:String = deserialize("name", "默认值") // 假设读取失败返回默认值
```