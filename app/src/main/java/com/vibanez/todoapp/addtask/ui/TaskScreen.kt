package com.vibanez.todoapp.addtask.ui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.vibanez.todoapp.addtask.ui.model.TaskModel


@Composable
fun TaskScreen(taskScreenViewModel: TaskScreenViewModel) {
    val showDialog: Boolean by taskScreenViewModel.showDialog.observeAsState(false)

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<TaskUiState>(
        initialValue = TaskUiState.Loading,
        key1 = lifecycle,
        key2 = taskScreenViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            taskScreenViewModel.uiState.collect { value = it }
        }
    }

    when(uiState) {
        is TaskUiState.Error -> {}
        TaskUiState.Loading -> { CircularProgressIndicator() }
        is TaskUiState.Success -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                TaskList((uiState as TaskUiState.Success).tasks, taskScreenViewModel)
                AddTaskDialog(
                    show = showDialog,
                    onDismiss = { taskScreenViewModel.onDialogClose() },
                    onTaskAdded = { taskScreenViewModel.onTaskCreated(it) })
                FabDialog(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(getGeneralPadding()),
                    taskScreenViewModel = taskScreenViewModel
                )
            }
        }
    }
}

@Composable
fun TaskList(tasks: List<TaskModel>, taskScreenViewModel: TaskScreenViewModel) {
//    val tasks = taskScreenViewModel.tasks
    tasks.forEach { Log.i("task added", it.task) }
    LazyColumn {
        items(tasks, key = { it.id }) {
            ItemTask(
                taskModel = it,
                taskScreenViewModel = taskScreenViewModel
            )
        }
    }
}

@Composable
fun ItemTask(taskModel: TaskModel, taskScreenViewModel: TaskScreenViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = getGeneralPadding(), vertical = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = { taskScreenViewModel.onItemRemove(taskModel) })
            },
        border = BorderStroke(2.dp, Color.Black),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = taskModel.task, modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
            )
            Checkbox(
                checked = taskModel.selected,
                onCheckedChange = { taskScreenViewModel.onCheckBoxSelected(taskModel) })
        }
    }
}

@Composable
fun FabDialog(modifier: Modifier, taskScreenViewModel: TaskScreenViewModel) {
    FloatingActionButton(
        onClick = { taskScreenViewModel.onShowDialog() },
        modifier = modifier
    ) {
        Icon(Icons.Filled.Add, contentDescription = "")
    }
}

@Composable
fun AddTaskDialog(show: Boolean, onDismiss: () -> Unit, onTaskAdded: (String) -> Unit) {

    var myTask by remember { mutableStateOf("") }

    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(getGeneralPadding())
            ) {
                Text(
                    text = "Add new task",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.size(getGeneralPadding()))
                TextField(
                    value = myTask,
                    onValueChange = { myTask = it },
                    singleLine = true,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.size(getGeneralPadding()))
                Button(onClick = {
                    onTaskAdded(myTask)
                    myTask = ""
                }, modifier = Modifier.fillMaxWidth(), enabled = !myTask.isEmpty()) {
                    Text(text = "Create task")
                }
            }
        }
    }

}

const val GENERAL_PADDING = 16
fun getGeneralPadding() = GENERAL_PADDING.dp