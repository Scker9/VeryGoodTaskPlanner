package com.example.verygoodtaskplanner.presentation.feature.calendar.presenter

import android.util.Log
import com.example.randomdog.presentation.base.BasePresenter
import com.example.verygoodtaskplanner.domain.interactors.DailyTasksInteractor
import com.example.verygoodtaskplanner.presentation.feature.calendar.view.CalendarTasksView
import com.github.terrakok.cicerone.Router
import org.koin.core.component.inject
import java.util.*

class CalendarTaskPresenter : BasePresenter<CalendarTasksView>() {
    private val TAG = this::class.java.simpleName
    private val interactor by inject<DailyTasksInteractor>()
    override fun onFirstViewAttach() {
        getTasksByDay(Calendar.getInstance().timeInMillis)
        super.onFirstViewAttach()
    }

    fun getTasksByDay(day: Long) {
        interactor.getHoursWithTasks(day).subscribe(
            { hours ->
                viewState.displayDailyTasks(hours)
                hours.map { it.tasks }.forEach { task ->
                    if (task.isNotEmpty()) {
                        Log.d(TAG, " Got " + task.toString())
                    }
                }
            },
            {
                Log.d(TAG, it.localizedMessage)
            }
        ).addToCompositeDisposable()
    }
}