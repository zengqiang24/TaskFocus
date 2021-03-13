package com.qzeng.focustask.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.qzeng.focustask.aidl.ICallBack
import com.qzeng.focustask.aidl.ITaskService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TaskService : Service() {
    @Inject
    lateinit var taskManager: TimeTaskManager
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return mIBinder;
    }

    private fun showNotification() {

    }

    //
//    //Server Binder
    private val mIBinder = object : ITaskService.Stub() {
        override fun setTaskType(type: Int) {
        }

        override fun start() {
            taskManager.start()
        }

        override fun unRegisterCallback(callback: ICallBack) {
            taskManager.removeTaskStateChangedListener(callback)
        }

        override fun reset() {
        }

        override fun pause() {
            taskManager.pause()
        }

        override fun getCurrentTaskTime(): Long {
            return taskManager.currentTaskInfo.currentTime
        }

        override fun registerCallBack(callback: ICallBack) {
            taskManager.addTaskStateChangedListener(callback)
        }


    }
}