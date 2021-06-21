package com.qzeng.focustask.service

import android.os.Bundle
import com.orhanobut.logger.Logger
import com.qzeng.focustask.aidl.ICallBack
import com.qzeng.focustask.model.TaskInfo
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.CopyOnWriteArraySet
import javax.inject.Inject
import javax.inject.Singleton

const val DEFAULT_TIME_INERNAL = 25 * 1000L
const val REST_TIME_INERNAL = 5 * 1000L
const val WORK_TASK_TYPE = 0
const val REST_TASK_TYPE = 1
const val TASK_STATE_START = 0x01
const val TASK_STATE_PAUSE = 0x02
const val TASK_STATE_DONE = 0x03
private const val TAG = "TimeComputeManager"

@Singleton
class TimeTaskManager @Inject constructor() {
    var currentTaskInfo: TaskInfo = TaskInfo()
    private val taskStateChangedCallbacks = CopyOnWriteArraySet<ICallBack>()
    fun isStarted() = !currentTaskInfo.isStart()

    fun pause() {
        currentTaskInfo.state = TASK_STATE_PAUSE
        notifyTaskState(currentTaskInfo)
    }

    fun stop() {
        currentTaskInfo.state = TASK_STATE_PAUSE
        notifyTaskState(currentTaskInfo)
    }

    suspend fun start(taskInfo: TaskInfo) {
        currentTaskInfo = taskInfo
        if (currentTaskInfo.isStart()) {
            return
        }

        currentTaskInfo.state = TASK_STATE_START
        val leaveTimes = currentTaskInfo.currentTime / 1000
        repeat(leaveTimes.toInt()) {
            if (currentTaskInfo.isPaused()) {
                return
            }
            delay(1000L)
            Logger.t(TAG).d("currentThread = ${Thread.currentThread().name}")
            if (currentTaskInfo.currentTime > 0) {
                currentTaskInfo.currentTime = currentTaskInfo.currentTime - 1000L
                if (currentTaskInfo.currentTime == 0L)
                    currentTaskInfo.state = TASK_STATE_DONE
            }
            notifyTaskState(currentTaskInfo)
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

    private fun notifyTaskState(task: TaskInfo) {
        for (taskStateChangedCallback in taskStateChangedCallbacks) {
            val bundle = Bundle().apply {
                putParcelable("TaskInfo", task)
            }

            taskStateChangedCallback.onTaskStateChanged(bundle)
        }
    }

    suspend fun resume() {
        start(currentTaskInfo)
    }
}

fun createTask(type: Int): TaskInfo {
    return when (type) {
        WORK_TASK_TYPE -> TaskInfo(DEFAULT_TIME_INERNAL, type)
        REST_TASK_TYPE -> TaskInfo(REST_TIME_INERNAL, type)
        else -> TaskInfo()
    }
}


fun TaskInfo.isPaused(): Boolean {
    return this.state == TASK_STATE_PAUSE
}

fun TaskInfo.isDone(): Boolean {
    return this.state == TASK_STATE_DONE
}

fun TaskInfo.isStart(): Boolean {
    return this.state == TASK_STATE_START
}