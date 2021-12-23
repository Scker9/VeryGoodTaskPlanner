package com.example.verygoodtaskplanner.data.repositories

import androidx.room.RoomDatabase
import com.example.verygoodtaskplanner.data.database.TaskDatabase
import com.example.verygoodtaskplanner.data.entities.Task
import com.example.verygoodtaskplanner.presentation.repositories.TasksRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.component.inject

class TaskRepositoryImpl : TasksRepository {
    val database by inject<TaskDatabase>()
    override fun getTasksByDay(timeStamp: Long): Single<ArrayList<Task>> {
        return database.taskDao().getTasksByRange(timeStamp, timeStamp + addDay)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .map {
                it as ArrayList<Task>
            }
    }

    companion object {
        const val addDay = 86_400_000
    }
}
