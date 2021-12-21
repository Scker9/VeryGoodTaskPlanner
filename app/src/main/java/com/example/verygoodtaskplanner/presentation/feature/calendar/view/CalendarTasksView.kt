package com.example.verygoodtaskplanner.presentation.feature.calendar.view

import com.example.randomdog.presentation.base.BaseView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface CalendarTasksView : BaseView {
}