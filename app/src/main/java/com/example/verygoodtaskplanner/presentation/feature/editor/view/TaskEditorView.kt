package com.example.verygoodtaskplanner.presentation.feature.editor.view

import com.example.randomdog.presentation.base.BaseView
import com.example.verygoodtaskplanner.data.entities.Task
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface TaskEditorView:BaseView {
    fun displayTaskProperties()
}