package com.qzeng.focustask.model

import android.os.Parcel
import com.qzeng.focustask.service.DEFAULT_TIME_INERNAL

/**
 * The schedule information in each task.
 */
class TaskInfo{
    var currentTime: Long = DEFAULT_TIME_INERNAL
    var isPaused: Boolean = true
    var id: Long = 0
}