package com.example.verygoodtaskplanner.domain.repositories

import com.example.verygoodtaskplanner.data.entities.Hour
import io.reactivex.Single

interface HourRepository {
    fun getDayHours(startDate: Long): ArrayList<Hour>
}