package com.qzeng.focustask.ui

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.orhanobut.logger.Logger
import com.qzeng.focustask.model.TaskInfo
import com.qzeng.focustask.service.isDone
import com.qzeng.focustask.utils.formatDateToString
import javax.inject.Inject

class TaskViewModel @Inject constructor(private val taskManager: TaskManager) : ViewModel(), LifecycleObserver, TaskManager.TaskListener {
    private val TAG = "ScheduleTaskViewModel"
    val currentTime: ObservableField<String> = ObservableField()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        taskManager.registerTaskListener(this)

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        taskManager.unRegisterTaskListener(this)
    }

    fun start() {
        taskManager.start()
    }

    fun pause() {
        taskManager.pause()
    }

    override fun onTaskStateChanged(bundle: Bundle) {
        bundle.classLoader = javaClass.classLoader
        val taskInfo = bundle.getParcelable<TaskInfo>("TaskInfo")
        taskInfo?.let {
            Logger.t(TAG).d("task type = ${taskInfo.type}; task state = ${taskInfo.state} task.time = ${taskInfo.currentTime}")
            currentTime.set(formatDateToString(taskInfo.currentTime))
            if (taskInfo.isDone()) {

            }
        }
    }
}

