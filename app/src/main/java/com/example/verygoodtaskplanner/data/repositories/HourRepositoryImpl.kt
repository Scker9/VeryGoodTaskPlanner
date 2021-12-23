package com.example.verygoodtaskplanner.data.repositories

import com.example.verygoodtaskplanner.data.entities.Hour
import com.example.verygoodtaskplanner.domain.repositories.HourRepository
import io.reactivex.Single
import java.util.*
import kotlin.collections.ArrayList

class HourRepositoryImpl : HourRepository {
    override fun get24HourByTimeStamp(startDate: Long): Single<ArrayList<Hour>> {
        TODO()
    }
}