package com.example.verygoodtaskplanner.presentation.entities

data class HourUI(
    override var dateStart: Long,
    override var dateFinish: Long,
    var tasks: ArrayList<TaskUI> = arrayListOf()
) : TimeRange()

