package com.qzeng.focustask.ui

import com.qzeng.focustask.model.TaskInfo
import com.qzeng.focustask.service.REST_TASK_TYPE
import com.qzeng.focustask.service.WORK_TASK_TYPE
import com.qzeng.focustask.utils.formatDateToString

fun TaskInfo.getPrompt(): String {
    return if (this.type == WORK_TASK_TYPE) "开始25分钟专注" else "开始5分钟休息"
}

fun TaskInfo.getCurrentTime(): String {
    return formatDateToString(this.currentTime)
}

fun TaskInfo.getNextTaskType(): Int {
    return if (this.type == WORK_TASK_TYPE) REST_TASK_TYPE else WORK_TASK_TYPE
}

fun TaskInfo.getNextTaskPrompt(): String {
    return if (this.type == WORK_TASK_TYPE) "您已经完成一次专注，开始5分钟休息吧！" else "您已经休息结束，开始25分钟专注吧！"
}

