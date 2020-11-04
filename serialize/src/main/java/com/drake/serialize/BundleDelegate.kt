/*
 * Designed and developed by 2020 skydoves (Jaewoong Eum)
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

@file:Suppress("UNCHECKED_CAST", "unused")

package com.drake.serialize

import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.io.Serializable

//<editor-fold desc="Activity">
/**
 * Retrieves a primitive type of extended data from intent lazily.
 *
 * @param name The name of the desired item.
 * @param defValue The value to be returned if no value of the desired type is stored with the given name.
 *
 * @throws IllegalArgumentException When a value is not a supported type of [Bundle].
 */
inline fun <reified T> Activity.bundle(defValue: T, name: String? = null): FieldLazy<T> =
        lazyField {
            val adjustName = name ?: it.name
            when (val value: T = defValue) {
                is Boolean -> intent.getBooleanExtra(adjustName, value)
                is Byte -> intent.getByteExtra(adjustName, value)
                is Char -> intent.getCharExtra(adjustName, value)
                is Double -> intent.getDoubleExtra(adjustName, value)
                is Float -> intent.getFloatExtra(adjustName, value)
                is Int -> intent.getIntExtra(adjustName, value)
                is Long -> intent.getLongExtra(adjustName, value)
                is Short -> intent.getShortExtra(adjustName, value)
                is CharSequence -> intent.getStringExtra(adjustName) ?: defValue
                else -> IllegalArgumentException("Illegal value type ${T::class.java} for key \"$adjustName\"")
            } as T
        }

/**
 * Retrieves a references type of extended data from intent lazily.
 *
 * @param name The name of the desired item.
 * @param defValue The value to be returned if no value of the desired type is stored with the given name.
 *
 * @throws IllegalArgumentException When a value is not a supported type of [Bundle].
 */
@JvmSynthetic
inline fun <reified T> FragmentActivity.bundle(
    name: String? = null,
    crossinline defValue: () -> T = { null as T }
): FieldLazy<T> {
    val objectType = T::class.java
    return lazyField {
        @Suppress("UNCHECKED_CAST")
        val adjustName = name ?: it.name
        when {
            // references
            Bundle::class.java.isAssignableFrom(objectType) -> intent.getBundleExtra(adjustName) as T
            CharSequence::class.java.isAssignableFrom(objectType) -> intent.getCharArrayExtra(adjustName) as T
            Parcelable::class.java.isAssignableFrom(objectType) -> intent.getParcelableExtra<Parcelable>(adjustName) as T
            Serializable::class.java.isAssignableFrom(objectType) -> intent.getSerializableExtra(adjustName) as T
            // scalar arrays
            BooleanArray::class.java.isAssignableFrom(objectType) -> intent.getBooleanArrayExtra(adjustName) as T
            ByteArray::class.java.isAssignableFrom(objectType) -> intent.getByteArrayExtra(adjustName) as T
            CharArray::class.java.isAssignableFrom(objectType) -> intent.getCharArrayExtra(adjustName) as T
            DoubleArray::class.java.isAssignableFrom(objectType) -> intent.getDoubleArrayExtra(adjustName) as T
            FloatArray::class.java.isAssignableFrom(objectType) -> intent.getFloatArrayExtra(adjustName) as T
            IntArray::class.java.isAssignableFrom(objectType) -> intent.getIntArrayExtra(adjustName) as T
            LongArray::class.java.isAssignableFrom(objectType) -> intent.getLongArrayExtra(adjustName) as T
            ShortArray::class.java.isAssignableFrom(objectType) -> intent.getShortArrayExtra(adjustName) as T

            else -> throw IllegalArgumentException("Illegal value type $objectType for key \"$adjustName\"")
        } ?: defValue()
    }
}

/**
 * Retrieves a references array type of extended data from intent lazily.
 *
 * @param name The name of the desired item.
 * @param defValue The value to be returned if no value of the desired type is stored with the given name.
 *
 * @throws IllegalArgumentException When a value is not a supported type of [Bundle].
 */
