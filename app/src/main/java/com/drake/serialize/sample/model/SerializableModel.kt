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

import java.io.Serializable

/**
 * 使用Java的[Serializable]进行序列化传递
 *
 * 1. 新增字段读取旧数据时会读取失败(除非使用serialVersionUID)
 * 2. 新增字段读取默认值默认值无效
 *
 * 最佳方案请使用[KotlinSerializableModel]
 */
@kotlinx.serialization.Serializable
data class SerializableModel(var name: String = "ModelSerializable") : Serializable {
    companion object {
        /**
         * 1. 保证新增字段依然可以读取到对象
         * 2. 该Id不能重复否则读取异常
         * 3. 新增字段的默认值无效, 例如新增`firstName = "default"`, 但读取到的默认值是null
         */
        private const val serialVersionUID = -7L
    }
}