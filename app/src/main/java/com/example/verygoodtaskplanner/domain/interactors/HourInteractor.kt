package com.example.verygoodtaskplanner.domain.interactors

import android.util.Log
import com.example.verygoodtaskplanner.data.entities.Hour
import com.example.verygoodtaskplanner.data.entities.Task
import com.example.verygoodtaskplanner.domain.repositories.TasksRepository
import io.reactivex.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*
import kotlin.collections.ArrayList

class HourInteractor : KoinComponent {
    private val TAG = this::class.java.simpleName
    private val taskRepository by inject<TasksRepository>()
    fun getHoursWithTasks(startOfDay: Long): Single<ArrayList<Hour>> {
        val hoursByDay = create24Hours(startOfDay)
        return taskRepository.getTasksByDay(startOfDay).doOnSuccess {
            Log.d(TAG, "Got tasks by sql request $it")
            distributeTasksIntoHours(hoursByDay, it)
        }.map {
            hoursByDay
        }
    }

    private fun distributeTasksIntoHours(
        listOfHours: ArrayList<Hour>,
        listOfTasks: ArrayList<Task>
    ) {
        listOfHours.forEach { hour ->
            listOfTasks.forEach { task ->
                if (task.dateStart <= hour.dateStart && task.dateFinish >= hour.dateFinish) {
                    hour.tasks.add(task)
                }
            }
        }
    }

    private fun create24Hours(startOfDay: Long): ArrayList<Hour> {
        val listOfHours: ArrayList<Hour> = arrayListOf()
        for (i in 0..23) {
            val startCalendar = Calendar.Builder().setInstant(startOfDay).build()
            val endCalendar = Calendar.Builder().setInstant(startOfDay).build()
            startCalendar.set(Calendar.HOUR_OF_DAY, i)
            startCalendar.set(Calendar.MINUTE, 0)
            endCalendar.set(Calendar.HOUR_OF_DAY, i)
            endCalendar.set(Calendar.MINUTE, 59)
            listOfHours.add(
                Hour(
                    startCalendar.timeInMillis,
                    endCalendar.timeInMillis,
                    arrayListOf()
                )
            )
        }
        return listOfHours
    }
}