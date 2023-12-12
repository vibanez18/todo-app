package com.vibanez.todoapp.addtask.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class TaskScreenViewModel @Inject constructor() : ViewModel() {
    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onTaskCreated(task: String) {
        Log.i("task added", task)
        _showDialog.value = false
    }

    fun onShowDialog() {
        _showDialog.value = true
    }

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog
}