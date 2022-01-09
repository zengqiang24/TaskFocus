package com.qzeng.focustask.initializer

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.qzeng.focustask.utils.AppLogger

private const val TAG = "AppLoggerInitializer"

class AppLoggerInitializer : Initializer<AppLogger> {
    override fun create(context: Context): AppLogger {
        Log.d(TAG, "logger inited...")
        return AppLogger.getInstance()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return ArrayList()
    }
}