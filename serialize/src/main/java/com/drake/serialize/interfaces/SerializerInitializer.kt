package com.drake.serialize.interfaces

import android.content.Context
import androidx.startup.Initializer
import com.tencent.mmkv.MMKV
import com.tencent.mmkv.MMKVLogLevel

internal class SerializerInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        MMKV.initialize(context, MMKVLogLevel.LevelNone)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}