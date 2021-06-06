package com.qzeng.focustask

import android.app.Application
import com.qzeng.focustask.ui.TaskManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {
    @Inject
    lateinit var taskManager: TaskManager
    override fun onCreate() {
        super.onCreate()
        taskManager.init()
    }
}