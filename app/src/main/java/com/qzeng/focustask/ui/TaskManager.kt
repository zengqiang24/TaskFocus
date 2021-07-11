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
import com.qzeng.focustask.model.TaskInfo
import com.qzeng.focustask.service.TaskService
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.CopyOnWriteArraySet
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskManager @Inject constructor(@ApplicationContext val context: Context) : ServiceConnection {
    private val TAG = "TaskManager"
    private var iTaskService: ITaskService? = null
    private var indexUITaskCallback: IndexUITaskCallback? = null
    private val taskListeners = CopyOnWriteArraySet<TaskListener>()
    fun init() {
        val intent = Intent(context, TaskService::class.java)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
        context.bindService(intent, this, BIND_AUTO_CREATE)
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

    fun start(taskInfo: TaskInfo) {
        val bundle = Bundle().apply { putParcelable("TaskInfo", taskInfo) }
        iTaskService?.start(bundle)
    }

    fun resume() {
        iTaskService?.resume()
    }

    fun pause() {
        iTaskService?.pause()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        Log.d(TAG, "onServiceDisconnected() called")
        iTaskService?.unRegisterCallback(indexUITaskCallback)
        context.unbindService(this)
        iTaskService = null
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        iTaskService = ITaskService.Stub.asInterface(service)
        indexUITaskCallback = IndexUITaskCallback(taskListeners)
        iTaskService?.registerCallBack(indexUITaskCallback)
    }

    class IndexUITaskCallback(private val taskListeners: CopyOnWriteArraySet<TaskListener>) : ICallBack.Stub() {

        override fun onTaskStateChanged(bundle: Bundle) {
            bundle.classLoader = javaClass.classLoader
            for (taskListener in taskListeners) {
                taskListener.onTaskStateChanged(bundle)
            }
        }
    }

    interface TaskListener {
        fun onTaskStateChanged(bundle: Bundle)
    }
}