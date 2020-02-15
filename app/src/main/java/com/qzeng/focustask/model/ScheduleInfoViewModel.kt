package com.qzeng.focustask.model

import androidx.lifecycle.ViewModel


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