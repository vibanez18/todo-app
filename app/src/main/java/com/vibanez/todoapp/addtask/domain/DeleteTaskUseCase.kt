package com.vibanez.todoapp.addtask.domain

import com.vibanez.todoapp.addtask.data.TaskRepository
import com.vibanez.todoapp.addtask.ui.TaskModel
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    suspend operator fun invoke(taskModel: TaskModel) {
        taskRepository.delete(taskModel)
    }

}