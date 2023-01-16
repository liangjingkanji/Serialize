/*
 * Copyright (C) 2018 Drake, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.drake.serialize.sample.model

/**
 * 使用kotlin官方序列化框架[kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization)
 *
 * 1. 使用转换为Json或者Protobuf等数据格式后存储
 * 2. 即使增删字段也不会导致读取失败
 * 3. 读写性能也更高
 */
@kotlinx.serialization.Serializable
data class KotlinSerializableModel(var name: String = "默认值")