package com.qzeng.focustask.db

class TaskRepository (val dao : TaskDao) {

    val taskEntities = dao.getAllTasks()

    suspend fun insertTask(task: TaskEntity){
       dao.insert(task)
    }

}