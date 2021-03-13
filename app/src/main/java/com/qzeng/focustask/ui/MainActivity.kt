package com.qzeng.focustask.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.qzeng.focustask.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .replace(R.id.content, TaskIndexFragment.createInstance(),
                        "TAG").commitAllowingStateLoss()
    }

}
