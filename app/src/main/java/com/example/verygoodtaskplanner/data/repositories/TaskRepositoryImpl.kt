package com.example.verygoodtaskplanner.data.repositories

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
    override fun getTasksByDay(timeStamp: Long): Single<ArrayList<Task>> {
        return database.taskDao().getTasksByRange(timeStamp, timeStamp + ADD_DAY)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .map {
                it as ArrayList<Task> //TODO() исправить!
            }
    }

    override fun addTaskToDataBase(task: Task): Completable {
        return database.taskDao().addTask(task).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    companion object {
        //TODO() объединить все константы
        const val ADD_DAY = 86_400_000
    }
}
