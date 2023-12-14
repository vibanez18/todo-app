package com.vibanez.todoapp.addtask.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vibanez.todoapp.addtask.ui.model.TaskModel
import javax.inject.Inject

class TaskScreenViewModel @Inject constructor() : ViewModel() {
    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onTaskCreated(task: String) {
        Log.i("task added", task)
        _tasks.add(TaskModel(task = task))
        _showDialog.value = false
    }

    fun onShowDialog() {
        _showDialog.value = true
    }

    fun onCheckBoxSelected(taskModel: TaskModel) {
        val index = _tasks.indexOf(taskModel)
        _tasks[index] = _tasks[index].let { it.copy(selected = !it.selected) }
    }

    fun onItemRemove(taskModel: TaskModel) {
        _tasks
            .find { it.id == taskModel.id }
            .let { _tasks.remove(it) }
    }

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    private val _tasks = mutableStateListOf<TaskModel>()
    val tasks = _tasks as List<TaskModel>
}