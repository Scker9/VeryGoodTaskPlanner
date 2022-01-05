package com.example.verygoodtaskplanner.presentation.feature.calendar.dialog.view

import com.example.verygoodtaskplanner.presentation.base.timerange.TimeRangeView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndStrategy::class)
interface CreateTaskDialogView : TimeRangeView {
    fun makeDefaultTimeValue()
}