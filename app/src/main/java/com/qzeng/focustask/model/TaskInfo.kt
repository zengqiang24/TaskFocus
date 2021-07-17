package com.qzeng.focustask.model

import android.os.Parcel
import android.os.Parcelable
import com.qzeng.focustask.service.WORK_TIME_INERNAL
import com.qzeng.focustask.service.TASK_STATE_PAUSE

/**
 * The schedule information in each task.
 */
data class TaskInfo constructor(var currentTime: Long = WORK_TIME_INERNAL, var type: Int = 0) :
    Parcelable {
    var state: Int = TASK_STATE_PAUSE

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readInt()
    ) {
        state = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(currentTime)
        parcel.writeInt(type)
        parcel.writeInt(state)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskInfo> {
        override fun createFromParcel(parcel: Parcel): TaskInfo {
            return TaskInfo(parcel)
        }

        override fun newArray(size: Int): Array<TaskInfo?> {
            return arrayOfNulls(size)
        }
    }
}