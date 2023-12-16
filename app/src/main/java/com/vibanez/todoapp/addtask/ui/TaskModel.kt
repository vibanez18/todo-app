package com.vibanez.todoapp.addtask.ui

import java.util.UUID

data class TaskModel(val id: UUID = UUID.randomUUID(), val task: String, var selected: Boolean = false) {
    fun getLongId() = id.mostSignificantBits
}
