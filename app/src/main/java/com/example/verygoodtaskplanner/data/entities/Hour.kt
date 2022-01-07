package com.example.verygoodtaskplanner.data.entities

import com.example.verygoodtaskplanner.presentation.extensions.ADD_HOUR

data class Hour(
    val dateStart: Long,
    val dateFinish: Long,
    var tasks: ArrayList<Task> = arrayListOf()
)  {
    constructor(dateStart: Long) : this(dateStart, dateStart + ADD_HOUR)
}
