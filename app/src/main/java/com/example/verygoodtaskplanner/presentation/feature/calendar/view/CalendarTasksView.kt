package com.example.verygoodtaskplanner.presentation.feature.calendar.view

import com.example.randomdog.presentation.base.BaseView
import com.example.verygoodtaskplanner.data.entities.Hour
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface CalendarTasksView : BaseView {
    fun displayDailyTasks(tasks: ArrayList<Hour>)
}