@JvmSynthetic
inline fun <reified T> Activity.bundleArray(
    name: String? = null,
    crossinline defValue: () -> Array<T>? = { null }
): FieldLazy<Array<*>?> {
    val javaObjectType = T::class.java
    return lazyField {
        @Suppress("UNCHECKED_CAST")
        val adjustName = name ?: it.name
        when {
            String::class.java.isAssignableFrom(javaObjectType) -> intent.getStringArrayExtra(adjustName)
            CharSequence::class.java.isAssignableFrom(javaObjectType) -> intent.getCharSequenceArrayExtra(adjustName)
            Parcelable::class.java.isAssignableFrom(javaObjectType) -> intent.getParcelableArrayExtra(adjustName)
            else -> throw IllegalArgumentException("Illegal value type $javaObjectType for key \"$adjustName\"")
        } ?: defValue()
    }
}

/**
 * Retrieves a references array list type of extended data from intent lazily.
 *
 * @param name The name of the desired item.
 * @param defValue The value to be returned if no value of the desired type is stored with the given name.
 *
 * @throws IllegalArgumentException When a value is not a supported type of [Bundle].
 */
@JvmSynthetic
inline fun <reified T> Activity.bundleArrayList(
    name: String? = null,
    crossinline defValue: () -> ArrayList<T>? = { null },
): FieldLazy<ArrayList<*>?> {
    val javaObjectType = T::class.java
    return lazyField {
        val adjustName = name ?: it.name
        @Suppress("UNCHECKED_CAST")
        when {
            String::class.java.isAssignableFrom(javaObjectType) -> intent.getStringArrayListExtra(adjustName)
            CharSequence::class.java.isAssignableFrom(javaObjectType) -> intent.getCharSequenceArrayListExtra(adjustName)
            Parcelable::class.java.isAssignableFrom(javaObjectType) -> intent.getParcelableArrayListExtra<Parcelable>(adjustName)
            else -> throw IllegalArgumentException("Illegal value type $javaObjectType for key \"$adjustName\"")
        } ?: defValue()
    }
}
//</editor-fold>


//<editor-fold desc="Fragment">
/**
 * Retrieves a primitive type of extended data from intent lazily.
 *
 * @param name The name of the desired item.
 * @param defValue The value to be returned if no value of the desired type is stored with the given name.
 *
 * @throws IllegalArgumentException When a value is not a supported type of [Bundle].
 */
@JvmSynthetic
inline fun <reified T> Fragment.bundle(defValue: T, name: String? = null): FieldLazy<T> =
        lazyField {
            val adjustName = name ?: it.name
            when (val value: T = defValue) {
                is Boolean -> arguments?.getBoolean(adjustName, value)
                is Byte -> arguments?.getByte(adjustName, value)
                is Char -> arguments?.getChar(adjustName, value)
                is Double -> arguments?.getDouble(adjustName, value)
                is Float -> arguments?.getFloat(adjustName, value)
                is Int -> arguments?.getInt(adjustName, value)
                is Long -> arguments?.getLong(adjustName, value)
                is Short -> arguments?.getShort(adjustName, value)
                is CharSequence -> arguments?.getString(adjustName)
                else -> IllegalArgumentException("Illegal value type ${T::class.java} for key \"$adjustName\"")
            } as T ?: defValue
        }

/**
 * Retrieves a references type of extended data from intent lazily.
 *
 * @param name The name of the desired item.
 * @param defValue The value to be returned if no value of the desired type is stored with the given name.
 *
 * @throws IllegalArgumentException When a value is not a supported type of [Bundle].
 */
