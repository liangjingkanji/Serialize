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
import com.drake.serialize.intent.openActivity
import com.drake.serialize.sample.model.MainViewModel
import com.drake.serialize.sample.model.ModelParcelable
import com.drake.serialize.sample.model.ModelSerializable
import com.drake.serialize.serialize.deserialize
import com.drake.serialize.serialize.serial
import com.drake.serialize.serialize.serialize
import com.drake.serialize.state
import com.hulab.debugkit.dev


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private var name: String by serial()
    private var model: ModelSerializable by serial()
    private var simple: String by serial("默认值", "自定义键名")
    private val stateModel: MainViewModel by state()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val list: List<String>? = listOf<String>()

        dev {
            function {
                stateModel.name = "吴彦祖"

                serialize("name" to "吴彦祖")
                val name: String = deserialize("name")
            }
            function {
                Log.d("日志", "(MainActivity.kt:36)    stateModel.name = ${stateModel.name}")
            }
            function("写") {
                name = "drake"
            }
            function("读") {
                Log.d("日志", "(MainActivity.kt:28)    name = ${name}")
            }
            function("打开界面") {
                openActivity<TestActivity>(
                    "parcelize" to ModelParcelable(),
                    "parcelizeList" to listOf(ModelParcelable()),
                    "serialize" to ModelSerializable(),
                    "serializeList" to listOf(ModelSerializable()),
                    "intArray" to intArrayOf(1, 3, 4),
                )
            }
        }
    }
}
