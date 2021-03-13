package com.qzeng.focustask.ui

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.orhanobut.logger.Logger
import com.qzeng.focustask.utils.formatDateToString
import javax.inject.Inject


class TaskViewModel : AndroidViewModel, TaskManager.TaskListener {
    private val TAG = "ScheduleTaskViewModel"
    val currentTime: ObservableField<String> = ObservableField()

    @Inject
    lateinit var taskManager: TaskManager

    @Inject
    constructor(application: Application) : super(application) {
        taskManager.init(application)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        taskManager.registerTaskListener(this)

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        taskManager.unRegisterTaskListener(this)
    }

    fun start() {
        taskManager.start()
    }

    fun pause() {
        taskManager.pause()
    }

    override fun onTaskStateChanged(type: Int, state: Int) {
        Logger.t(TAG).d("task type = $type; task state = $state" )
    }

    override fun onProgress(time: Long) {
        Logger.t(TAG).d("task currentTime = $time;  " )
        currentTime.set(formatDateToString(time))

    }


}

