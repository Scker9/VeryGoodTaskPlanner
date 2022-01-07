package com.example.verygoodtaskplanner.data.entities

import com.example.verygoodtaskplanner.data.utils.ADD_HOUR


data class Hour(
    val dateStart: Long,
    val dateFinish: Long = dateStart + ADD_HOUR,
    var tasks: ArrayList<Task> = arrayListOf()
)