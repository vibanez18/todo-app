package com.vibanez.todoapp.addtask.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM TaskEntity")
    fun findAll(): Flow<List<TaskEntity>>

    @Insert
    suspend fun addTask(taskEntity: TaskEntity)

}