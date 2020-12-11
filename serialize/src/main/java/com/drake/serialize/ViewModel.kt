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

@file:Suppress("CAST_NEVER_SUCCEEDS")

package com.drake.serialize

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 返回当前组件指定的SavedViewModel
 * 要求数据类型为StateViewModel的子集
 */
inline fun <reified V : StateViewModel> FragmentActivity?.stateModels() = lazy {
    if (this != null) {
        ViewModelProvider(this, SavedStateViewModelFactory(application, this)).get(V::class.java)
    } else null as V
}

inline fun <reified V : StateViewModel> Fragment?.stateModels() = lazy {
    if (this != null) {
        ViewModelProvider(this, SavedStateViewModelFactory(requireActivity().application, this)).get(V::class.java)
    } else null as V
}


/**
 * 自动根据生命周期使用[FragmentActivity.onSaveInstanceState]
 */
inline fun <reified V> StateViewModel?.stateHandle(
    defValue: V? = null,
    name: String? = null
) = object : ReadWriteProperty<StateViewModel, V> {

    override fun setValue(thisRef: StateViewModel, property: KProperty<*>, value: V) {
        val adjustName = name ?: property.name
        thisRef.stateHandle.set(adjustName, value)
    }

    override fun getValue(thisRef: StateViewModel, property: KProperty<*>): V {
        val adjustName = name ?: property.name
        return thisRef.stateHandle.get<V>(adjustName) ?: defValue as V
    }
}
