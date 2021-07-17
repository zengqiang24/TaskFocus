package com.qzeng.focustask.ui

import android.os.Bundle
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.qzeng.focustask.model.TaskInfo
import com.qzeng.focustask.service.REST_TASK_TYPE
import com.qzeng.focustask.service.WORK_TASK_TYPE
import com.qzeng.focustask.service.createTask
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class TaskViewModel constructor(private val taskManager: TaskManager) : ViewModel(),
    LifecycleObserver, TaskManager.TaskListener {
    val currentTime: ObservableField<String> = ObservableField()
    val prompt: ObservableField<String> = ObservableField("开始25分钟专注")
    val debugInfoOfTaskInfo = ObservableField<String>()
    val isTaskRunning = ObservableBoolean(false)
    val isTaskCompleted = ObservableBoolean(false)

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
        _taskInfo?.let {
            when (it.type) {
//                WORK_TASK_TYPE -> taskManager.start(createTask(REST_TASK_TYPE))
//                REST_TASK_TYPE -> taskManager.start(createTask(WORK_TASK_TYPE))
                else ->
                    taskManager.start(createTask(WORK_TASK_TYPE))
            }
        }
    }

    fun pause() {
        taskManager.pause()
    }

    fun cancel() {
        taskManager.cancel()
    }

    override fun onTaskStateChanged(bundle: Bundle) {
        val taskInfo = bundle.getParcelable<TaskInfo>("TaskInfo")
        taskInfo?.let {
            _taskInfo = taskInfo
            debugInfoOfTaskInfo.set(taskInfo.toString())
            currentTime.set(it.getCurrentTime())
            prompt.set(if (it.isDone()) it.getNextTaskPrompt() else it.getPrompt())
            isTaskRunning.set(it.isStart())
            isTaskCompleted.set(it.isDone())
        }
    }

    companion object {
        private const val TAG = "ScheduleTaskViewModel"
    }

}

