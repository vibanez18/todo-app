package com.vibanez.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.vibanez.todoapp.addtask.ui.TaskScreen
import com.vibanez.todoapp.addtask.ui.TaskScreenViewModel
import com.vibanez.todoapp.addtask.ui.TaskSwipeScreen
import com.vibanez.todoapp.ui.theme.TodoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val taskScreenViewModel: TaskScreenViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TaskScreen(taskScreenViewModel)
//                    TaskSwipeScreen(taskScreenViewModel)
                }
            }
        }
    }
}