package com.example.verygoodtaskplanner.presentation.utils.timepickers

import android.app.DatePickerDialog
import android.content.Context
import java.util.*

class DatePickerRange(
    context: Context,
    var startCalendar: Calendar = Calendar.getInstance(),
    var finishCalendar: Calendar = Calendar.getInstance()
) : DatePickerDialog(context) {
    var onDateChanged: ((CalendarType, Calendar) -> Unit)? = null

    fun getDatePickerDialog(type: CalendarType): DatePickerDialog {
        val calendar = when (type) {
            CalendarType.FINISH -> finishCalendar
            CalendarType.START -> startCalendar
        }
        return DatePickerDialog(
            context,
            getOnDataSetListener(type, calendar),
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    private fun getOnDataSetListener(
        type: CalendarType,
        calendar: Calendar
    ): OnDateSetListener {
        val listener = OnDateSetListener { _, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            onDateChanged?.invoke(type, calendar)
        }
        return listener
    }
}