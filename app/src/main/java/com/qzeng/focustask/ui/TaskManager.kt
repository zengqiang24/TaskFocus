package com.qzeng.focustask.ui

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.qzeng.focustask.aidl.ICallBack
import com.qzeng.focustask.aidl.ITaskService
import com.qzeng.focustask.service.TaskService
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.CopyOnWriteArraySet
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskManager @Inject constructor(@ApplicationContext val mContext: Context) : ServiceConnection {
    private val TAG = "TaskManager"
    var iTaskService: ITaskService? = null
    var mIndexUITaskCallback: IndexUITaskCallback? = null
    private val taskListeners = CopyOnWriteArraySet<TaskListener>()

    fun init() {
        val intent = Intent(mContext, TaskService::class.java)
        mContext.startService(intent)
        mContext.bindService(intent, this, BIND_AUTO_CREATE)
    }

    fun registerTaskListener(listener: TaskListener) {
        taskListeners.add(listener)
        iTaskService?.currentTaskTime?.run {
            listener.onProgress(this)
        }
    }

    fun unRegisterTaskListener(listener: TaskListener) {
        taskListeners.remove(listener)
    }

    fun start() {
        iTaskService?.start()
    }

    fun pause() {
        iTaskService?.pause()
    }


    fun destroy() {
        Log.d(TAG, "destroy() called")
        iTaskService?.unRegisterCallback(mIndexUITaskCallback)
        mContext.unbindService(this)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        destroy()
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        iTaskService = ITaskService.Stub.asInterface(service)
        mIndexUITaskCallback = IndexUITaskCallback(taskListeners)
        iTaskService?.registerCallBack(mIndexUITaskCallback)
    }

    class IndexUITaskCallback(private val taskListeners: CopyOnWriteArraySet<TaskListener>) : ICallBack.Stub() {
        override fun onTaskStateChanged(type: Int, state: Int) {
            for (taskListener in taskListeners) {
                taskListener.onTaskStateChanged(type, state)
            }
        }

        override fun onProgress(time: Long) {
            for (taskListener in taskListeners) {
                taskListener.onProgress(time)
            }
        }
    }

    interface TaskListener {
        fun onTaskStateChanged(type: Int, state: Int)

        fun onProgress(time: Long)
    }
}