package com.qzeng.focustask

import android.app.Application
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.qzeng.crashwatcher.BugReportManager
import com.qzeng.focustask.utils.AppLogger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

    }
}