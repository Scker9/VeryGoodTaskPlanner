package com.example.verygoodtaskplanner

import com.example.verygoodtaskplanner.data.repositories.HourRepositoryImpl
import com.example.verygoodtaskplanner.data.utils.ADD_HOUR
import com.example.verygoodtaskplanner.domain.repositories.HourRepository
import org.junit.Assert.assertEquals
import org.junit.Test

class HourRepositoryUnitTest {
    private val repository: HourRepository = HourRepositoryImpl()

    @Test
    fun `assert hours finish date `() {
        val startDate = 1641502800_000 // 07.01.2022 00:00
        repository.getDayHours(startDate)
            .forEach {
                val expectedFinish = it.dateStart + ADD_HOUR
                assertEquals(
                    expectedFinish,
                    it.dateFinish
                )
            }
    }
}