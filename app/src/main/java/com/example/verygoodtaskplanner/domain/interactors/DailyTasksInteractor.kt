package com.example.verygoodtaskplanner.domain.interactors

import com.example.verygoodtaskplanner.data.entities.Hour
import com.example.verygoodtaskplanner.data.entities.Task
import com.example.verygoodtaskplanner.domain.mappers.HourToHourUIMapper
import com.example.verygoodtaskplanner.domain.mappers.TaskToTaskUIMapper
import com.example.verygoodtaskplanner.domain.repositories.HourRepository
import com.example.verygoodtaskplanner.domain.repositories.TasksRepository
import com.example.verygoodtaskplanner.presentation.entities.HourUI
import com.example.verygoodtaskplanner.presentation.entities.TaskUI
import io.reactivex.Completable
import io.reactivex.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.collections.ArrayList

class DailyTasksInteractor : KoinComponent {
    private val taskRepository by inject<TasksRepository>()
    private val hourRepository by inject<HourRepository>()

    fun getHoursWithTasks(startOfDay: Long): Single<List<HourUI>> {
        val hoursByDay = hourRepository.getDayHours(startOfDay)
        return taskRepository.getTasksByDayStart(startOfDay).doOnSuccess {
            distributeTasksIntoHours(hoursByDay, it)
        }.map {
            hoursByDay.map {
                HourToHourUIMapper().convertHourToHourUI(it)
            }
        }
    }

    fun addTask(taskUI: TaskUI): Completable =
        taskRepository.addTaskToDataBase(TaskToTaskUIMapper().convertTaskUIToTask(taskUI))

    fun updateTask(taskUI: TaskUI): Completable =
        taskRepository.updateTask(TaskToTaskUIMapper().convertTaskUIToTask(taskUI))

    fun deleteTaskById(id: Long): Completable = taskRepository.deleteTaskById(id)

    private fun distributeTasksIntoHours(
        listOfHours: ArrayList<Hour>,
        listOfTasks: ArrayList<Task>
    ) {
        if (listOfTasks.isNotEmpty()) {
            listOfHours.forEach { hour ->
                listOfTasks.forEach { task ->
                    if ((task.dateStart in hour.dateStart..hour.dateFinish)
                        ||
                        (task.dateStart <= hour.dateStart && hour.dateFinish <= task.dateFinish)
                        ||
                        (task.dateFinish in hour.dateStart..hour.dateFinish)
                    ) {
                        hour.tasks.add(task)
                    }
                }
            }
        }
    }

}
