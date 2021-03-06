package com.example.verygoodtaskplanner.data.repositories

import com.example.verygoodtaskplanner.data.entities.Hour
import com.example.verygoodtaskplanner.domain.repositories.HourRepository
import java.util.*
import kotlin.collections.ArrayList

class HourRepositoryImpl : HourRepository {
    override fun getDayHours(startDate: Long): ArrayList<Hour> {
        val listOfHours: ArrayList<Hour> = arrayListOf()
        for (i in START_DAY_HOUR..END_DAY_HOUR) {
            val startCalendar = Calendar.Builder().setInstant(startDate).build()
            startCalendar.set(Calendar.HOUR_OF_DAY, i)
            startCalendar.set(Calendar.MINUTE, 0)
            listOfHours.add(Hour(startCalendar.timeInMillis))
        }
        return listOfHours
    }

    companion object {
        private const val START_DAY_HOUR = 0
        private const val END_DAY_HOUR = 23
    }
}