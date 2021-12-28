package com.example.verygoodtaskplanner.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//можно потом перевести в json....
@Entity(tableName = "tasks")
data class Task(
    @ColumnInfo(name = "dateStart") override var dateStart: Long,
    @ColumnInfo(name = "dateFinish") override var dateFinish: Long,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "description") var description: String,
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
) : TimeRange()

