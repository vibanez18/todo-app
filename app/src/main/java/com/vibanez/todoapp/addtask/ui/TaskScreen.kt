package com.vibanez.todoapp.addtask.ui

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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.vibanez.todoapp.addtask.ui.model.TaskModel


@Composable
fun TaskScreen(taskScreenViewModel: TaskScreenViewModel) {
    val showDialog: Boolean by taskScreenViewModel.showDialog.observeAsState(false)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TaskList(taskScreenViewModel = taskScreenViewModel)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListWhitSwipe(taskScreenViewModel: TaskScreenViewModel) {
    val tasks = taskScreenViewModel.tasks
    LazyColumn {
        items(tasks, key = { it.id }) { task ->
            val state = rememberDismissState(
                confirmValueChange = {
                    if (it == DismissValue.DismissedToStart) {
                        taskScreenViewModel.onItemRemove(task)
                    }
                    true
                }
            )
            SwipeToDismissItem(state, task, taskScreenViewModel)
        }
    }
}

@Composable
fun TaskList(taskScreenViewModel: TaskScreenViewModel) {
    val tasks = taskScreenViewModel.tasks
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
@OptIn(ExperimentalMaterial3Api::class)
private fun SwipeToDismissItem(
    state: DismissState,
    taskModel: TaskModel,
    taskScreenViewModel: TaskScreenViewModel
) {
    SwipeToDismiss(state = state,
        background = {
            val color = when (state.dismissDirection) {
                DismissDirection.StartToEnd -> Color.Transparent
                DismissDirection.EndToStart -> Color.Red
                null -> Color.Transparent
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.align(
                        Alignment.CenterEnd
                    )
                )
            }
        },
        dismissContent = {
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
        })
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