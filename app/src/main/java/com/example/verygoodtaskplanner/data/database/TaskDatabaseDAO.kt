package com.example.verygoodtaskplanner.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.verygoodtaskplanner.data.entities.Task
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface TaskDatabaseDAO {
    @Insert
    fun addTask(task: Task): Completable

    @Update
    fun updateTask(task: Task): Completable

    @Query(
        "SELECT * FROM tasks WHERE" +
                " (dateStart <= (:dayStart) AND dateFinish>=(:dayFinish))" +
                " OR (dateStart BETWEEN (:dayStart) AND (:dayFinish))" +
                " OR (dateFinish BETWEEN (:dayStart) AND (:dayFinish))"
    )
    fun getTasksFilteredByStartDate(dayStart: Long, dayFinish: Long): Single<List<Task>>

    @Query("DELETE FROM tasks WHERE id=(:id)")
    fun deleteTaskById(id: Long): Completable
}
