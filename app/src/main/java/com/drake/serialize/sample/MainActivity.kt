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

import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.drake.engine.base.EngineActivity
import com.drake.serialize.intent.openActivity
import com.drake.serialize.sample.constant.AppConfig
import com.drake.serialize.sample.databinding.ActivityMainBinding
import com.drake.serialize.sample.model.ParcelableModel
import com.drake.serialize.sample.model.SerializableModel
import com.drake.serialize.serialize.serial
import com.drake.serialize.serialize.serialLazy
import com.drake.serialize.serialize.serialLiveData
import com.drake.tooltip.toast
import kotlin.system.measureTimeMillis


class MainActivity : EngineActivity<ActivityMainBinding>(R.layout.activity_main) {

    private var name: String by serial()
    private var data: SerializableModel? by serialLazy()
    private var amount: String by serial("默认值", "自定义键名")
    private val liveData: MutableLiveData<String> by serialLiveData("默认值")

    override fun initView() {
        binding.v = this
    }

    override fun initData() {
        // 监听本地数据变化
        liveData.observe(this) {
            toast("观察到本地数据: $it")
        }
    }

    override fun onClick(v: View) {
        when (v) {
            // 可观察本地数据
            binding.llObserve -> {
                liveData.value = SystemClock.elapsedRealtime().toString()
            }
            // 写入
            binding.cardWriteField -> {
                name = "https://github.com/liangjingkanji/Serialize"
                toast("写入数据: $name 到磁盘")
            }
            // 读取
            binding.cardReadField -> {
                toast("读取本地数据为: $name")
            }
            // 打开页面
            binding.cardOpenPage -> {
                // startActivity 同样可以传递数据
                openActivity<ReceiveArgumentsActivity>(
                    "serialize" to SerializableModel(),
                    "serializeList" to listOf(SerializableModel(), SerializableModel()),
                    "parcelize" to ParcelableModel(),
                    "parcelizeList" to listOf(ParcelableModel(), ParcelableModel()),
                    "intArray" to intArrayOf(1, 3, 4),
                )
                // ReceiveArgumentsFragment().withArguments("parcelize" to ParcelableModel()) // Fragment传递数据
            }
            // 应用配置数据
            binding.cardConfig -> {
                Log.d("日志", "isFirstLaunch = ${AppConfig.isFirstLaunch}")
                AppConfig.isFirstLaunch = false
            }
            // 读取100w次
            binding.cardBigRead -> {
                val measureTimeMillis = measureTimeMillis {
                    repeat(100000) {
                        val name = data?.name ?: toast("本地没有数据可读, 请先写入")
                    }
                }
                binding.tvBigReadTime.text = "${measureTimeMillis}ms"
            }
            // 写入100w次
            binding.cardBigWrite -> {
                val measureTimeMillis = measureTimeMillis {
                    repeat(100000) {
                        data = SerializableModel("第${it}次")
                    }
                }
                binding.tvBigWriteTime.text = "${measureTimeMillis}ms"
            }
        }
    }
}
