package com.qzeng.focustask.model

import com.qzeng.focustask.service.DEFAULT_TIME_INERNAL

/**
 * The schedule information in each task.
 */
data class TaskInfo(var taskTime: Long = DEFAULT_TIME_INERNAL) {
  var currentTime: Long = taskTime
  var isPaused : Boolean = true
  var id : Long = 0
}