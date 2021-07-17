package com.qzeng.focustask.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
//Annotations classed to be Room Database with a table of TaskEntity class
@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
  abstract fun getTaskDao(): TaskDao

  companion object {
    @Volatile
    private var INSTANCE: TaskDatabase? = null

    // Double check the instance whether or not null as a singleton pattern
    fun getDatabase(context: Context): TaskDatabase {
      val tempInstance = INSTANCE
      if (tempInstance != null) {
        return tempInstance
      }
      synchronized(this) {
        val instance = Room.databaseBuilder(context.applicationContext, TaskDatabase::class.java,
            "task_database").build()
        INSTANCE = instance
        return instance
      }
    }
  }


}