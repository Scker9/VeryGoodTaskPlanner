package com.example.verygoodtaskplanner.presentation.feature.calendar.presenter

import com.example.verygoodtaskplanner.presentation.base.BasePresenter
import com.example.verygoodtaskplanner.domain.interactors.DailyTasksInteractor
import com.example.verygoodtaskplanner.presentation.feature.calendar.view.CalendarTasksView
import org.koin.core.component.inject
import java.util.*

class CalendarTaskPresenter : BasePresenter<CalendarTasksView>() {
    private val interactor by inject<DailyTasksInteractor>()
    override fun onFirstViewAttach() {

        getTasksByDay(getCurrentDateCalendar().timeInMillis)
        super.onFirstViewAttach()
    }

    fun getTasksByDay(day: Long) {
        interactor.getHoursWithTasks(day).subscribe(
            { hours ->
                viewState.displayDailyTasks(hours)
            },
            {
                viewState.onError(it.localizedMessage)
            }
        ).addToCompositeDisposable()
    }

    private fun getCurrentDateCalendar(): Calendar {
        val calendar = Calendar.getInstance()
        //выставляем начало текущего дня
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar
    }

}