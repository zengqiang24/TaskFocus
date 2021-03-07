package com.qzeng.focustask.service

import com.qzeng.focustask.model.TaskInfo
import com.qzeng.focustask.utils.AppLogger
import java.util.concurrent.CopyOnWriteArraySet
import javax.inject.Inject
import javax.inject.Singleton

val DEFAULT_TIME_INERNAL = 25 * 60 * 1000L
private const val TAG = "TimeComputeManager"

@Singleton
class TimeTaskManager @Inject constructor() {
    lateinit var currentTaskInfo: TaskInfo
    private val taskStateChangedListeners = CopyOnWriteArraySet<TaskStateChangedListener>()
    fun decrease() {
        if (!currentTaskInfo.isPaused && currentTaskInfo.currentTime > 0)
            currentTaskInfo.currentTime = currentTaskInfo.currentTime - 1000L
        notifyTaskState(currentTaskInfo)
    }

    fun pause() {
        currentTaskInfo.isPaused = true
        notifyTaskState(currentTaskInfo)
        AppLogger.getInstance().d(TAG, "pause called in viewModel")
    }

    fun reset() {
        currentTaskInfo.isPaused = true
        currentTaskInfo.currentTime = DEFAULT_TIME_INERNAL
        notifyTaskState(currentTaskInfo)
        AppLogger.getInstance().d(TAG, "reset called in viewModel")
    }

    fun start() {
        currentTaskInfo = TaskInfo(DEFAULT_TIME_INERNAL)
        AppLogger.getInstance().d(TAG, "start called in viewModel")
        currentTaskInfo.isPaused = false
        decrease()
    }

    //listener set


    fun addTaskStateChangedListener(listener: TaskStateChangedListener): Boolean {
        return taskStateChangedListeners.add(listener)
    }

    fun removeTaskStateChangedListener(listener: TaskStateChangedListener): Boolean {
        return taskStateChangedListeners.remove(listener)
    }

    private fun notifyTaskState(currentTaskInfo: TaskInfo) {
        for (taskStateChangedListener in taskStateChangedListeners) {
            taskStateChangedListener.onTaskStateChanged(currentTaskInfo)
        }
    }

    //notify current time changed, task state.
    interface TaskStateChangedListener {
        fun onTaskStateChanged(taskInfo: TaskInfo)
    }
}