package com.qzeng.focustask.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.qzeng.focustask.aidl.IRemoteService

class TaskService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }
    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

   private val binder = object : IRemoteService.Stub() {
        override fun getPID() {
            Log.d("qiang", "binder: called getPID")
        }

        override fun getCurrentTaskTime(): Long {
            Log.d("qiang", "binder:getCurrentTaskTime")
            return 1
        }
    }
}