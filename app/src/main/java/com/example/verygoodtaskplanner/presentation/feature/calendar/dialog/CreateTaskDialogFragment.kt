package com.example.verygoodtaskplanner.presentation.feature.calendar.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.verygoodtaskplanner.data.entities.Task
import com.example.verygoodtaskplanner.data.getFormattedDate
import com.example.verygoodtaskplanner.data.getFormattedTime
import com.example.verygoodtaskplanner.databinding.TaskCreatorBinding
import com.example.verygoodtaskplanner.domain.interactors.DailyTasksInteractor
import com.example.verygoodtaskplanner.presentation.base.TimeRangePicker
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

//тут отходим от чистой архитектуры чуть-чуть...
class CreateTaskDialogFragment : DialogFragment(), KoinComponent, TimeRangePicker {
    private val TAG = this::class.java.simpleName
    var onTaskCreated: (() -> Unit)? = null
    private var _binding: TaskCreatorBinding? = null
    private val binding get() = _binding!!

    private val interactor by inject<DailyTasksInteractor>()
    override val startCalendar: Calendar = Calendar.getInstance()
    override val finishCalendar: Calendar = Calendar.getInstance()
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //по умолчанию конец задачи на час больше
        finishCalendar.set(Calendar.HOUR_OF_DAY, startCalendar.get(Calendar.HOUR_OF_DAY) + 1)
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
        //начальные значения
        binding.chooseStartDateButton.text = startCalendar.getFormattedDate()
        binding.chooseStartTimeButton.text = startCalendar.getFormattedTime()
        binding.chooseFinishDateButton.text = finishCalendar.getFormattedDate()
        binding.chooseFinishTimeButton.text = finishCalendar.getFormattedTime()
        //установка даты и времени
        binding.chooseStartDateButton.setOnClickListener {
            getDatePickerDialog(requireContext(), TimeRangePicker.ListenerType.START).show()
        }
        binding.chooseStartTimeButton.setOnClickListener {
            getTimePickerDialog(requireContext(), TimeRangePicker.ListenerType.START).show()
        }
        binding.chooseFinishDateButton.setOnClickListener {
            getDatePickerDialog(requireContext(), TimeRangePicker.ListenerType.FINISH).show()
        }
        binding.chooseFinishTimeButton.setOnClickListener {
            getTimePickerDialog(requireContext(), TimeRangePicker.ListenerType.FINISH).show()
        }
        //кнопка создать
        binding.createTaskButton.setOnClickListener {
            addTaskAndCloseDialog()
        }
        binding.cancelTaskCreationButton.setOnClickListener {
            dismiss()
        }
    }


    override fun onDateChanged(type: TimeRangePicker.ListenerType, calendar: Calendar) {
        when (type) {
            TimeRangePicker.ListenerType.START -> binding.chooseStartDateButton.text =
                calendar.getFormattedDate()
            TimeRangePicker.ListenerType.FINISH -> binding.chooseFinishDateButton.text =
                calendar.getFormattedDate()
        }
    }

    override fun onTimeChanged(type: TimeRangePicker.ListenerType, calendar: Calendar) {
        when (type) {
            TimeRangePicker.ListenerType.START -> binding.chooseStartTimeButton.text =
                calendar.getFormattedTime()
            TimeRangePicker.ListenerType.FINISH -> binding.chooseFinishTimeButton.text =
                calendar.getFormattedTime()
        }
    }

    private fun addTaskAndCloseDialog() {
        disposable.add(interactor.addTask(
            Task
                (
                startCalendar.timeInMillis,
                finishCalendar.timeInMillis,
                binding.chooseTaskNameEditText.text.toString(),
                binding.taskDescriptionEditText.text.toString()
            )
        )
            .subscribe(
                {
                    Toast.makeText(requireContext(), TASK_ADDED, Toast.LENGTH_SHORT).show()
                    onTaskCreated?.invoke()
                    dismiss()
                },
                {
                    Toast.makeText(requireContext(), TASK_NOT_ADDED, Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            ))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.dispose()
        _binding = null
    }

    companion object {
        const val TASK_ADDED = "Задача успешно добавлена!"
        const val TASK_NOT_ADDED = "Что-то пошло не так..."
    }
}