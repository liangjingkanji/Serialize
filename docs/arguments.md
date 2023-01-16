快速在Activity/Fragment中传递和获取参数

可以解决Java中存在的默认值问题. <br> 
譬如Intent中传递一个Int值. 即使你没有传递该Int值也是0(这在Kotlin中是错误的行为). 没有传递值应当默认为null

> 前一章介绍的`SerializeHook`并不支持bundle()等方法

### 传递参数

=== "打开Activity"
    ```kotlin
    openActivity<TestActivity>(
        "parcelize" to ModelParcelable(),
        "name" to "吴彦祖",
        "age" to 34
    )
    ```

=== "打开Fragment"
    ```kotlin
    MyFragment().withArguments(
        "parcelize" to ModelParcelable(),
        "name" to "吴彦祖",
        "age" to 34
    )
    ```

### 读取参数

```kotlin
private val parcelize: ModelParcelable by bundle()
private val name:String by bundle()
private val age:Int by bundle()
```

1. 允许字段为任何访问权限(例如private/public). 这点比ARouter路由框架的自动注入字段优秀的多(不推荐使用ARouter)
1. 不使用`openActivity`等Serialize框架的函数打开的界面也可以读取参数
1. 可以是成员属性也可以是局部变量


## 泛型问题

假设写入的`List<String>`,  读取的是`List<Int>`, 也会返回一个正常的集合对象, 但是`get[]`时候会导致抛出`ClassCastException`类型转换异常(官方也是如此)