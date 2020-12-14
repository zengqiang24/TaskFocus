package com.qzeng.focustask

import android.app.Application
import com.qzeng.focustask.utils.AppLogger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application(){
   override fun onCreate() {
    super.onCreate()
    AppLogger.i("[init]","App start... threadName = ${Thread.currentThread().name}")
  }
}