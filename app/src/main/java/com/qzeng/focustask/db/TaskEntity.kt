package com.qzeng.focustask.db

 import androidx.annotation.IntDef
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *   A database table to persist task info.
 *
 */
@Entity(tableName="task_info")
data class TaskEntity(@PrimaryKey val id: String, val content: String,
    val title: String, @TaskState val state: Int = 0, val createTime: Long, val updateTime: Long)

/**
 * Used to explicitly set a value of taskState.
 */
@IntDef(FINISH, START, PENDING)
@Retention(AnnotationRetention.SOURCE)
annotation class TaskState

const val FINISH = 2
const val START = 1
const val PENDING = 0
