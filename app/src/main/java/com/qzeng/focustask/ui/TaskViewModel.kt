package com.qzeng.focustask.ui

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.qzeng.focustask.service.DEFAULT_TIME_INERNAL
import com.qzeng.focustask.utils.formatDateToString
import io.reactivex.disposables.Disposable
import javax.inject.Inject


class TaskViewModel : AndroidViewModel {
    private val TAG = "ScheduleTaskViewModel"
    val currentTime: ObservableField<String> = ObservableField()


    var schedulePeriodicallyDirect: Disposable? = null

    @Inject
    constructor(application: Application) : super(application) {

     }
}