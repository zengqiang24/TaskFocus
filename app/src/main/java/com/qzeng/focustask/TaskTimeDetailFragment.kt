package com.qzeng.focustask

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qzeng.focustask.databinding.TaskScheduleFragmentBinding


class TaskTimeDetailFragment : Fragment()
{
  private var  mTaskScheduleFragmentBinding:TaskScheduleFragmentBinding?=null
   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    super.onCreateView(inflater, container, savedInstanceState)
    mTaskScheduleFragmentBinding = TaskScheduleFragmentBinding.inflate(layoutInflater, container, false)
    return mTaskScheduleFragmentBinding!!.root
  }

  override fun onStart() {
    super.onStart()

   }
}