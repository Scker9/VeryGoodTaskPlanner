package com.example.verygoodtaskplanner.data

import java.util.*

fun Calendar.getFormattedTime(): String =
    "%02d:%02d".format(this.get(Calendar.HOUR_OF_DAY), this.get(Calendar.MINUTE))

fun Calendar.getFormattedDate(): String =
    "%02d.%02d.%d".format(
        this.get(Calendar.DAY_OF_MONTH),
        this.get(Calendar.MONTH) + 1 /*идёт нумерация с нуля */,
        this.get(Calendar.YEAR)
    )

fun Calendar.getByTimeAndDateCalendars(timeCalendar: Calendar, dateCalendar: Calendar): Calendar {
    return Calendar.Builder()
        .setTimeOfDay(
            timeCalendar.get(Calendar.HOUR_OF_DAY),
            timeCalendar.get(Calendar.MINUTE), 0
        )
        .setDate(
            dateCalendar.get(Calendar.YEAR),
            dateCalendar.get(Calendar.MONTH),
            dateCalendar.get(Calendar.DAY_OF_MONTH)
        )
        .build()
}