package com.qzeng.focustask

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.qzeng.focustask.aidl.IRemoteService
import com.qzeng.focustask.service.TaskService

class MainActivity : FragmentActivity() {
    val mConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d("qiang", "onServiceConnected...")
          IRemoteService.Stub.asInterface(service).apply {
                getPID()
            }
         }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, TaskService::class.java)
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
        val timeFragment = TaskTimeDetailFragment.createInstance()
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, timeFragment,
                        "TAG").commitAllowingStateLoss()


    }
}
