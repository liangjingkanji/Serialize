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

package com.drake.serialize.sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.drake.serialize.intent.bundle
import com.drake.serialize.intent.bundleLazy
import com.drake.serialize.sample.model.Model
import com.drake.serialize.sample.model.ModelParcelable
import com.drake.serialize.sample.model.ModelSerializable

class TestActivity : AppCompatActivity(R.layout.activity_test) {

    private val parcelize: ModelParcelable? by bundle()
    private val parcelizeList: ArrayList<ModelParcelable>? by bundle()
    private val serialize: ModelSerializable? by bundle()
    private val serializeList: ArrayList<ModelSerializable>? by bundle()
    private val intArray: IntArray? by bundle()
    private val modelList: List<Model>? by bundle()
    private val model: Model? by bundleLazy { Model("延迟初始化默认值") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val serialize: ModelSerializable? by bundle()
        Log.d("日志", "(TestActivity.kt:27)    parcelize = ${parcelize}")
        Log.d("日志", "(TestActivity.kt:28)    parcelizeList = ${parcelizeList}")
        Log.d("日志", "(TestActivity.kt:29)    serialize = ${serialize}")
        Log.d("日志", "(TestActivity.kt:30)    serializeList = ${serializeList}")
        Log.d("日志", "(TestActivity.kt:30)    intArrayOf = ${intArray?.get(0)}")
        Log.d("日志", "(TestActivity.kt:30)    modelList = ${modelList?.get(0)}")
        Log.d("日志", "(TestActivity.kt:30)    model = ${model}")
    }
}
