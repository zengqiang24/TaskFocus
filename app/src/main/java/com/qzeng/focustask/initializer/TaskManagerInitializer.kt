package com.qzeng.focustask.initializer

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.qzeng.focustask.ui.TaskManager

private const val TAG = "TaskManagerInitializer"

class TaskManagerInitializer : Initializer<TaskManager> {
    override fun create(context: Context): TaskManager {
        Log.d(TAG, "TaskManagerInitializer inited...")
        return TaskManager(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(AppLoggerInitializer::class.java, ImageLoaderInitializer::class.java)
    }
}