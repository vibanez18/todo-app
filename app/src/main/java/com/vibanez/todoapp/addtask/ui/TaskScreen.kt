package com.vibanez.todoapp.addtask.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog


@Composable
fun TaskScreen(taskScreenViewModel: TaskScreenViewModel) {
    val showDialog: Boolean by taskScreenViewModel.showDialog.observeAsState(false)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
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
                Button(onClick = { onTaskAdded(myTask) }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Create task")
                }
            }
        }
    }

}

const val GENERAL_PADDING = 16
fun getGeneralPadding() = GENERAL_PADDING.dp