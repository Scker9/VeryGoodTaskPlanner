package com.example.verygoodtaskplanner.presentation.base.timerange

import com.example.verygoodtaskplanner.presentation.base.BasePresenter
import com.example.verygoodtaskplanner.presentation.extensions.getFormattedDate
import com.example.verygoodtaskplanner.presentation.extensions.getFormattedTime
import com.example.verygoodtaskplanner.presentation.entities.TaskUI
import com.example.verygoodtaskplanner.presentation.utils.timepickers.CalendarType
import java.util.*

abstract class TimeRangePresenter<V : TimeRangeView> : BasePresenter<V>() {
    fun displayNewDate(type: CalendarType, calendar: Calendar) {
        viewState.updateDate(type, calendar.getFormattedDate())
    }

    fun displayNewTime(type: CalendarType, calendar: Calendar) {
        viewState.updateTime(type, calendar.getFormattedTime())
    }

    fun createAndShowDateDialog(type: CalendarType) {
        viewState.showDateDialog(type)
    }

    fun createAndShowTimeDialog(type: CalendarType) {
        viewState.showTimeDialog(type)
    }

    fun displayDefault() {
        viewState.showWhenCreated()
    }

    protected fun validateTask(taskUI: TaskUI): ValidationError {
        //можно придумать ещё ограничения...
        if (taskUI.dateStart >= taskUI.dateFinish) {
            return ValidationError.END_MORE_THAN_START
        }
        if (taskUI.name.isBlank()) {
            return ValidationError.NAME_IS_BLANK
        }
        return ValidationError.NO_ERROR
    }

    protected enum class ValidationError {
        NO_ERROR,
        END_MORE_THAN_START,
        NAME_IS_BLANK
    }
}