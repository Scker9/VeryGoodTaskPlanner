package com.example.verygoodtaskplanner.presentation.feature.editor.view

import com.example.verygoodtaskplanner.presentation.base.timerange.TimeRangeView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface TaskEditorView : TimeRangeView {
    fun onSuccess(hasTaskChanged: Boolean)
    fun showAreYouSureDialog()
    fun closeEditor()
}