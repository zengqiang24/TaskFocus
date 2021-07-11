package com.qzeng.focustask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qzeng.focustask.MainApplication
import com.qzeng.focustask.databinding.TaskScheduleFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), ViewModelProvider.Factory {
    private lateinit var taskScheduleFragmentBinding: TaskScheduleFragmentBinding
    private val scheduleTaskViewModel: TaskViewModel by viewModels { this }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        taskScheduleFragmentBinding = TaskScheduleFragmentBinding.inflate(layoutInflater, container, false)
        scheduleTaskViewModel.let {
            taskScheduleFragmentBinding.mViewModel = it
            lifecycle.addObserver(it)
        }
        return taskScheduleFragmentBinding.root
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val mainApplication = activity?.application as MainApplication
        return TaskViewModel(mainApplication.taskManager) as T
    }

    companion object {
        private val TAG = "TaskTimeDetailFragment"
        fun createInstance(): MainFragment {
            return MainFragment()
        }
    }

}