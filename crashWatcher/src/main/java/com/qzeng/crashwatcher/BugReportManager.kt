package com.qzeng.crashwatcher

import android.util.Log

object BugReportManager {
    private val TAG = "BugReportManager"
    fun watchCrash() {
        Thread.setDefaultUncaughtExceptionHandler { t, e -> Log.d(TAG, "uncaughtException: t = ${t.name} , e = ${e.message}") }
    }


    external fun stringFromJNI() : String?

    init {
        System.loadLibrary("native-crash-lib")
    }
}