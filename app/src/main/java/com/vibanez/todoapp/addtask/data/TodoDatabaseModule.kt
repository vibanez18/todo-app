package com.vibanez.todoapp.addtask.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TodoDatabaseModule {

    companion object {
        const val DATABASE_NAME = "TaskDatabase"
    }

    @Provides
    fun providesTaskDao(todoDatabase: TodoDatabase): TaskDao = todoDatabase.taskDao()
    @Provides
    @Singleton
    fun provideTodoDatabase(@ApplicationContext context: Context): TodoDatabase =
        Room.databaseBuilder(context, TodoDatabase::class.java, DATABASE_NAME).build()
}