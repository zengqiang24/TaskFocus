package com.qzeng.focustask

import android.app.Application
import android.util.Log
import com.qzeng.focustask.ui.TaskManager
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.Callable
import java.util.concurrent.Future
import java.util.concurrent.FutureTask
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {
    @Inject
    lateinit var taskManager: TaskManager
    override fun onCreate() {
        super.onCreate()
    }

    class MyCallable : Callable<String> {
        override fun call(): String {
            Thread.sleep(20000)
            return "Task Done!"
        }
    }

}