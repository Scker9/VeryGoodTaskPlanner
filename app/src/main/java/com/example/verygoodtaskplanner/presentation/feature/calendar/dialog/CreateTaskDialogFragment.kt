package com.example.verygoodtaskplanner.presentation.feature.calendar.dialog

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
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
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

//тут отходим от чистой архитектуры чуть-чуть...
class CreateTaskDialogFragment : DialogFragment(), KoinComponent {
    private val TAG = this::class.java.simpleName

    private var _binding: TaskCreatorBinding? = null
    private val binding get() = _binding!!

    private val interactor by inject<DailyTasksInteractor>()
    private val startCalendar = Calendar.getInstance()
    private val finishCalendar = Calendar.getInstance()
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
    ): View? {
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
            DatePickerDialog(
                requireContext(),
                getOnDataSetListener(ListenerType.START, startCalendar),
                startCalendar.get(Calendar.YEAR),
                startCalendar.get(Calendar.MONTH),
                startCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        binding.chooseStartTimeButton.setOnClickListener {
            TimePickerDialog(
                requireContext(),
                getOnTimeSetListener(ListenerType.START, startCalendar),
                startCalendar.get(Calendar.HOUR_OF_DAY),
                startCalendar.get(Calendar.MINUTE), true
            ).show()
        }
        binding.chooseFinishDateButton.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                getOnDataSetListener(ListenerType.FINISH, finishCalendar),
                finishCalendar.get(Calendar.YEAR),
                finishCalendar.get(Calendar.MONTH),
                finishCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        binding.chooseFinishTimeButton.setOnClickListener {
            TimePickerDialog(
                requireContext(),
                getOnTimeSetListener(ListenerType.FINISH, finishCalendar),
                finishCalendar.get(Calendar.HOUR_OF_DAY),
                finishCalendar.get(Calendar.MINUTE),
                true
            ).show()
        }
        //кнопка создать
        binding.createTaskButton.setOnClickListener {
            addTaskAndCloseDialog()
        }
        binding.cancelTaskCreationButton.setOnClickListener {
            dismiss()
        }
    }

    private fun getOnDataSetListener(
        type: ListenerType,
        calendar: Calendar
    ): DatePickerDialog.OnDateSetListener {
        val listener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            onDateChanged(type, calendar)
        }
        return listener
    }

    private fun getOnTimeSetListener(
        type: ListenerType,
        calendar: Calendar
    ): TimePickerDialog.OnTimeSetListener {
        val listener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            onTimeChanged(type, calendar)
        }
        return listener
    }

    private fun onDateChanged(type: ListenerType, calendar: Calendar) {
        when (type) {
            ListenerType.START -> binding.chooseStartDateButton.text = calendar.getFormattedDate()
            ListenerType.FINISH -> binding.chooseFinishDateButton.text = calendar.getFormattedDate()
        }
    }

    private fun onTimeChanged(type: ListenerType, calendar: Calendar) {
        when (type) {
            ListenerType.START -> binding.chooseStartTimeButton.text = calendar.getFormattedTime()
            ListenerType.FINISH -> binding.chooseFinishTimeButton.text = calendar.getFormattedTime()
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
                    dismiss()
                },
                {
                    Log.d(TAG, "Error task is not added!")
                    dismiss()
                }
            ))
    }

    enum class ListenerType {
        START,
        FINISH
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.dispose()
        _binding = null
    }

    companion object {
        const val TASK_ADDED = "Задача успешно добавлена!"
    }
}