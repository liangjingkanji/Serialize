package com.drake.serialize.sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.drake.serialize.bundle

class MainActivity2 : AppCompatActivity(R.layout.activity_main2) {

    private val transformData: Int? by bundle { 23 }
    private val type: Int? by bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("日志", "(MainActivity2.kt:15)    transformData = ${transformData}, type = ${type}")
    }
}
