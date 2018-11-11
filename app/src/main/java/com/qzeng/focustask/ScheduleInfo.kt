package com.qzeng.focustask

/**
 * The schedule information about each task.
 */
data class ScheduleInfo(var taskTime: Long = 111) {
  var period: Long? = null
  var restTime: Long? = null
}