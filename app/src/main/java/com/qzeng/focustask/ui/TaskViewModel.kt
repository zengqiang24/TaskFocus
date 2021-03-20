package com.qzeng.focustask.ui

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.orhanobut.logger.Logger
import com.qzeng.focustask.utils.formatDateToString
import javax.inject.Inject

class TaskViewModel @Inject constructor(private val taskManager: TaskManager) : ViewModel(), LifecycleObserver, TaskManager.TaskListener {
    private val TAG = "ScheduleTaskViewModel"
    val currentTime: ObservableField<String> = ObservableField()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        taskManager.registerTaskListener(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        taskManager.unRegisterTaskListener(this)
    }

    fun start() {
        taskManager.start()
    }

    fun pause() {
        taskManager.pause()
    }

    override fun onTaskStateChanged(type: Int, state: Int) {
        Logger.t(TAG).d("task type = $type; task state = $state")
    }

    override fun onProgress(time: Long) {
        Log.d(TAG, "task currentTime = $time;  ")
        currentTime.set(formatDateToString(time))
    }


}

