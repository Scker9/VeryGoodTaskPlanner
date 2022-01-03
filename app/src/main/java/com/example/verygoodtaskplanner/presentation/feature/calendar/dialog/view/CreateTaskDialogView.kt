package com.example.verygoodtaskplanner.presentation.feature.calendar.dialog.view

import com.example.randomdog.presentation.base.BaseView
import com.example.verygoodtaskplanner.presentation.utils.CalendarType
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndStrategy::class)
interface CreateTaskDialogView : BaseView {
    fun showDateDialog(type: CalendarType)
    fun showTimeDialog(type: CalendarType)
    fun updateDate(type: CalendarType, date: String)
    fun updateTime(type: CalendarType, time: String)
    fun showWhenCreated()
    fun makeDefaultTimeValue()
    fun onErrorNoClose(errorMessage:String)
}