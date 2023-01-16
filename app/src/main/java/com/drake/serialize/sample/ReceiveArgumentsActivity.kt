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

import android.os.Parcelable
import com.drake.engine.base.EngineActivity
import com.drake.serialize.intent.bundle
import com.drake.serialize.sample.databinding.ActivityReceiveArgumentsBinding
import com.drake.serialize.sample.model.ParcelableModel
import com.drake.serialize.sample.model.SerializableModel

class ReceiveArgumentsActivity : EngineActivity<ActivityReceiveArgumentsBinding>(R.layout.activity_receive_arguments) {

    /**
     * Fragment使用方法一样
     * bundle等方法仅[com.drake.serialize.serialize.SerializeHook]自定义序列化
     * 仅支持实现[Serializable][Parcelable]接口对象或基础类型, 并支持其类型的集合/数组
     */
    private val parcelize: ParcelableModel? by bundle()
    private val parcelizeList: ArrayList<ParcelableModel>? by bundle()
    private val serialize: SerializableModel? by bundle()
    private val serializeList: ArrayList<SerializableModel>? by bundle()
    private val intArray: IntArray? by bundle()

    override fun initView() {
    }

    override fun initData() {
        binding.tvReceiveArguments.text = "parcelize = $parcelize" +
                "\nparcelizeList = $parcelizeList" +
                "\nserialize = $serialize" +
                "\nserializeList = $serializeList" +
                "\nintArrayOf = ${intArray?.get(0)}"
    }
}
