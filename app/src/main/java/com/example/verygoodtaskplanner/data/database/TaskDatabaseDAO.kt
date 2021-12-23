package com.example.verygoodtaskplanner.data.database

import android.widget.ArrayAdapter
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.verygoodtaskplanner.data.entities.Task
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface TaskDatabaseDAO {
    @Insert
    fun addTask(task: Task): Completable

    @Insert
    fun addTasks(task: List<Task>): Completable

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Single<List<Task>>

    @Query("SELECT * FROM tasks WHERE dateStart >= (:rangeStart) AND dateFinish <= (:rangeFinish)")
    fun getTasksByRange(rangeStart: Long, rangeFinish: Long): Single<List<Task>>
    }
