package com.qzeng.focustask.service

import com.qzeng.focustask.aidl.ICallBack
import com.qzeng.focustask.model.TaskInfo
import kotlinx.coroutines.*
import java.util.concurrent.CopyOnWriteArraySet
import javax.inject.Inject
import javax.inject.Singleton

val DEFAULT_TIME_INERNAL = 25 * 60  * 1000L
private const val TAG = "TimeComputeManager"

@Singleton
class TimeTaskManager @Inject constructor() {
    var currentTaskInfo = TaskInfo()
    private val taskStateChangedCallbacks = CopyOnWriteArraySet<ICallBack>()
    fun isStarted() = !currentTaskInfo.isPaused
    suspend fun decrease() {
        if (!currentTaskInfo.isPaused && currentTaskInfo.currentTime > 0)
            currentTaskInfo.currentTime = currentTaskInfo.currentTime - 1000L
        notifyTaskState(currentTaskInfo)
    }

    fun pause() {
        currentTaskInfo.isPaused = true
        notifyTaskState(currentTaskInfo)
    }

    fun reset() {
        currentTaskInfo.isPaused = true
        currentTaskInfo.currentTime = DEFAULT_TIME_INERNAL
        notifyTaskState(currentTaskInfo)
    }

    suspend fun start() {
        if (currentTaskInfo == null) {
            currentTaskInfo = TaskInfo()
        }
        currentTaskInfo.isPaused = false
        withContext(Dispatchers.Default) {
            val leaveTimes = currentTaskInfo.currentTime / 1000
            repeat(leaveTimes.toInt()) {
                if (currentTaskInfo.isPaused) {
                    return@repeat
                }
                delay(1000L)
                decrease()
            }
        }

    }

    fun addTaskStateChangedCallback(callback: ICallBack): Boolean {
        taskStateChangedCallbacks.add(callback)
        notifyTaskState(currentTaskInfo)
        return true
    }

    fun removeTaskStateChangedCallback(callback: ICallBack): Boolean {
        return taskStateChangedCallbacks.remove(callback)
    }

    private fun notifyTaskState(currentTaskInfo: TaskInfo) {
        for (taskStateChangedCallback in taskStateChangedCallbacks) {
            taskStateChangedCallback.onProgress(currentTaskInfo.currentTime)
            taskStateChangedCallback.onTaskStateChanged(0, if (currentTaskInfo.isPaused) 0 else 1)
        }
    }

}