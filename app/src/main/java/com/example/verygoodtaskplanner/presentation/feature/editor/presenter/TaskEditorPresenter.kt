package com.example.verygoodtaskplanner.presentation.feature.editor.presenter

import com.example.randomdog.presentation.base.BasePresenter
import com.example.verygoodtaskplanner.data.entities.Task
import com.example.verygoodtaskplanner.domain.interactors.DailyTasksInteractor
import com.example.verygoodtaskplanner.presentation.feature.editor.view.TaskEditorView
import org.koin.core.component.inject

class TaskEditorPresenter : BasePresenter<TaskEditorView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.displayTaskProperties()
    }
    val DailyTasksInteractor by inject<DailyTasksInteractor>()
}