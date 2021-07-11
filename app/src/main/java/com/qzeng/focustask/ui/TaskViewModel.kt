package com.qzeng.focustask.ui

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.qzeng.focustask.model.TaskInfo
import com.qzeng.focustask.service.isDone
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val taskManager: TaskManager)
    : ViewModel(), LifecycleObserver, TaskManager.TaskListener {
    val currentTime: ObservableField<String> = ObservableField()
    val prompt: ObservableField<String> = ObservableField("开始25分钟专注")
    private var _taskInfo: TaskInfo? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        taskManager.registerTaskListener(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        taskManager.unRegisterTaskListener(this)
    }

    fun start() {
        _taskInfo?.let { taskManager.start(it) }
    }

    fun pause() {
        taskManager.pause()
    }

    override fun onTaskStateChanged(bundle: Bundle) {
        val taskInfo = bundle.getParcelable<TaskInfo>("TaskInfo")
        taskInfo?.let {
            _taskInfo = taskInfo
            currentTime.set(it.getCurrentTime())
            prompt.set(if (it.isDone()) it.getNextTaskPrompt() else it.getPrompt())
        }
    }

    companion object {
        private val TAG = "ScheduleTaskViewModel"
    }

}

