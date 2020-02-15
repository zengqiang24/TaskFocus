package com.qzeng.focustask

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.qzeng.focustask.databinding.TaskScheduleFragmentBinding
import com.qzeng.focustask.service.TaskService


class TaskTimeDetailFragment : Fragment() {
  private var  mTaskScheduleFragmentBinding: TaskScheduleFragmentBinding? = null

  companion object {
     fun createInstance() :TaskTimeDetailFragment{
       return TaskTimeDetailFragment()
     }
  }
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    super.onCreateView(inflater, container, savedInstanceState)
    mTaskScheduleFragmentBinding = TaskScheduleFragmentBinding.inflate(layoutInflater, container,
        false)
    return mTaskScheduleFragmentBinding!!.root
  }


  override fun onStart() {
    super.onStart()

  }
}