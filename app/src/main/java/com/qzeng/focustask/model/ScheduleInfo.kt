package com.qzeng.focustask.model

/**
 * The schedule information in each task.
 */
data class ScheduleInfo(var taskTime: Long = 111) {
  var period: Long? = null
  var restTime: Long? = null
}