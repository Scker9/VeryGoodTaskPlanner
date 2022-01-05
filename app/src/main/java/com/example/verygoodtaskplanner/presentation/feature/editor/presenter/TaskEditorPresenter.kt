package com.example.verygoodtaskplanner.presentation.feature.editor.presenter

import com.example.verygoodtaskplanner.R
import com.example.verygoodtaskplanner.domain.interactors.DailyTasksInteractor
import com.example.verygoodtaskplanner.presentation.base.timerange.TimeRangePresenter
import com.example.verygoodtaskplanner.presentation.entities.TaskUI
import com.example.verygoodtaskplanner.presentation.feature.editor.view.TaskEditorView
import org.koin.core.component.inject

class TaskEditorPresenter : TimeRangePresenter<TaskEditorView>() {
    private val dailyTasksInteractor by inject<DailyTasksInteractor>()
    fun deleteTask(id: Long) {
        dailyTasksInteractor.deleteTaskById(id).subscribe(
            {
                viewState.onSuccess(R.string.task_deleted, true)
            },
            {
                viewState.onError(it.localizedMessage)
            }
        ).addToCompositeDisposable()
    }

    fun saveChanges(oldTask: TaskUI, newTask: TaskUI) {
        when (validateTask(newTask)) {
            ValidationError.NO_ERROR ->
                if (oldTask != newTask) {
                    dailyTasksInteractor.updateTask(newTask)
                        .subscribe(
                            {
                                viewState.onSuccess(R.string.task_saved, true)
                            },
                            {
                                viewState.onError(it.localizedMessage)
                            }
                        ).addToCompositeDisposable()
                } else {
                    viewState.onSuccess(R.string.task_saved, false)
                }
            ValidationError.END_MORE_THAN_START -> viewState.onError(
                R.string.error_end_more_than_start
            )
            ValidationError.NAME_IS_BLANK -> viewState.onError(
                R.string.task_name_is_blank
            )
        }

    }
}