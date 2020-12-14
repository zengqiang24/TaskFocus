package com.qzeng.focustask.ui

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.qzeng.focustask.utils.AppLogger
import com.qzeng.focustask.utils.format
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class TaskViewModel : AndroidViewModel {
    private val TAG = "ScheduleTaskViewModel"
    val currentTime: ObservableField<String> = ObservableField()
    var timeMin = 25 //min
    val seconds = 1
    var timeInternal = timeMin * seconds * 1000.toLong()
    var schedulePeriodicallyDirect: Disposable? = null
    private var isPaused: Boolean = false

    @Inject
    constructor(application: Application) : super(application) {
        currentTime.set(format(timeInternal.toInt()))
    }

    fun start() {
        AppLogger.d(TAG, "start called in viewModel")
        isPaused = false
        schedulePeriodicallyDirect = Schedulers.io().schedulePeriodicallyDirect({
            if (!isPaused && timeInternal > 0) {
                timeInternal -= 1000
                currentTime.set(format(timeInternal.toInt()))
            }else{
              reset()
            }
        }, 1000, 1000, TimeUnit.MILLISECONDS)
    }

    fun pause() {
        if (!schedulePeriodicallyDirect?.isDisposed!!) {
            schedulePeriodicallyDirect?.dispose()
            isPaused = true
        }
        AppLogger.d(TAG, "pause called in viewModel")
    }

    fun reset() {
        isPaused = true
        schedulePeriodicallyDirect?.dispose()
        timeInternal = timeMin * seconds * 1000.toLong()
        AppLogger.d(TAG, "reset called in viewModel")
    }

}