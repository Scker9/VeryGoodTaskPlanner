package com.example.verygoodtaskplanner.presentation.entities

import com.example.verygoodtaskplanner.presentation.extensions.getFormattedDate
import com.example.verygoodtaskplanner.presentation.extensions.getFormattedTime
import java.util.*

abstract class TimeRange {
    abstract var dateStart: Long
    abstract var dateFinish: Long

    //по умолчанию возвращаем и дату и время
    fun getFormattedRange(): String =
        getFormattedRange(ReturnType.TIME_AND_DATE)

    fun getFormattedRange(type: ReturnType): String {
        val startCalendar = Calendar.Builder().setInstant(dateStart).build()
        val endCalendar = Calendar.Builder().setInstant(dateFinish).build()
        when (type) {
            ReturnType.TIME_ONLY -> return "%s - %s".format(
                startCalendar.getFormattedTime(),
                endCalendar.getFormattedTime()
            )
            ReturnType.DATE_ONLY -> return "%s - %s".format(
                startCalendar.getFormattedDate(),
                endCalendar.getFormattedDate()
            )
            ReturnType.TIME_AND_DATE -> return "%s %s - %s %s".format(
                startCalendar.getFormattedTime(),
                startCalendar.getFormattedDate(),
                endCalendar.getFormattedTime(),
                endCalendar.getFormattedDate()
            )
        }
    }

    enum class ReturnType {
        TIME_ONLY,
        DATE_ONLY,
        TIME_AND_DATE
    }

}
