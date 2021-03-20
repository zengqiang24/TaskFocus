package com.qzeng.focustask.ui

import android.os.Bundle
import android.os.Debug
import android.os.Handler
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.qzeng.focustask.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    @Inject
    lateinit var taskManager: TaskManager
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        taskManager.init()
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, TaskIndexFragment.createInstance(),
                        "TAG").commitAllowingStateLoss()
    }

}
