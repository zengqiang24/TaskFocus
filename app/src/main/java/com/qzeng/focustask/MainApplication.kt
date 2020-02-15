package com.qzeng.focustask

import android.app.Application
import com.qzeng.focustask.db.TaskDao
import com.qzeng.focustask.db.TaskDatabase
import com.qzeng.focustask.db.TaskEntity
import com.qzeng.focustask.db.TaskRepository
import com.qzeng.focustask.utils.AppLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainApplication : Application(){
   override fun onCreate() {
    super.onCreate()
    AppLogger.i("[init]","App start...")
    //initial database
//     var repository = TaskRepository(TaskDatabase.getDatabase(this).getTaskDao())


//    repository.insertTask(TaskEntity)
  }
}