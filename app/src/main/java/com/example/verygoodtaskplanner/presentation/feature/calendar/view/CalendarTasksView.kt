package com.example.verygoodtaskplanner.presentation.feature.calendar.view

import com.example.verygoodtaskplanner.presentation.base.BaseView
import com.example.verygoodtaskplanner.presentation.entities.HourUI
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface CalendarTasksView : BaseView {
    fun displayDailyTasks(tasks: List<HourUI>)
}