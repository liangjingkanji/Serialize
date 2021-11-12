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

import android.os.Parcelable
import java.io.Serializable

/** 使用Java自带的[Serializable]进行序列化传递 */
data class SerializableModel(var name: String = "ModelSerializable") : Serializable {
    companion object {
        /**
         * 保证新增字段依然可以读取到对象, 该Id不能重复否则读取异常
         * 注意新增字段的默认值是无效的,比如新增 firstName = "default" 实际上读取到的默认值是null
         * 想保证完整的默认值效果请实现[Parcelable], 具体请查看[ParcelableModel]
         */
        private const val serialVersionUID = -7L
    }
}