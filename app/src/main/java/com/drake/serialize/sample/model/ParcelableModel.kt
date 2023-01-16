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

import android.os.Parcel
import android.os.Parcelable

/**
 * 使用Android的[Parcelable]进行序列化传递
 *
 * 1. 新增字段读取旧数据时如果字段非可空?会导致崩溃
 * 2. 字段顺序被打乱会导致读取
 *
 * 最佳方案请使用[KotlinSerializableModel]
 */
data class ParcelableModel(var name: String = "ModelParcelable") : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readString() ?: "ModelParcelable") // 读取空则赋值默认值

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ParcelableModel> {
        override fun createFromParcel(parcel: Parcel): ParcelableModel {
            return ParcelableModel(parcel)
        }

        override fun newArray(size: Int): Array<ParcelableModel?> {
            return arrayOfNulls(size)
        }
    }
}