package com.example.verygoodtaskplanner.presentation.base.timerange

import com.example.verygoodtaskplanner.presentation.base.BaseView
import com.example.verygoodtaskplanner.presentation.utils.CalendarType
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndStrategy::class)
interface TimeRangeView : BaseView {
    fun showDateDialog(type: CalendarType)
    fun showTimeDialog(type: CalendarType)
    fun updateDate(type: CalendarType, date: String)
    fun updateTime(type: CalendarType, time: String)
    fun showWhenCreated()
    fun onError(resId: Int)
}