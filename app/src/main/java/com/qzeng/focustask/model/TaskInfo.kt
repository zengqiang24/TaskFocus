package com.qzeng.focustask.model

import com.qzeng.focustask.service.DEFAULT_TIME_INERNAL
import com.qzeng.focustask.service.TASK_STATE_READY

/**
 * The schedule information in each task.
 */
class TaskInfo constructor(var currentTime: Long = DEFAULT_TIME_INERNAL, var type: Int = 0) {
    var state: Int = TASK_STATE_READY
 }