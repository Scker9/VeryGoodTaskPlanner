package com.example.verygoodtaskplanner.data.entities

data class Hour(
    override var dateStart: Long,
    override var dateFinish: Long,
    var tasks: ArrayList<Task>
) : TimeRange()
