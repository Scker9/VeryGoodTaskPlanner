package com.example.verygoodtaskplanner.presentation.feature.calendar.view

import com.example.randomdog.presentation.base.BaseView
import com.example.verygoodtaskplanner.data.entities.Hour
import com.example.verygoodtaskplanner.data.entities.Task
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle

@StateStrategyType(AddToEndStrategy::class)
interface CalendarTasksView : BaseView {
    fun displayHours(tasks: ArrayList<Hour>)
}