@JvmSynthetic
inline fun <reified T> Fragment.bundle(
    crossinline defValue: () -> T? = { null },
    name: String? = null
): FieldLazy<T?> {
    val objectType = T::class.java
    return lazyField {
        @Suppress("UNCHECKED_CAST")
        val adjustName = name ?: it.name
        when {
            // references
            Bundle::class.java.isAssignableFrom(objectType) -> arguments?.getBundle(adjustName) as T
            CharSequence::class.java.isAssignableFrom(objectType) -> arguments?.getChar(adjustName) as T
            Parcelable::class.java.isAssignableFrom(objectType) -> arguments?.getParcelable<Parcelable>(adjustName) as T
            Serializable::class.java.isAssignableFrom(objectType) -> arguments?.getSerializable(adjustName) as T
            // scalar arrays
            BooleanArray::class.java.isAssignableFrom(objectType) -> arguments?.getBooleanArray(adjustName) as T
            ByteArray::class.java.isAssignableFrom(objectType) -> arguments?.getByteArray(adjustName) as T
            CharArray::class.java.isAssignableFrom(objectType) -> arguments?.getCharArray(adjustName) as T
            DoubleArray::class.java.isAssignableFrom(objectType) -> arguments?.getDoubleArray(adjustName) as T
            FloatArray::class.java.isAssignableFrom(objectType) -> arguments?.getFloatArray(adjustName) as T
            IntArray::class.java.isAssignableFrom(objectType) -> arguments?.getIntArray(adjustName) as T
            LongArray::class.java.isAssignableFrom(objectType) -> arguments?.getLongArray(adjustName) as T
            ShortArray::class.java.isAssignableFrom(objectType) -> arguments?.getShortArray(adjustName) as T
            else -> throw IllegalArgumentException("Illegal value type $objectType for key \"$adjustName\"")
        } ?: defValue()
    }
}

/**
 * Retrieves a references array type of extended data from intent lazily.
 *
 * @param name The name of the desired item.
 * @param defValue The value to be returned if no value of the desired type is stored with the given name.
 *
 * @throws IllegalArgumentException When a value is not a supported type of [Bundle].
 */
@JvmSynthetic
inline fun <reified T> Fragment.bundleArray(
    crossinline defValue: () -> Array<T>? = { null },
    name: String? = null
): FieldLazy<Array<*>?> {
    val javaObjectType = T::class.java
    return lazyField {
        val adjustName = name ?: it.name
        @Suppress("UNCHECKED_CAST")
        when {
            String::class.java.isAssignableFrom(javaObjectType) -> arguments?.getStringArray(adjustName)
            CharSequence::class.java.isAssignableFrom(javaObjectType) -> arguments?.getCharSequenceArray(adjustName)
            Parcelable::class.java.isAssignableFrom(javaObjectType) -> arguments?.getParcelableArray(adjustName)
            else -> throw IllegalArgumentException("Illegal value type $javaObjectType for key \"$adjustName\"")
        } ?: defValue()
    }
}

/**
 * Retrieves a references array list type of extended data from intent lazily.
 *
 * @param name The name of the desired item.
 * @param defValue The value to be returned if no value of the desired type is stored with the given name.
 *
 * @throws IllegalArgumentException When a value is not a supported type of [Bundle].
 */
@JvmSynthetic
inline fun <reified T> Fragment.bundleArrayList(
    crossinline defValue: () -> ArrayList<T>? = { null },
    name: String? = null
): FieldLazy<ArrayList<*>?> {
    val javaObjectType = T::class.java
    return lazyField {
        val adjustName = name ?: it.name
        @Suppress("UNCHECKED_CAST")
        when {
            String::class.java.isAssignableFrom(javaObjectType) -> arguments?.getStringArrayList(adjustName)
            CharSequence::class.java.isAssignableFrom(javaObjectType) -> arguments?.getCharSequenceArrayList(adjustName)
            Parcelable::class.java.isAssignableFrom(javaObjectType) -> arguments?.getParcelableArrayList(adjustName)
            else -> throw IllegalArgumentException("Illegal value type $javaObjectType for key \"$adjustName\"")
        } ?: defValue()
    }
}
//</editor-fold>
