package com.qzeng.focustask.ui

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import javax.inject.Inject


class TaskViewModel : AndroidViewModel {
    private val TAG = "ScheduleTaskViewModel"
    val currentTime: ObservableField<String> = ObservableField()



    @Inject
    constructor(application: Application) : super(application) {

     }
}