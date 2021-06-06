package com.qzeng.focustask.ui

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.qzeng.focustask.aidl.ICallBack
import com.qzeng.focustask.aidl.ITaskService
import com.qzeng.focustask.service.TaskService
import com.qzeng.focustask.service.WORK_TASK_TYPE
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
        iTaskService?.currentTaskInfo?.run {
            listener.onTaskStateChanged(this)
        }
    }

    fun unRegisterTaskListener(listener: TaskListener) {
        taskListeners.remove(listener)
    }

    fun start() {
        iTaskService?.start(WORK_TASK_TYPE)
    }

    fun resume() {
        iTaskService?.resume()
    }

    fun pause() {
        iTaskService?.pause()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        Log.d(TAG, "onServiceDisconnected() called")
        iTaskService?.unRegisterCallback(mIndexUITaskCallback)
        mContext.unbindService(this)
        iTaskService = null
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        iTaskService = ITaskService.Stub.asInterface(service)
        mIndexUITaskCallback = IndexUITaskCallback(taskListeners)
        iTaskService?.registerCallBack(mIndexUITaskCallback)
    }

    class IndexUITaskCallback(private val taskListeners: CopyOnWriteArraySet<TaskListener>) : ICallBack.Stub() {

        override fun onTaskStateChanged(bundle: Bundle) {
            for (taskListener in taskListeners) {
                taskListener.onTaskStateChanged(bundle)
            }
        }
    }

    interface TaskListener {
        fun onTaskStateChanged(bundle: Bundle)
    }
}