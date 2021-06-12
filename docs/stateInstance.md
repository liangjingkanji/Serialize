ViewModel是Android JetPack组件库中的组件之一. 在AndroidSDK中自带其组件. 

配合本框架可以方便的读取使用. 继承不同的类可以实现不同的功能

1. ViewModel: 在配置变更时恢复数据. 配置变更即为切换横竖屏/夜间模式等
1. StateViewModel: 在意外销毁(包含配置变更)时恢复数据. 意外销毁即内存不足/修改权限情况下应用会重建组件

## ViewModel

ViewModel可以在屏幕旋转时也不会丢失数据

1) 创建类

要求继承ViewModel才可以具备自动恢复

```kotlin
class MainViewModel : ViewModel() {
    var name = "MainViewModel"
}
```

2) 创建字段

```kotlin
private val model: MainViewModel by viewModels()
```

3) 使用

```kotlin
model.name

Log.d("日志", "name = ${model.name}")
```

> 模拟配置变更可以切换手机屏幕方向


## StateViewModel

ViewModel可以扩展来自动`onSaveInstance`, 让应用意外销毁也可以保存/恢复数据

```kotlin
class MainStateViewModel(stateHandle: SavedStateHandle) : StateViewModel(stateHandle) {
    var name: String by stateModels()
}
```

2) 创建字段

```kotlin
private val model: MainStateViewModel by stateHandle()
```

3) 使用

```kotlin
model.name

Log.d("日志", "name = ${model.name}")
```

> 模拟意外销毁可以使用在应用后台运行时去设置界面变更应用的权限