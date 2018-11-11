package com.qzeng.focustask

/**
 * taskEntity is the base element for Task,
 * Include Content, State (0 = not start . 1:start, 2:pause),
 */
data class TaskEntity(val content: String = "", val state: Int = 0,val createTime : Long, val updateTime : Long)