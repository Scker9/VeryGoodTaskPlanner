package com.example.verygoodtaskplanner.presentation.repositories

import com.example.verygoodtaskplanner.data.entities.Task
import io.reactivex.Single
import org.koin.core.component.KoinComponent

interface TasksRepository:KoinComponent {
    fun getTasksByDay(timeStamp: Long): Single<ArrayList<Task>>
}