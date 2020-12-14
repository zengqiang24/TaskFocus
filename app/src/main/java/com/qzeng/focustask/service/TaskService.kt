package com.qzeng.focustask.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.qzeng.focustask.aidl.ITaskService
import com.qzeng.focustask.utils.AppLogger

class TaskService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return super.onStartCommand(intent, flags, startId)
    }
    override fun onBind(intent: Intent?): IBinder? {
       return null
    }

    private fun showNotification(){
         
    }
//
//    //Server Binder
   private val mIBinder = object : ITaskService.Stub() {
    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun getCurrentTaskTime(): Long {
        TODO("Not yet implemented")
    }

    override fun registerCallBack() {
        TODO("Not yet implemented")
    }

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun reset() {
        TODO("Not yet implemented")
    }

}
}