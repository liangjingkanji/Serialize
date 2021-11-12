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

/** 使用Android自带的[Parcelable]进行序列化传递 */
// @kotlinx.parcelize.Parcelize // 请不要使用注解而是手动实现
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