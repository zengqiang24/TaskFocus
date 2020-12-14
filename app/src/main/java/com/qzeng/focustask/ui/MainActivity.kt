package com.qzeng.focustask.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.FragmentActivity
import com.qzeng.focustask.R
import com.qzeng.focustask.aidl.ITaskService
import com.qzeng.focustask.service.TaskService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    private val TAG = "MainActivity"
    private val mConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val taskService = ITaskService.Stub.asInterface(service)
         }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, TaskService::class.java)
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, TaskIndexFragment.createInstance(),
                        "TAG").commitAllowingStateLoss()

    }


}
