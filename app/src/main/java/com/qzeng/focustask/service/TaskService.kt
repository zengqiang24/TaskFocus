package com.qzeng.focustask.service

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import com.qzeng.focustask.aidl.ICallBack
import com.qzeng.focustask.aidl.ITaskService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TaskService : Service() {
    //define a scope of a Coroutine. in main thread.
    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

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
        override fun getCurrentTaskInfo(): Bundle {
            return Bundle().apply { putParcelable("TaskInfo", taskManager.currentTaskInfo) }
        }

        override fun start(type: Int) {
            coroutineScope.launch {
                if (taskManager.currentTaskInfo.state == TASK_STATE_PAUSE) {
                    taskManager.resume()
                } else {
                    val taskInfo = createTask(type)
                    taskManager.start(taskInfo)
                }
            }
        }

        override fun resume() {

        }

        override fun unRegisterCallback(callback: ICallBack) {
            coroutineScope.launch {
                taskManager.removeTaskStateChangedCallback(callback)
            }
        }


        override fun pause() {
            taskManager.pause()
        }


        override fun registerCallBack(callback: ICallBack) {
            taskManager.addTaskStateChangedCallback(callback)
        }

    }
}