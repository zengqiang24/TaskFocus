package com.qzeng.focustask.ui

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.orhanobut.logger.Logger
import com.qzeng.focustask.model.TaskInfo
import com.qzeng.focustask.service.REST_TASK_TYPE
import com.qzeng.focustask.service.WORK_TASK_TYPE
import com.qzeng.focustask.service.isDone
import com.qzeng.focustask.utils.formatDateToString
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(val taskManager: TaskManager)
    : ViewModel(), LifecycleObserver, TaskManager.TaskListener {
    private val TAG = "ScheduleTaskViewModel"
    val currentTime: ObservableField<String> = ObservableField()
    private var currentTaskType = WORK_TASK_TYPE
    val prompt: ObservableField<String> = ObservableField("开始25分钟专注")

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        taskManager.registerTaskListener(this)

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        taskManager.unRegisterTaskListener(this)
    }

    fun start() {
        taskManager.start(currentTaskType)
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
                when (taskInfo.type) {
                    WORK_TASK_TYPE -> {  //work task done,  starting rest task
                        currentTaskType = REST_TASK_TYPE
                        prompt.set("开始5分钟休息")
                    }
                    REST_TASK_TYPE -> {
                        currentTaskType = WORK_TASK_TYPE
                        prompt.set("开始25分钟专注")
                    }
                }
            }
        }
    }
}

