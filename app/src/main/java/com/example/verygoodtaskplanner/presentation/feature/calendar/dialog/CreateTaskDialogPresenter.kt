package com.example.verygoodtaskplanner.presentation.feature.calendar.dialog

import com.example.randomdog.presentation.base.BasePresenter
import com.example.verygoodtaskplanner.data.entities.Task
import com.example.verygoodtaskplanner.data.getFormattedDate
import com.example.verygoodtaskplanner.data.getFormattedTime
import com.example.verygoodtaskplanner.domain.interactors.DailyTasksInteractor
import com.example.verygoodtaskplanner.presentation.utils.CalendarType
import com.example.verygoodtaskplanner.presentation.utils.DatePickerRange
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class CreateTaskDialogPresenter : BasePresenter<CreateTaskDialogView>(), KoinComponent {
    private val dailyTasksInteractor by inject<DailyTasksInteractor>()

    fun isNextHourIsNextDay(hour: Int): Boolean = hour >= 23 //23+1=nextDay

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

    fun addTaskAndCloseDialog(task: Task) {
//        interactor.addTask(
//            Task
//        )
//            .subscribe(
//                {
//                    Toast.makeText(
//                        requireContext(),
//                        CreateTaskDialogFragment.TASK_ADDED,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    onTaskCreated?.invoke()
//                    dismiss()
//                },
//                {
//                    Toast.makeText(
//                        requireContext(),
//                        CreateTaskDialogFragment.TASK_NOT_ADDED,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    dismiss()
//                }
//            ))
    }
}