package com.drake.serialize.sample

import android.app.Application
import com.tencent.mmkv.MMKV
import com.tencent.mmkv.MMKVLogLevel

class App:Application() {

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this, MMKVLogLevel.LevelNone)
    }
}