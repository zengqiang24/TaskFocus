package com.qzeng.focustask.model

import androidx.lifecycle.ViewModel
import com.qzeng.focustask.ScheduleInfo
import java.util.*


class ScheduleInfoViewModel : ViewModel(){
  lateinit var scheduleInfo : ScheduleInfo

  fun getSechuleInfo(): ScheduleInfo?{
    return scheduleInfo
  }

  fun startTask(){
    scheduleInfo.taskTime
  }

  fun pauseTask(){

  }


}