package com.qzeng.focustask.ui

import android.os.Bundle
import com.qzeng.focustask.model.TaskInfo
import com.qzeng.focustask.service.*
import com.qzeng.focustask.utils.formatDateToString

fun TaskInfo.getPrompt(): String {
    return if (this.type == REST_TASK_TYPE) "正在5分钟的休息" else "正在25分钟的专注"
}

fun TaskInfo.getCurrentTime(): String {
    return formatDateToString(this.currentTime)
}

fun TaskInfo.getNextTaskType(): Int {
    return if (this.type == WORK_TASK_TYPE) REST_TASK_TYPE else WORK_TASK_TYPE
}

fun TaskInfo.getNextTaskPrompt(): String {
    return if (this.type == WORK_TASK_TYPE) "开始5分钟休息吧！" else "开始25分钟专注吧！"
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

fun TaskInfo.isCancelled(): Boolean {
    return this.state == TASK_STATE_CANCEL
}

fun TaskInfo.isReady(): Boolean {
    return this.state == TASK_STATE_READY
}

fun Bundle.getTaskInfoExtra(): TaskInfo? {
    return getParcelable("TaskInfo")
}