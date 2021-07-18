package com.qzeng.focustask.service

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import com.qzeng.focustask.aidl.ICallBack
import com.qzeng.focustask.aidl.ITaskService
import com.qzeng.focustask.model.TaskInfo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TaskService : Service() {
    private var _myBinder: MyBinder? = null

    @Inject
    lateinit var taskManager: TimeTaskManager
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        if (_myBinder == null) {
            _myBinder = MyBinder(taskManager)
        }
        return _myBinder;
    }

    private fun showNotification() {
    }

    class MyBinder(private val manager: TimeTaskManager) : ITaskService.Stub() {
        //define a scope of a Coroutine. in main thread.
        private val coroutineScope = CoroutineScope(Dispatchers.Default)
        override fun getCurrentTaskInfo(): Bundle {
            return Bundle().apply { putParcelable("TaskInfo", manager.currentTaskInfo) }
        }

        override fun start(type: Bundle) {
            coroutineScope.launch {
                val taskInfo = type.getParcelable<TaskInfo>("TaskInfo")
                if (taskInfo != null) {
                    manager.start(taskInfo)
                }
            }
        }

        override fun resume() {
            coroutineScope.launch {
                manager.resume()
            }
        }

        override fun unRegisterCallback(callback: ICallBack) {
            coroutineScope.launch {
                manager.removeTaskStateChangedCallback(callback)
            }
        }


        override fun pause() {
            coroutineScope.coroutineContext.cancelChildren()
            coroutineScope.launch {
                manager.pause()
            }
        }

        override fun cancel() {
            coroutineScope.coroutineContext.cancelChildren()
            coroutineScope.launch {
                manager.cancel()
            }
        }

        override fun registerCallBack(callback: ICallBack) {
            manager.addTaskStateChangedCallback(callback)
        }

    }
}