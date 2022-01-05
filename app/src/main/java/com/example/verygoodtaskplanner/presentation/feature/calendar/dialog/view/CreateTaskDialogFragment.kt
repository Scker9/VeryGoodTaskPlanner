package com.example.verygoodtaskplanner.presentation.feature.calendar.dialog.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.verygoodtaskplanner.R
import com.example.verygoodtaskplanner.data.getByTimeAndDateCalendars
import com.example.verygoodtaskplanner.data.getFormattedDate
import com.example.verygoodtaskplanner.data.getFormattedTime
import com.example.verygoodtaskplanner.databinding.TaskCreatorBinding
import com.example.verygoodtaskplanner.presentation.entities.TaskUI
import com.example.verygoodtaskplanner.presentation.feature.calendar.dialog.presenter.CreateTaskDialogPresenter
import com.example.verygoodtaskplanner.presentation.utils.CalendarType
import com.example.verygoodtaskplanner.presentation.utils.DatePickerRange
import com.example.verygoodtaskplanner.presentation.utils.TimePickerRange
import moxy.MvpAppCompatDialogFragment
import moxy.presenter.InjectPresenter
import org.koin.core.component.KoinComponent
import java.util.*

class CreateTaskDialogFragment : MvpAppCompatDialogFragment(), KoinComponent, CreateTaskDialogView {
    var onTaskCreated: (() -> Unit)? = null
    private var _binding: TaskCreatorBinding? = null
    private val binding get() = _binding!!
    private val timePickerRange by lazy { TimePickerRange(requireContext()) }
    private val datePickerRange by lazy {
        DatePickerRange(
            requireContext(),
            startCalendar = timePickerRange.startCalendar,
            finishCalendar = timePickerRange.finishCalendar //чтобы число совпадало если задача начинается в 23:00 и выше
        )
    }

    @InjectPresenter
    lateinit var presenter: CreateTaskDialogPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.setDefaultTime()         //по умолчанию конец задачи на час больше
        //коллбэки на установку даты и времени
        timePickerRange.onTimeChanged =
            { type, calendar ->
                presenter.displayNewTime(type, calendar)
            }
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
        presenter.displayDefault()
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
                presenter.addTaskAndCloseDialog(
                    TaskUI(
                        Calendar.Builder().getByTimeAndDateCalendars(
                            timePickerRange.startCalendar,
                            datePickerRange.startCalendar
                        ).timeInMillis,
                        Calendar.Builder().getByTimeAndDateCalendars(
                            timePickerRange.finishCalendar,
                            datePickerRange.finishCalendar
                        ).timeInMillis,
                        binding.chooseTaskNameEditText.text.toString(),
                        binding.taskDescriptionEditText.text.toString()
                    )
                )
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

    override fun showWhenCreated() {
        updateDate(CalendarType.START, datePickerRange.startCalendar.getFormattedDate())
        updateDate(CalendarType.FINISH, datePickerRange.finishCalendar.getFormattedDate())
        updateTime(CalendarType.START, datePickerRange.startCalendar.getFormattedTime())
        updateTime(CalendarType.FINISH, datePickerRange.finishCalendar.getFormattedTime())
    }

    override fun makeDefaultTimeValue() {
        timePickerRange.finishCalendar.set(
            Calendar.HOUR_OF_DAY,
            timePickerRange.startCalendar.get(Calendar.HOUR_OF_DAY) + 1
        )
    }


    override fun onSuccess() {
        Toast.makeText(
            requireContext(),
            getString(R.string.task_created_success),
            Toast.LENGTH_SHORT
        ).show()
        onTaskCreated?.invoke()
        dismiss()
    }

    override fun onError(errorMessage: String) {
        Toast.makeText(
            requireContext(),
            getString(R.string.task_creation_error, errorMessage),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onError(resId: Int) {
        Toast.makeText(
            requireContext(),
            getString(resId),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}