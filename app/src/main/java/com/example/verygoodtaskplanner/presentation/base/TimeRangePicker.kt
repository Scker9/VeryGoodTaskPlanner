package com.example.verygoodtaskplanner.presentation.base

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import java.util.*

interface TimeRangePicker {
    val startCalendar: Calendar
    val finishCalendar: Calendar

    fun onDateChanged(type: ListenerType, calendar: Calendar)
    fun onTimeChanged(type: ListenerType, calendar: Calendar)

    fun getDatePickerDialog(context: Context, type: ListenerType): DatePickerDialog {
        val calendar = when (type) {
            ListenerType.FINISH -> finishCalendar
            ListenerType.START -> startCalendar
        }
        return DatePickerDialog(
            context,
            getOnDataSetListener(type, calendar),
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    fun getTimePickerDialog(context: Context, type: ListenerType): TimePickerDialog {
        val calendar = when (type) {
            ListenerType.FINISH -> finishCalendar
            ListenerType.START -> startCalendar
        }
        return TimePickerDialog(
            context,
            getOnTimeSetListener(type, calendar),
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
    }

    private fun getOnDataSetListener(
        type: ListenerType,
        calendar: Calendar
    ): DatePickerDialog.OnDateSetListener {
        val listener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            onDateChanged(type, calendar)
        }
        return listener
    }

    private fun getOnTimeSetListener(
        type: ListenerType,
        calendar: Calendar
    ): TimePickerDialog.OnTimeSetListener {
        val listener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            onTimeChanged(type, calendar)
        }
        return listener
    }

    enum class ListenerType {
        START,
        FINISH
    }
}