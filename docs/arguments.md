快速在Activity/Fragment中传递和接受参数

!!! failure "默认值"
    基础类型默认值为null(未指定默认值情况下), 请注意非可空类型读到null会崩溃


### 传递参数

在界面A传递

=== "Activity"
    ```kotlin
    openActivity<TestActivity>(
        "parcelize" to ModelParcelable(),
        "name" to "名称",
        "age" to 34
    )
    ```

=== "Fragment"
    ```kotlin
    MyFragment().withArguments(
        "parcelize" to ModelParcelable(),
        "name" to "名称",
        "age" to 34
    )
    ```

### 读取参数

在界面B读取

```kotlin
private val parcelize: ModelParcelable by bundle()
private val name:String by bundle()
private val age:Int by bundle()
```

1. 允许字段为任何访问权限(例如private/public)
1. 不使用`openActivity`等函数也可以接受数据


## 集合泛型

写入`List<String>`,  读取就一定是`List<String>`, 否则集合`get[]`时会抛出`ClassCastException`