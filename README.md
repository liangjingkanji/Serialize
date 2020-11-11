<p align="center"><img src="https://i.loli.net/2020/11/05/kH4udPFocxtVC76.gif" width="200"/></p>

<p align="center"><strong>标准序列化工具</strong></p>

<p align="center"><a href="http://liangjingkanji.github.io/Serialize/">使用文档</a></p>

<p align="center"><img src="https://i.imgur.com/VBsC3Ra.jpg" width="350"/></p>

<p align="center">
<a href="https://jitpack.io/#liangjingkanji/Serialize"><img src="https://jitpack.io/v/liangjingkanji/Serialize.svg"/></a>
<img src="https://img.shields.io/badge/language-kotlin-orange.svg"/>
<img src="https://img.shields.io/badge/license-Apache-blue"/>
<a href="https://jq.qq.com/?_wv=1027&k=vWsXSNBJ"><img src="https://img.shields.io/badge/QQ群-752854893-blue"/></a>
</p>


简化项目中的序列化

- 开启组件方便传递参数
- 创建自动读写自本地的字段
- 创建自动读写Intent/Argument参数
- 创建意外销毁自动保存/恢复的字段
- 创建配置变更/屏幕旋转自动恢复的数据实例
- 快捷打开意图

<br>

在项目根目录的 build.gradle 添加仓库

```groovy
allprojects {
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }
}
```

在 module 的 build.gradle 添加依赖

```groovy
implementation 'com.github.liangjingkanji:Serialize:1.0.3'
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

