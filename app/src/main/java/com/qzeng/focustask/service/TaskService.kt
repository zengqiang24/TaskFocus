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
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

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
        private var _taskQueueIndex = 0

        //define a scope of a Coroutine. in main thread.
        private val _coroutineScope = CoroutineScope(Dispatchers.Default)
        override fun getCurrentTaskInfo(): Bundle {
            return Bundle().apply { putParcelable("TaskInfo", manager.currentTaskInfo) }
        }

        override fun start(type: Bundle) {
            _coroutineScope.launch {
                val taskInfo = type.getParcelable<TaskInfo>("TaskInfo")
                if (taskInfo != null) {
                    manager.start(taskInfo)
                }
            }
        }

        override fun startTaskQueue() {
            _coroutineScope.launch {
                val taskList = getQueue()
                if (_taskQueueIndex < taskList.size && taskList.size >= 0) {
                    val taskInfo = taskList[_taskQueueIndex++]
                    manager.start(taskInfo)
                }
            }
        }

        override fun resume() {
            _coroutineScope.launch {
                manager.resume()
            }
        }

        override fun unRegisterCallback(callback: ICallBack) {
            _coroutineScope.launch {
                manager.removeTaskStateChangedCallback(callback)
            }
        }

        override fun getTaskRegularQueue(): Bundle {
            return Bundle().apply {
                this.putParcelableArrayList("queue", getQueue())
            }
        }


        override fun pause() {
            _coroutineScope.coroutineContext.cancelChildren()
            _coroutineScope.launch {
                manager.pause()
            }
        }

        override fun cancel() {
            _coroutineScope.coroutineContext.cancelChildren()
            _coroutineScope.launch {
                manager.cancel()
            }
        }

        override fun registerCallBack(callback: ICallBack) {
            manager.addTaskStateChangedCallback(callback)
        }

        fun getQueue(): ArrayList<TaskInfo> {
            return ArrayList<TaskInfo>()
                .apply {
                    add(createTask(WORK_TASK_TYPE))
                    add(createTask(REST_TASK_TYPE))
                }
        }
    }
}