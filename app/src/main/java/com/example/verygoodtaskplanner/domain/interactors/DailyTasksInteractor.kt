package com.example.verygoodtaskplanner.domain.interactors

import android.util.Log
import com.example.verygoodtaskplanner.data.entities.Hour
import com.example.verygoodtaskplanner.data.entities.Task
import com.example.verygoodtaskplanner.domain.repositories.HourRepository
import com.example.verygoodtaskplanner.domain.repositories.TasksRepository
import io.reactivex.Completable
import io.reactivex.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.collections.ArrayList

class DailyTasksInteractor : KoinComponent {
    private val TAG = this::class.java.simpleName

    private val taskRepository by inject<TasksRepository>()
    private val hourRepository by inject<HourRepository>()

    fun getHoursWithTasks(startOfDay: Long): Single<ArrayList<Hour>> {
        val hoursByDay = hourRepository.getDayHours(startOfDay)
        return taskRepository.getTasksByDay(startOfDay).doOnSuccess {
            Log.d(TAG, "Got tasks by sql request $it")
            distributeTasksIntoHours(hoursByDay, it)
        }.map {
            Log.d(TAG, hoursByDay.toString())
            hoursByDay
        }
    }

    fun addTask(task: Task): Completable {
        return taskRepository.addTaskToDataBase(task)
    }

    private fun distributeTasksIntoHours(
        listOfHours: ArrayList<Hour>,
        listOfTasks: ArrayList<Task>
    ) {
        listOfHours.forEach { hour ->
            listOfTasks.forEach { task ->
                if (hour.dateStart <= task.dateStart && hour.dateFinish <= task.dateFinish) {
                    hour.tasks.add(task)
                    Log.d(TAG, "task added = $task")
                }
            }
        }
    }
}
//00:00 - 00:59 start :1640638800000   stop: 1640642340000
//
// start:1640641800884  stop: 1640645400884