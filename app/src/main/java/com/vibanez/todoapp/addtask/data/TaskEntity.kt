package com.vibanez.todoapp.addtask.data

import androidx.room.PrimaryKey

data class TaskEntity(
    @PrimaryKey
    val id: Long,
    val task: String,
    var selected: Boolean = false
)
