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
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.drake.serialize.intent.openActivity
import com.drake.serialize.model.stateModels
import com.drake.serialize.sample.databinding.ActivityMainBinding
import com.drake.serialize.sample.model.MyStateViewModel
import com.drake.serialize.sample.model.MyViewModel
import com.drake.serialize.sample.model.ParcelableModel
import com.drake.serialize.sample.model.SerializableModel
import com.drake.serialize.serialize.serial
import com.drake.serialize.serialize.serialLazy
import com.drake.tooltip.toast


class MainActivity : AppCompatActivity() {

    private var name: String by serial()
    private var model: SerializableModel by serialLazy()
    private var simple: String by serial("默认值", "自定义键名")
    private val stateModel: MyStateViewModel by stateModels()
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        binding.cardWriteField.setOnClickListener {
            name = "吴彦祖"
            toast("写入数据 [吴彦祖] 到磁盘")
        }
        binding.cardReadField.setOnClickListener {
            toast("读取本地数据为: $name")
        }
        binding.cardOpenPage.setOnClickListener {
            openActivity<TestActivity>(
                "parcelize" to ParcelableModel(),
                "parcelizeList" to listOf(ParcelableModel()),
                "serialize" to SerializableModel(),
                "serializeList" to listOf(SerializableModel()),
                "intArray" to intArrayOf(1, 3, 4),
            )
            // MyFragment().withArguments("parcelize" to ParcelableModel()) // Fragment传递数据
        }
        binding.cardConfig.setOnClickListener {
            Log.d("日志", "(MainActivity.kt:60)    isFirstLaunch = ${AppConfig.isFirstLaunch}")
            AppConfig.isFirstLaunch = false
        }
    }
}
