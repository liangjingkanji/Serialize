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
import com.drake.serialize.intent.bundle
import com.drake.serialize.sample.R
import kotlinx.android.synthetic.main.fragment_test.*

class TestFragment : Fragment(R.layout.fragment_test) {

    private var data: String? by bundle() // 传递的键名默认为字段名
    private var defaultValue: Boolean by bundle(false) // 存在默认值的字段
    private var specifyName: Int by bundle(name = "info") // 指定键名
    // private var nullable: String by bundle() // 假设声明类型为非空, 但是没有传递数据会导致异常抛出

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tv_data.text = data ?: return
        tv_default_value.text = defaultValue.toString()
        tv_specify_name.text = specifyName.toString()
    }
}