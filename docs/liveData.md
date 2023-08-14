使用`serialLiveData`创建LiveData

```kotlin
val liveData by serialLiveData("默认值")
```

每次写入都回调观察者`observe`
```kotlin
liveData.observe(this) {
    toast("观察到本地数据: $it")
}
```