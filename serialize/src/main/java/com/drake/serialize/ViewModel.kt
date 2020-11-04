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
import androidx.lifecycle.*
import com.drake.serialize.delegate.lazyField
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 快速创建LiveData的观察者
 */
fun <M> LifecycleOwner.observe(liveData: LiveData<M>?, block: M?.() -> Unit) {
    liveData?.observe(this, { it.block() })
}

/**
 * 返回当前组件指定的ViewModel
 */
inline fun <reified V : ViewModel> ViewModelStoreOwner?.model() = lazyField {
    if (this != null) {
        ViewModelProvider(this).get(V::class.java)
    } else null as V
}

/**
 * 返回当前组件指定的SavedViewModel
 */
inline fun <reified V : StateViewModel> FragmentActivity?.state() = lazyField {
    if (this != null) {
        ViewModelProvider(this, SavedStateViewModelFactory(application, this)).get(V::class.java)
    } else null as V
}

inline fun <reified V : StateViewModel> Fragment?.state() = lazyField {
    if (this != null) {
        ViewModelProvider(
            this,
            SavedStateViewModelFactory(requireActivity().application, this)
        ).get(V::class.java)
    } else null as V
}


inline fun <reified V> StateViewModel?.state(
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
