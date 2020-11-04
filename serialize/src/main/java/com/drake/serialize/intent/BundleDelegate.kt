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

package com.drake.serialize.intent

import android.app.Activity
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.drake.serialize.delegate.lazyField

//<editor-fold desc="Activity">
/**
 * 从序列化中检索到数据
 * @param name 键名
 * @param defValue 默认值
 */
inline fun <reified T> Activity?.bundle(defValue: T? = null, name: String? = null) =
    lazyField {
        val adjustName = name ?: it.name
        val result = when {
            Parcelable::class.java.isAssignableFrom(T::class.java) -> this?.intent?.getParcelableExtra<Parcelable>(
                adjustName
            ) as? T
            else -> this?.intent?.getSerializableExtra(adjustName) as? T
        }
        result ?: defValue ?: null as T
    }

/**
 * 从序列化中检索到数据
 * @param name 键名
 * @param defValue 懒加载默认值回调函数
 */
@JvmSynthetic
inline fun <reified T> FragmentActivity?.bundleLazy(
    name: String? = null,
    crossinline defValue: () -> T
) = lazyField {
    val adjustName = name ?: it.name
    val result = when {
        Parcelable::class.java.isAssignableFrom(T::class.java) -> this?.intent?.getParcelableExtra<Parcelable>(
            adjustName
        ) as? T
        else -> this?.intent?.getSerializableExtra(adjustName) as? T
    }
    result ?: defValue() ?: null as T
}
//</editor-fold>


//<editor-fold desc="Fragment">
/**
 * 从序列化中检索到数据
 * @param name 键名
 * @param defValue 默认值
 */
@JvmSynthetic
inline fun <reified T> Fragment?.bundle(defValue: T? = null, name: String? = null) =
    lazyField {
        val adjustName = name ?: it.name
        val result = when {
            Parcelable::class.java.isAssignableFrom(T::class.java) -> this?.arguments?.getParcelable<Parcelable>(
                adjustName
            ) as? T
            else -> this?.arguments?.getSerializable(adjustName) as? T
        }
        result ?: defValue ?: null as T
    }

/**
 * 从序列化中检索到数据
 * @param name 键名
 * @param defValue 懒加载默认值回调函数
 */
@JvmSynthetic
inline fun <reified T> Fragment?.bundleLazy(
    name: String? = null,
    crossinline defValue: () -> T
) = lazyField {
    val adjustName = name ?: it.name
    val result = when {
        Parcelable::class.java.isAssignableFrom(T::class.java) -> this?.arguments?.getParcelable<Parcelable>(
            adjustName
        ) as? T
        else -> this?.arguments?.getSerializable(adjustName) as? T
    }
    result ?: defValue() ?: null as T
}
//</editor-fold>