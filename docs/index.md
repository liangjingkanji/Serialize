Serialize基于腾讯 [MMKV](https://github.com/Tencent/MMKV) 实现, 其比SharePreference/SQLite速度快, 可以有效解决ANR

## 使用场景

本项目为解决以下问题

1. 变量只存在内存中而不是映射本地磁盘
1. 只能存储基础类型不能直接存储对象
1. 读写本地数据会阻塞主线程
1. 不能监听字段的读写

!!! failure "强制阅读"
    请一定阅读章节: [常见问题](issues.md), 以保证数据安全性

    关系查询/列表数据/大量数据场景建议使用数据库, 本框架无法满足其复杂需求



## 序列化字段

创建一个变量, 对该变量读写都会自动映射到本地磁盘, 即序列化字段

```kotlin
@SerializeConfig(mmapID = "app_config") // 指定mmapID可以避免字段名重复情况下导致的错误
object AppConfig {
    var name: String by serial()
    var simple: String by serial("默认值", "自定义键名")
}
```

之后该字段读写都会自动读取和写入到本地磁盘

```kotlin
AppConfig.name = "名称" // 写入到本地磁盘
AppConfig.name // 读取自本地磁盘
```

!!! failure "初始化"
    多进程项目要求在`Application`中初始化MMKV
    ```kotlin
    MMKV.initialize(this)
    ```

## 数据类型

支持存储的类型取决于`SerializeHook`, 如果开发者没有[自定义SerializeHook](hook.md)那么仅支持以下类型

| 类型 | 描述 |
|-|-|
| 基础类型 | 任何基础类型 |
| Serializable | Serializable子类 |
| Parcelable |  Parcelable子类 |
| 集合/数组 | 以上类型的集合/数组, 请填写正确泛型, 否则抛出`ClassCastException` |

!!! failure "基础类型默认值"
    本框架基于kotlin设计, 基础类型默认值也是null

## 可空字段

如果不声明可空类型, 且也不存在默认值, 那么读取不存在的字段时将抛出异常

```kotlin
var nameOrNull: String? by serial()   // 不存在时读取为null
var nameOrException: String by serial()  // 不存在时读取将崩溃
```

## 懒加载

即只在第一次读取字段的时候才会从本地读取, 后续从内存读取


```kotlin
var model: ModelSerializable by serialLazy() // 懒加载
```

1. 不支持跨进程
1. 写入时将先更改内存值, 然后使用异步方式写入磁盘

!!! success "零耗时"
    彻底解决反复读取磁盘造成耗时

## 动态键名

动态键名请使用`{}`函数回调返回值

```kotlin
var userId: String = "0123"
var balance: String by serial("0.0", { "${userId}:balance" })
```