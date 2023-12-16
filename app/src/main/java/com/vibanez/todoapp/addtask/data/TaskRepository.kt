package com.vibanez.todoapp.addtask.data

import com.vibanez.todoapp.addtask.ui.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

//    fun findAll(): Flow<List<TaskModel>> = taskDao.findAll().map { items ->
//        items.map {
//            TaskModel(
//                id = it.id.toUUID(),
//                task = it.task,
//                selected = it.selected
//            )
//        }
//    }

    val tasks: Flow<List<TaskModel>> = taskDao.findAll().map { items ->
        items.map {
            TaskModel(
                id = it.id.toUUID(),
                task = it.task,
                selected = it.selected
            )
        }
    }

    suspend fun add(taskModel: TaskModel) {
        taskDao.addTask(
            TaskEntity(
                id = taskModel.getLongId(),
                task = taskModel.task,
                selected = taskModel.selected
            )
        )
    }

    private fun Long.toUUID(): UUID = UUID.fromString(this.toString())
}