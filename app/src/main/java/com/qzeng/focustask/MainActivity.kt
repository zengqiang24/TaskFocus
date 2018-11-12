package com.qzeng.focustask
import android.os.Bundle
import android.support.v4.app.FragmentActivity

class MainActivity : FragmentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val timeFragment = TaskTimeDetailFragment()
    supportFragmentManager.beginTransaction().replace(R.id.content, timeFragment,
        "TAG").commitAllowingStateLoss()
 

  }
}
