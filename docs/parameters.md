快速在Activity/Fragment中传递和获取参数

### 传递参数
```kotlin
openActivity<TestActivity>(
    "parcelize" to ModelParcelable(),
    "name" to "吴彦祖",
    "age" to 34
)
```

### 获取参数

```kotlin
private val parcelize: ModelParcelable? by bundle()
private val name:String by bundle()
private val age:Int by bundle()
```

<br>

1. 不要求使用本框架规定的传递函数才能通过本框架获取到参数, 两者没有关系
2. 这样的委托属性可以在对象成员使用也可以在函数内部使用


> 类型错误或者读取失败会返回Null, 即使基本类型也是如此(除非指定默认值)

假设写入的List\<String\>,  读取的是List\<Int\>, 也会返回一个正常的集合对象, 但是`get[]`时候会导致抛出`ClassCastException`类型转换异常(官方也是如此)