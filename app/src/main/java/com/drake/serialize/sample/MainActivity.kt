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
import android.os.SystemClock
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.drake.serialize.intent.openActivity
import com.drake.serialize.sample.databinding.ActivityMainBinding
import com.drake.serialize.sample.model.MyViewModel
import com.drake.serialize.sample.model.ParcelableModel
import com.drake.serialize.sample.model.SerializableModel
import com.drake.serialize.serialize.serial
import com.drake.serialize.serialize.serialLazy
import com.drake.serialize.serialize.serialLiveData
import com.drake.tooltip.toast


class MainActivity : AppCompatActivity() {

    private var name: String by serial()
    private var model: SerializableModel? by serialLazy()
    private var simple: String by serial("默认值", "自定义键名")
    private val viewModel: MyViewModel by viewModels()
    private val liveData by serialLiveData("默认值")

    private var test: Boolean by serialLazy(Boolean::class.java) {
        // 让调用者在这里，可以有机会处理一下逻辑，例如从 Preferences 中读取存量数据
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        liveData.observe(this) {
            toast("观察到本地数据: $it")
        }

        // 可观察数据
        binding.llObserve.setOnClickListener {
            liveData.value = SystemClock.elapsedRealtime().toString()
        }

        binding.cardWriteField.setOnClickListener {
            //name = "https://github.com/liangjingkanji/Serialize"
            //toast("写入数据: $name 到磁盘")
            println("test $test")
            test = true
            println("test $test")
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
            Log.d("日志", "isFirstLaunch = ${AppConfig.isFirstLaunch}")
            AppConfig.isFirstLaunch = false
        }

        // 读取100w次
        binding.cardBigRead.setOnClickListener {
            val startTime = SystemClock.elapsedRealtime()
            repeat(1000000) {
                val name = model?.name ?: toast("本地没有数据可读, 请先写入")
            }
            binding.tvBigReadTime.text = "${(SystemClock.elapsedRealtime() - startTime)}ms"
        }
        // 写入100w次
        binding.cardBigWrite.setOnClickListener {
            val startTime = SystemClock.elapsedRealtime()
            repeat(1000000) {
                model = SerializableModel("第${it}次")
            }
            binding.tvBigWriteTime.text = "${(SystemClock.elapsedRealtime() - startTime)}ms"
        }
    }
}
