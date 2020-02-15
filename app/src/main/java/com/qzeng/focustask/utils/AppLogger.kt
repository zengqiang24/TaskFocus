package com.qzeng.focustask.utils

import android.util.Log

object  AppLogger {
  private val appTag: String = "[Focus]"
  fun d(tag: String, msg: String) {
    Log.d(appTag + tag, msg)
  }

  fun i(tag: String, msg: String) {
    Log.i(appTag + tag, msg)
  }

  fun w(tag: String, msg: String) {
    Log.w(appTag + tag, msg)
  }

  fun e(tag: String, msg: String) {
    Log.e(appTag + tag, msg)
  }

}