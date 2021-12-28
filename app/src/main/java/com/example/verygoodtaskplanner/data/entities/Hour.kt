package com.example.verygoodtaskplanner.data.entities

data class Hour(
    override var dateStart: Long,
    override var dateFinish: Long,
    var tasks: ArrayList<Task> = arrayListOf()
) : TimeRange() {
    constructor(dateStart: Long) : this(dateStart, dateStart + ADD_HOUR)

    companion object {
        const val ADD_HOUR = 3600_000
    }
}
