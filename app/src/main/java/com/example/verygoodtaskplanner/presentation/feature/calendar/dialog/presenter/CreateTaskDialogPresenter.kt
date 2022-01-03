package com.example.verygoodtaskplanner.presentation.feature.calendar.dialog.presenter

import com.example.randomdog.presentation.base.BasePresenter
import com.example.verygoodtaskplanner.data.entities.Task
import com.example.verygoodtaskplanner.data.getFormattedDate
import com.example.verygoodtaskplanner.data.getFormattedTime
import com.example.verygoodtaskplanner.domain.interactors.DailyTasksInteractor
import com.example.verygoodtaskplanner.presentation.feature.calendar.dialog.view.CreateTaskDialogView
import com.example.verygoodtaskplanner.presentation.utils.CalendarType
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class CreateTaskDialogPresenter : BasePresenter<CreateTaskDialogView>() {
    private val dailyTasksInteractor by inject<DailyTasksInteractor>()

    fun createAndShowDateDialog(type: CalendarType) {
        viewState.showDateDialog(type)
    }

    fun createAndShowTimeDialog(type: CalendarType) {
        viewState.showTimeDialog(type)
    }

    fun displayNewDate(type: CalendarType, calendar: Calendar) {
        viewState.updateDate(type, calendar.getFormattedDate())
    }

    fun displayNewTime(type: CalendarType, calendar: Calendar) {
        viewState.updateTime(type, calendar.getFormattedTime())
    }

    fun displayDefault() {
        viewState.showWhenCreated()
    }

    fun setDefaultTime() {
        viewState.makeDefaultTimeValue()
    }

    fun addTaskAndCloseDialog(task: Task) {
        when (validateTask(task)) {
            ValidationError.NO_ERROR ->
                dailyTasksInteractor.addTask(task)
                    .subscribe(
                        {
                            viewState.onSuccess()
                        },
                        {
                            viewState.onError(it.localizedMessage)
                        }
                    ).addToCompositeDisposable()
            ValidationError.END_MORE_THAN_START -> viewState.onErrorNoClose(
                ERROR_END_MORE_THAN_START_MSG
            )
            ValidationError.NAME_IS_BLANK -> viewState.onErrorNoClose(NAME_IS_BLANK_MSG)
        }
    }

    private fun validateTask(task: Task): ValidationError {
        //можно придумать ещё ограничения...
        if (task.dateStart >= task.dateFinish) {
            return ValidationError.END_MORE_THAN_START
        }
        if (task.name.isBlank()) {
            return ValidationError.NAME_IS_BLANK
        }
        return ValidationError.NO_ERROR
    }

    private enum class ValidationError {
        NO_ERROR,
        END_MORE_THAN_START,
        NAME_IS_BLANK
    }

    companion object {
        private const val ERROR_END_MORE_THAN_START_MSG =
            "Нельзя ставить заканчивать задачи в прошлом...."
        private const val NAME_IS_BLANK_MSG = "Имя задачи не может быть пустым"
    }
}