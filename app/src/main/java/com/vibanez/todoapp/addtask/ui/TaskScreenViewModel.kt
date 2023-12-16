package com.vibanez.todoapp.addtask.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vibanez.todoapp.addtask.domain.AddTaskUseCase
import com.vibanez.todoapp.addtask.domain.GetTaskUseCase
import com.vibanez.todoapp.addtask.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskScreenViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    getTaskUseCase: GetTaskUseCase
) : ViewModel() {

    val uiState: StateFlow<TaskUiState> = getTaskUseCase().map {
        TaskUiState.Success(it)
    }
        .catch { TaskUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TaskUiState.Loading)

    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onTaskCreated(task: String) {
        Log.i("task added", task)
//        _tasks.add(TaskModel(task = task))
        viewModelScope.launch {
            addTaskUseCase(TaskModel(task = task))
        }
        _showDialog.value = false
    }

    fun onShowDialog() {
        _showDialog.value = true
    }

    fun onCheckBoxSelected(taskModel: TaskModel) {
//        val index = _tasks.indexOf(taskModel)
//        _tasks[index] = _tasks[index].let { it.copy(selected = !it.selected) }
    }

    fun onItemRemove(taskModel: TaskModel) {
//        _tasks
//            .find { it.id == taskModel.id }
//            .let { _tasks.remove(it) }
    }

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

//    private val _tasks = mutableStateListOf<TaskModel>()
//    val tasks = _tasks as List<TaskModel>
}