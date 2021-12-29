package com.example.verygoodtaskplanner.domain.repositories

import com.example.verygoodtaskplanner.data.entities.Task
import io.reactivex.Completable
import io.reactivex.Single
import org.koin.core.component.KoinComponent

interface TasksRepository : KoinComponent {
    fun getTasksByDayStart(dayStart: Long): Single<ArrayList<Task>>
    fun addTaskToDataBase(task: Task): Completable
    fun getAllTasks(): Single<ArrayList<Task>>
}