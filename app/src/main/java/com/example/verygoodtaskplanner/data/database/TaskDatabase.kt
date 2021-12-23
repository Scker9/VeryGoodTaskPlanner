package com.example.verygoodtaskplanner.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.verygoodtaskplanner.data.entities.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDatabaseDAO
}