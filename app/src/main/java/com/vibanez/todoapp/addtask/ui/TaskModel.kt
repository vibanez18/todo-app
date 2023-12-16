package com.vibanez.todoapp.addtask.ui

data class TaskModel(
    val id: Int = System.currentTimeMillis().hashCode(),
    val task: String,
    var selected: Boolean = false
)