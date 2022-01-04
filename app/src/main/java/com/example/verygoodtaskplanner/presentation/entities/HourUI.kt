package com.example.verygoodtaskplanner.presentation.entities

import com.example.verygoodtaskplanner.data.entities.TimeRange

data class HourUI(
    override var dateStart: Long,
    override var dateFinish: Long,
    var tasks: ArrayList<TaskUI> = arrayListOf()
) : TimeRange()

