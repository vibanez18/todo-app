package com.vibanez.todoapp.addtask.domain

import com.vibanez.todoapp.addtask.data.TaskRepository
import com.vibanez.todoapp.addtask.ui.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    operator fun invoke(): Flow<List<TaskModel>> = taskRepository.findAll()
}