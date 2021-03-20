package com.qzeng.focustask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.qzeng.focustask.databinding.TaskScheduleFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TaskIndexFragment : Fragment() {
    private val TAG = "TaskTimeDetailFragment"
    private lateinit var mTaskScheduleFragmentBinding: TaskScheduleFragmentBinding

    @Inject
    lateinit var mScheduleTaskViewModel: TaskViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        this.mTaskScheduleFragmentBinding = TaskScheduleFragmentBinding.inflate(layoutInflater, container,
                false)
        mTaskScheduleFragmentBinding.mViewModel = mScheduleTaskViewModel
        lifecycle.addObserver(mScheduleTaskViewModel)
        return mTaskScheduleFragmentBinding.root
    }

    companion object {
        fun createInstance(): TaskIndexFragment {
            return TaskIndexFragment()
        }
    }

}