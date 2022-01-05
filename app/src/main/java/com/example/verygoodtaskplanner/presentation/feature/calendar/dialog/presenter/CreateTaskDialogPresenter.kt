package com.example.verygoodtaskplanner.presentation.feature.calendar.dialog.presenter


import com.example.verygoodtaskplanner.R
import com.example.verygoodtaskplanner.domain.interactors.DailyTasksInteractor
import com.example.verygoodtaskplanner.presentation.base.timerange.TimeRangePresenter
import com.example.verygoodtaskplanner.presentation.entities.TaskUI
import com.example.verygoodtaskplanner.presentation.feature.calendar.dialog.view.CreateTaskDialogView
import org.koin.core.component.inject

class CreateTaskDialogPresenter : TimeRangePresenter<CreateTaskDialogView>() {
    private val dailyTasksInteractor by inject<DailyTasksInteractor>()

    fun setDefaultTime() {
        viewState.makeDefaultTimeValue()
    }

    fun addTaskAndCloseDialog(taskUI: TaskUI) {
        when (validateTask(taskUI)) {
            ValidationError.NO_ERROR ->
                dailyTasksInteractor.addTask(taskUI)
                    .subscribe(
                        {
                            viewState.onSuccess()
                        },
                        {
                            viewState.onError(it.localizedMessage)
                        }
                    ).addToCompositeDisposable()
            ValidationError.END_MORE_THAN_START -> viewState.onError(
                R.string.error_end_more_than_start
            )
            ValidationError.NAME_IS_BLANK -> viewState.onError(
                R.string.task_name_is_blank
            )
        }
    }
}