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


@file:Suppress("unused")

import android.app.Activity
import android.app.Service
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import java.io.Serializable

// <editor-fold desc="跳转">

inline fun <reified T : Activity> Context.openActivity(vararg params: Pair<String, Any?>) {
    val intent = createIntent<T>(params)
    if (this !is Activity) intent.newTask()
    startActivity(intent)
}

inline fun <reified T : Activity> Fragment.openActivity(vararg params: Pair<String, Any?>) =
    context?.openActivity<T>(*params)


inline fun <reified T : Activity> Activity.openActivityForResult(
    requestCode: Int,
    vararg params: Pair<String, Any?>
) = startActivityForResult(createIntent<T>(params), requestCode)


inline fun <reified T : Activity> Fragment.openActivityForResult(
    requestCode: Int,
    vararg params: Pair<String, Any?>
) = activity?.openActivityForResult<T>(requestCode, *params)


inline fun <reified T : Service> Context.startService(vararg params: Pair<String, Any?>) =
    startService(createIntent<T>(params))

inline fun <reified T : Service> Context.stopService(vararg params: Pair<String, Any?>) =
    stopService(createIntent<T>(params))

inline fun <reified T : Service> Fragment.startService(vararg params: Pair<String, Any?>) =
    context?.startService<T>(*params)

inline fun <reified T : Service> Fragment.stopService(vararg params: Pair<String, Any?>) =
    context?.stopService<T>(*params)

inline fun <reified T : Any> Context.intentFor(vararg params: Pair<String, Any?>): Intent =
    createIntent<T>(params)


inline fun <reified T : Any> Fragment.intentFor(vararg params: Pair<String, Any?>): Intent =
    context?.createIntent<T>(params) ?: Intent()

// </editor-fold>

// <editor-fold desc="回退栈">
/**
 * Add the [Intent.FLAG_ACTIVITY_CLEAR_TASK] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
fun Intent.clearTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) }

/**
 * Add the [Intent.FLAG_ACTIVITY_CLEAR_TOP] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
fun Intent.clearTop(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) }

/**
 * Add the [Intent.FLAG_ACTIVITY_NEW_DOCUMENT] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
fun Intent.newDocument(): Intent = apply {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
    } else {
        @Suppress("DEPRECATION")
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
    }
}

/**
 * Add the [Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
fun Intent.excludeFromRecents(): Intent =
    apply { addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS) }

/**
 * Add the [Intent.FLAG_ACTIVITY_MULTIPLE_TASK] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
fun Intent.multipleTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK) }

/**
 * Add the [Intent.FLAG_ACTIVITY_NEW_TASK] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
fun Intent.newTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }

/**
 * Add the [Intent.FLAG_ACTIVITY_NO_ANIMATION] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
fun Intent.noAnimation(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION) }

/**
 * Add the [Intent.FLAG_ACTIVITY_NO_HISTORY] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
fun Intent.noHistory(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) }

/**
 * Add the [Intent.FLAG_ACTIVITY_SINGLE_TOP] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
fun Intent.singleTop(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP) }

// </editor-fold>


// <editor-fold desc="意图">

fun Fragment.browse(url: String, newTask: Boolean = false) = activity?.browse(url, newTask)

fun Context.browse(url: String, newTask: Boolean = false): Boolean {
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        if (newTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        return true
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        return false
    }
}


fun Fragment.share(text: String, subject: String = "", title: String? = null) =
    activity?.share(text, subject, title)

fun Context.share(text: String, subject: String = "", title: String? = null): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(intent, title))
        true
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        false
    }
}


fun Fragment.email(email: String, subject: String = "", text: String = "") =
    activity?.email(email, subject, text)

fun Context.email(email: String, subject: String = "", text: String = ""): Boolean {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.data = Uri.parse("mailto:")
    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
    if (subject.isNotEmpty())
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    if (text.isNotEmpty())
        intent.putExtra(Intent.EXTRA_TEXT, text)
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
        return true
    }
    return false

}

fun Fragment.makeCall(number: String): Boolean = activity?.makeCall(number) ?: false

fun Context.makeCall(number: String): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$number"))
        startActivity(intent)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

fun Fragment.sendSMS(number: String, text: String = ""): Boolean =
    activity?.sendSMS(number, text) ?: false

fun Context.sendSMS(number: String, text: String = ""): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:$number"))
        intent.putExtra("sms_body", text)
        startActivity(intent)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

fun <T : Fragment> T.withArguments(vararg params: Pair<String, Any?>): T {
    arguments = bundleOf(*params)
    return this
}

/**
 * 创建意图
 * @param clazz Class<out T>
 * @param params Array<out Pair<String, Any?>>
 * @return Intent
 */
inline fun <reified T> Context.createIntent(params: Array<out Pair<String, Any?>>): Intent {
    val intent = Intent(this, T::class.java)
    if (params.isNotEmpty()) intent.withArguments(params)
    return intent
}

/**
 * 意图添加数据
 * @param intent Intent
 * @param params Array<out Pair<String, Any?>>
 */
fun Intent.withArguments(params: Array<out Pair<String, Any?>>) {
    params.forEach {
        when (val value = it.second) {
            null -> putExtra(it.first, null as Serializable?)
            is Int -> putExtra(it.first, value)
            is Long -> putExtra(it.first, value)
            is CharSequence -> putExtra(it.first, value)
            is String -> putExtra(it.first, value)
            is Float -> putExtra(it.first, value)
            is Double -> putExtra(it.first, value)
            is Char -> putExtra(it.first, value)
            is Short -> putExtra(it.first, value)
            is Boolean -> putExtra(it.first, value)
            is Serializable -> putExtra(it.first, value)
            is Bundle -> putExtra(it.first, value)
            is Parcelable -> putExtra(it.first, value)
            is Array<*> -> when {
                value.isArrayOf<CharSequence>() -> putExtra(it.first, value)
                value.isArrayOf<String>() -> putExtra(it.first, value)
                value.isArrayOf<Parcelable>() -> putExtra(it.first, value)
                else -> throw IllegalArgumentException("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
            }
            is IntArray -> putExtra(it.first, value)
            is LongArray -> putExtra(it.first, value)
            is FloatArray -> putExtra(it.first, value)
            is DoubleArray -> putExtra(it.first, value)
            is CharArray -> putExtra(it.first, value)
            is ShortArray -> putExtra(it.first, value)
            is BooleanArray -> putExtra(it.first, value)
            else -> throw IllegalArgumentException("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
        }
        return@forEach
    }
}

// </editor-fold>

