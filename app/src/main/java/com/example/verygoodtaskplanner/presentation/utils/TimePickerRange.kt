package com.example.verygoodtaskplanner.presentation.utils

import android.app.TimePickerDialog
import android.content.Context
import java.util.*


class TimePickerRange(
    val context: Context,
    var startCalendar: Calendar = Calendar.getInstance(),
    var finishCalendar: Calendar = Calendar.getInstance(),
) {
    var onTimeChanged: ((CalendarType, Calendar) -> Unit)? = null

    fun getTimePickerDialog(type: CalendarType): TimePickerDialog {
        val calendar = when (type) {
            CalendarType.FINISH -> finishCalendar
            CalendarType.START -> startCalendar
        }
        return TimePickerDialog(
            context,
            getOnTimeSetListener(type, calendar),
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
    }

    private fun getOnTimeSetListener(
        type: CalendarType,
        calendar: Calendar
    ): TimePickerDialog.OnTimeSetListener {
        val listener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            onTimeChanged?.invoke(type, calendar)
        }
        return listener
    }
}