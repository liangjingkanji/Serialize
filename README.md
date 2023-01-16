<p align="center"><img src="https://i.loli.net/2020/11/05/kH4udPFocxtVC76.gif" width="200"/></p>

<p align="center"><strong>标准序列化工具</strong></p>

<p align="center"><a href="http://liangjingkanji.github.io/Serialize/">使用文档</a> | <a href="https://coding-pages-bucket-3558162-8706000-16646-587724-1252757332.cos-website.ap-shanghai.myqcloud.com/">备用访问</a></p>

<p align="center"><img src="https://i.imgur.com/atvWwig.png" width="420"/></p>

<p align="center">
<a href="https://jitpack.io/#liangjingkanji/Serialize"><img src="https://jitpack.io/v/liangjingkanji/Serialize.svg"/></a>
<img src="https://img.shields.io/badge/language-kotlin-orange.svg"/>
<img src="https://img.shields.io/badge/license-Apache-blue"/>
<a href="http://liangjingkanji.github.io/Serialize/updates"><img src="https://img.shields.io/badge/updates-%E6%9B%B4%E6%96%B0%E6%97%A5%E5%BF%97-brightgreen"/></a>
<a href="https://liangjingkanji.github.io/Serialize/api/"><img src="https://img.shields.io/badge/api-%E5%87%BD%E6%95%B0%E6%96%87%E6%A1%A3-red"/></a>
<img src="https://raw.githubusercontent.com/liangjingkanji/liangjingkanji/master/img/group.svg"/>
</p>


简化项目中的序列化

- 创建自动存储本地的字段(内部使用[MMKV](https://github.com/Tencent/MMKV))
- 创建自动存储本地的LiveData可观察字段
- 存储读写对象
- 创新式使用双通道读写磁盘, 耗时大幅度低于SQLite/sp/mmkv, 完美解决ANR
- 快捷跳转Activity/Fragment和参数传递
- 创建自动注入Activity/Fragment参数的字段(支持可空类型)


<br>

## 安装

添加远程仓库根据创建项目的 Android Studio 版本有所不同

Android Studio Arctic Fox以下创建的项目 在项目根目录的 build.gradle 添加仓库

```groovy
allprojects {
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }
}
```

Android Studio Arctic Fox以上创建的项目 在项目根目录的 settings.gradle 添加仓库

```kotlin
dependencyResolutionManagement {
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }
}
```

然后在 module 的 build.gradle 添加依赖框架

```groovy
implementation 'com.github.liangjingkanji:Serialize:2.0.0'
```

<br>

## License

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

