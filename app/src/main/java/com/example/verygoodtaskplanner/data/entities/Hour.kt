package com.example.verygoodtaskplanner.data.entities

import com.example.verygoodtaskplanner.data.ADD_HOUR

data class Hour(
    override var dateStart: Long,
    override var dateFinish: Long,
    var tasks: ArrayList<Task> = arrayListOf()
) : TimeRange() {
    constructor(dateStart: Long) : this(dateStart, dateStart + ADD_HOUR)

}
