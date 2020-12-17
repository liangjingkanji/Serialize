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

package com.drake.serialize.sample.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.drake.serialize.intent.openActivity
import com.drake.serialize.sample.R
import com.drake.serialize.sample.model.ModelParcelable
import com.drake.serialize.sample.model.ModelSerializable
import com.drake.serialize.sample.ui.activity.TestActivity
import kotlinx.android.synthetic.main.fragment_activity_transfer.*

/**
 * 演示打开界面传递数据
 */
class ActivityTransferFragment : Fragment(R.layout.fragment_activity_transfer) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        tv_open_activity.setOnClickListener {
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