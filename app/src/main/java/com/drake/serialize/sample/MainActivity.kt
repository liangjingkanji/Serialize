package com.drake.serialize.sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.drake.serialize.delegateSerialize
import com.drake.serialize.deserialize
import com.drake.serialize.lazyField
import com.drake.serialize.sample.model.Model
import com.drake.serialize.serialize
import com.hulab.debugkit.dev
import com.tencent.mmkv.MMKV
import openActivity


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private var name: Boolean by delegateSerialize(false, model = MMKV.MULTI_PROCESS_MODE)
    private var model: Model by delegateSerialize()

    private val age by lazyField {
        System.currentTimeMillis()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dev {
            function {
                // model = Model("吴彦祖")
                // name = true
                serialize("naa" to listOf(Model("4444")))
                // serialize("na" to setOf("36"))
            }
            function {
                // Log.d("日志", "(MainActivity.kt:32)    model = ${model.name}")
                // Log.d("日志", "(MainActivity.kt:32)    name = ${name}")

                // val data: Set<Model>? = deserialize("na", setOf(Model("高新园")))
                // val data: Set<String>? = deserialize("na", setOf("高新园"))
                // val data: Set<String>? = deserialize("na")
                val data: List<Model>? = deserialize("naa")
                Log.d("日志", "(MainActivity.kt:38)    data = ${data}")
            }
            function {
                openActivity<MainActivity2>("transformData" to 34)
            }
        }
    }
}