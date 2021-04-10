package com.qzeng.focustask.service

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
const val TASK_STATE_READY = 0x00
private const val TAG = "TimeComputeManager"

@Singleton
class TimeTaskManager @Inject constructor() {
    lateinit var currentTaskInfo: TaskInfo
    private val taskStateChangedCallbacks = CopyOnWriteArraySet<ICallBack>()
    lateinit var taskQueue: LinkedList<TaskInfo>
    fun isStarted() = !currentTaskInfo.isStart()
    fun init() {
        taskQueue = LinkedList<TaskInfo>()
        taskQueue.add(createTask(WORK_TASK_TYPE))
        taskQueue.add(createTask(REST_TASK_TYPE))
        currentTaskInfo = taskQueue.poll()
    }

    private suspend fun decrease() {
        currentTaskInfo.currentTime = currentTaskInfo.currentTime - 1000L
        notifyTaskState(currentTaskInfo)
    }

    fun pause() {
        currentTaskInfo.state = TASK_STATE_PAUSE
        notifyTaskState(currentTaskInfo)
    }

    fun reset() {
        init()
    }

    suspend fun start(taskInfo: TaskInfo) {
        Logger.t(TAG).d("currentThread = ${Thread.currentThread().name}")
        if (taskInfo.isStart()) {
            return
        }

        taskInfo.state = TASK_STATE_START
        val leaveTimes = taskInfo.currentTime / 1000
        repeat(leaveTimes.toInt()) {
            if (taskInfo.isPaused()) {
                return@repeat
            }
            // there switch to IO thread
            withContext(Dispatchers.Default) {
                delay(1000L)
                Logger.t(TAG).d("currentThread = ${Thread.currentThread().name}")
            }
            Logger.t(TAG).d("currentThread = ${Thread.currentThread().name}")
            if (!taskInfo.isPaused() && taskInfo.currentTime > 0) {
                decrease()
            }
        }
         if (taskInfo.currentTime <= 0) {
            currentTaskInfo = taskQueue.poll()
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
            taskStateChangedCallback.onProgress(task.currentTime)
            taskStateChangedCallback.onTaskStateChanged(task.type, task.state)
        }
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
fun TaskInfo.isREADY(): Boolean {
    return this.state == TASK_STATE_READY
}
fun TaskInfo.isStart(): Boolean {
    return this.state == TASK_STATE_START
}