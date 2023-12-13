package com.vibanez.todoapp.addtask.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vibanez.todoapp.addtask.ui.model.TaskModel


@Composable
fun TaskSwipeScreen(taskScreenViewModel: TaskScreenViewModel) {
    val showDialog: Boolean by taskScreenViewModel.showDialog.observeAsState(false)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TaskSwipeListWhitSwipe(taskScreenViewModel = taskScreenViewModel)
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
fun TaskSwipeListWhitSwipe(taskScreenViewModel: TaskScreenViewModel) {
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