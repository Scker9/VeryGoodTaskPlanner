package com.example.verygoodtaskplanner.data.repositories

import com.example.verygoodtaskplanner.data.ADD_DAY
import com.example.verygoodtaskplanner.data.database.TaskDatabase
import com.example.verygoodtaskplanner.data.entities.Task
import com.example.verygoodtaskplanner.domain.repositories.TasksRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.component.inject


class TaskRepositoryImpl : TasksRepository {
    val database by inject<TaskDatabase>()
    override fun getTasksByDayStart(dayStart: Long): Single<ArrayList<Task>> {
        return database.taskDao().getTasksFilteredByStartDate(dayStart, dayStart + ADD_DAY)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .map {
                it as ArrayList<Task> //TODO() исправить!
            }
    }

    override fun addTaskToDataBase(task: Task): Completable {
        return database.taskDao().addTask(task).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getAllTasks(): Single<ArrayList<Task>> {
        return database.taskDao().getAllTasks().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it as ArrayList<Task>
            }
    }

    override fun updateTask(task: Task): Completable {
        return database.taskDao().updateTask(task).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
