package com.example.verygoodtaskplanner.domain.mappers

import com.example.verygoodtaskplanner.data.entities.Hour
import com.example.verygoodtaskplanner.presentation.entities.HourUI

class HourToHourUIMapper {
    fun convertHourToHourUI(hour: Hour): HourUI =
        HourUI(
            hour.dateStart,
            hour.dateFinish,
            TaskToTaskUIMapper().convertTaskToTaskUI(hour.tasks)
        )

    fun convertHourToHourUI(hours: List<Hour>): List<HourUI> {
        return hours.map { convertHourToHourUI(it) }
    }
}