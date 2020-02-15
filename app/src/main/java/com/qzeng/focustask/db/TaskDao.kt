package com.qzeng.focustask.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskDao {

  @Query("SELECT * from task_info ORDER BY updateTime ASC")
  fun getAllTasks(): LiveData<List<TaskEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(taskEntity: TaskEntity)

  @Query("DELETE FROM task_info")
    fun deleteAll()
}