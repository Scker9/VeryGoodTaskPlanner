package com.example.verygoodtaskplanner.presentation.feature.calendar.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.verygoodtaskplanner.databinding.TaskCreatorBinding
import com.example.verygoodtaskplanner.presentation.utils.CalendarType
import com.example.verygoodtaskplanner.presentation.utils.DatePickerRange
import com.example.verygoodtaskplanner.presentation.utils.TimePickerRange
import moxy.MvpAppCompatDialogFragment
import moxy.presenter.InjectPresenter
import org.koin.core.component.KoinComponent
import java.util.*

class CreateTaskDialogFragment : MvpAppCompatDialogFragment(), KoinComponent, CreateTaskDialogView {
    private val TAG = this::class.java.simpleName
    var onTaskCreated: (() -> Unit)? = null
    private var _binding: TaskCreatorBinding? = null
    private val binding get() = _binding!!
    private val datePickerRange by lazy {
        DatePickerRange(
            requireContext(),
            startCalendar = timePickerRange.startCalendar,
            finishCalendar = timePickerRange.finishCalendar //чтобы число совпадало если задача начинается в 23:00 и выше
        )
    }
    private val timePickerRange by lazy { TimePickerRange(requireContext()) }

    @InjectPresenter
    lateinit var presenter: CreateTaskDialogPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        timePickerRange.finishCalendar.set(
            Calendar.HOUR_OF_DAY,
            timePickerRange.startCalendar.get(Calendar.HOUR_OF_DAY) + 1
        )
        timePickerRange.onTimeChanged =
            { type, calendar ->
                presenter.displayNewTime(type, calendar)
            }
        //по умолчанию конец задачи на час больше
        datePickerRange.onDateChanged =
            { type, calendar ->
                presenter.displayNewDate(type, calendar)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TaskCreatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showDefaultTimeAndDate()
        with(binding)
        {
            //начальные значения
            //установка даты и времени
            chooseStartDateButton.setOnClickListener {
                presenter.createAndShowDateDialog(CalendarType.START)
            }
            chooseStartTimeButton.setOnClickListener {
                presenter.createAndShowTimeDialog(CalendarType.START)
            }
            chooseFinishDateButton.setOnClickListener {
                presenter.createAndShowDateDialog(CalendarType.FINISH)
            }
            chooseFinishTimeButton.setOnClickListener {
                presenter.createAndShowTimeDialog(CalendarType.FINISH)
            }
            //кнопка создать
            createTaskButton.setOnClickListener {
                //  presenter.addTaskAndCloseDialog()
            }
            cancelTaskCreationButton.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun showDateDialog(type: CalendarType) {
        datePickerRange.getDatePickerDialog(type).show()
    }

    override fun showTimeDialog(type: CalendarType) {
        timePickerRange.getTimePickerDialog(type).show()
    }

    override fun updateDate(type: CalendarType, date: String) {
        when (type) {
            CalendarType.START -> binding.chooseStartDateButton.text = date
            CalendarType.FINISH -> binding.chooseFinishDateButton.text = date
        }
    }

    override fun updateTime(type: CalendarType, time: String) {
        when (type) {
            CalendarType.START -> binding.chooseStartTimeButton.text = time
            CalendarType.FINISH -> binding.chooseFinishTimeButton.text = time
        }
    }

    private fun showDefaultTimeAndDate() {
        //важно, что сначала время, т.к дата зависит от времени
        presenter.displayNewTime(CalendarType.START, timePickerRange.startCalendar)
        presenter.displayNewTime(CalendarType.FINISH, timePickerRange.finishCalendar)
        presenter.displayNewDate(CalendarType.START, datePickerRange.startCalendar)
        presenter.displayNewDate(CalendarType.FINISH, datePickerRange.finishCalendar)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TASK_ADDED = "Задача успешно добавлена!"
        const val TASK_NOT_ADDED = "Что-то пошло не так..."
    }
}