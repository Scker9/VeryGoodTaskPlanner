package com.example.verygoodtaskplanner.presentation.feature.editor.view

import com.example.randomdog.presentation.base.BaseView
import com.example.verygoodtaskplanner.data.entities.Task
import com.example.verygoodtaskplanner.presentation.utils.CalendarType
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface TaskEditorView : BaseView {
    fun onSuccess(message: String, hasTaskChanged: Boolean)
    fun showTaskProperties()
    fun updateDate(type: CalendarType, date: String)
    fun updateTime(type: CalendarType, time: String)
}