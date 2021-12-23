package com.example.verygoodtaskplanner.data.entities

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

abstract class TimeRange {
    abstract var dateStart: Long
    abstract var dateFinish: Long

    //по умолчанию возвращаем и дату и время
    @RequiresApi(Build.VERSION_CODES.O)
    fun getFormattedRange(): String =
        getFormattedRange(ReturnType.TIME_AND_DATE)

    @RequiresApi(Build.VERSION_CODES.O)
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

    fun Calendar.getFormattedTime(): String =
        "%02d:%02d".format(this.get(Calendar.HOUR_OF_DAY), this.get(Calendar.MINUTE))

    fun Calendar.getFormattedDate(): String =
        "%02d.%02d.%d".format(
            this.get(Calendar.DAY_OF_MONTH),
            this.get(Calendar.MONTH) + 1 /*идёт нумерация с нуля */,
            this.get(Calendar.YEAR)
        )


}
