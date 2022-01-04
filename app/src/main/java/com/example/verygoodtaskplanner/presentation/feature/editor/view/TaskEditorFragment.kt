package com.example.verygoodtaskplanner.presentation.feature.editor.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import com.example.randomdog.presentation.base.BaseFragment
import com.example.verygoodtaskplanner.R
import com.example.verygoodtaskplanner.data.entities.Task
import com.example.verygoodtaskplanner.data.getByTimeAndDateCalendars
import com.example.verygoodtaskplanner.data.getFormattedDate
import com.example.verygoodtaskplanner.data.getFormattedTime
import com.example.verygoodtaskplanner.databinding.TaskEditorBinding
import com.example.verygoodtaskplanner.presentation.base.time.TimeRangePicker
import com.example.verygoodtaskplanner.presentation.entities.TaskUI
import com.example.verygoodtaskplanner.presentation.feature.editor.presenter.TaskEditorPresenter
import com.example.verygoodtaskplanner.presentation.utils.CalendarType
import com.example.verygoodtaskplanner.presentation.utils.DatePickerRange
import com.example.verygoodtaskplanner.presentation.utils.TimePickerRange
import com.github.terrakok.cicerone.Router
import moxy.presenter.InjectPresenter
import org.koin.core.component.inject
import java.util.*

class TaskEditorFragment : BaseFragment<TaskEditorBinding>(), TaskEditorView {
    private val router by inject<Router>()
    private var task: TaskUI? = null

    @InjectPresenter
    lateinit var presenter: TaskEditorPresenter
    private val datePickerRange: DatePickerRange by lazy {
        DatePickerRange(
            requireContext(),
            startCalendar = Calendar.Builder().setInstant(task!!.dateStart).build(),
            finishCalendar = Calendar.Builder().setInstant(task!!.dateFinish).build()
        )
    }
    private val timePickerRange: TimePickerRange by lazy {
        TimePickerRange(
            requireContext(),
            startCalendar = Calendar.Builder().setInstant(task!!.dateStart).build(),
            finishCalendar = Calendar.Builder().setInstant(task!!.dateFinish).build()
        )
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> TaskEditorBinding
        get() = TaskEditorBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        task = this.arguments?.getParcelable(TASK_TAG)
        datePickerRange.onDateChanged =
            { calendarType, calendar ->

            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.displayTaskProperties()
        with(binding)
        {
            //установка даты и времени
            chooseStartDateButton.setOnClickListener {
                datePickerRange.getDatePickerDialog(CalendarType.START).show()
            }
            chooseStartTimeButton.setOnClickListener {
                // getTimePickerDialog(requireContext(), TimeRangePicker.Type.START).show()
            }
            chooseFinishDateButton.setOnClickListener {
                // getDatePickerDialog(requireContext(), TimeRangePicker.Type.FINISH).show()
            }
            chooseFinishTimeButton.setOnClickListener {
                //  getTimePickerDialog(requireContext(), TimeRangePicker.Type.FINISH).show()
            }
            saveEditedTask.setOnClickListener {
                presenter.saveChanges(
                    oldTask = task!!,
                    newTask = Task(
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
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_delete_task -> {
                        presenter.deleteTask(task!!.id!!)
                    }
                }
                return@setOnMenuItemClickListener true
            }
        }
    }

    override fun onSuccess(message: String, hasTaskChanged: Boolean) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        router.sendResult(SAVING_RESULT_KEY, hasTaskChanged)
        router.exit()
    }

    override fun showTaskProperties() {
        with(binding)
        {
            chooseStartDateButton.text = datePickerRange.startCalendar.getFormattedDate()
            chooseStartTimeButton.text = timePickerRange.startCalendar.getFormattedTime()
            chooseFinishDateButton.text = datePickerRange.finishCalendar.getFormattedDate()
            chooseFinishTimeButton.text = timePickerRange.finishCalendar.getFormattedTime()
            chooseTaskNameEditText.setText(task?.name)
            taskDescriptionEditText.setText(task?.description)
        }
    }

    override fun updateDate(type: CalendarType, date: String) {
        with(binding)
        {
            when (type) {
                CalendarType.START -> chooseStartDateButton.text =
                    date
                CalendarType.FINISH -> chooseFinishDateButton.text =
                    date
            }
        }
    }

    override fun updateTime(type: CalendarType, time: String) {
        when (type) {
            CalendarType.START -> binding.chooseStartTimeButton.text =
                time
            CalendarType.FINISH -> binding.chooseFinishTimeButton.text =
                time
        }
    }

    override fun onError(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        router.exit()
    }

    fun onDateChanged(type: TimeRangePicker.Type, calendar: Calendar) {
        when (type) {
            TimeRangePicker.Type.START -> binding.chooseStartDateButton.text =
                calendar.getFormattedDate()
            TimeRangePicker.Type.FINISH -> binding.chooseFinishDateButton.text =
                calendar.getFormattedDate()
        }
    }

    fun onTimeChanged(type: TimeRangePicker.Type, calendar: Calendar) {
        when (type) {
            TimeRangePicker.Type.START -> binding.chooseStartTimeButton.text =
                calendar.getFormattedTime()
            TimeRangePicker.Type.FINISH -> binding.chooseFinishTimeButton.text =
                calendar.getFormattedTime()
        }
    }

    companion object {
        const val SAVING_RESULT_KEY = "task_manipulation"
        private const val TASK_TAG = "got_task"
        fun newInstance(taskUI: TaskUI): TaskEditorFragment {
            val bundle = Bundle()
            bundle.putParcelable(TASK_TAG, taskUI)
            val fragment = TaskEditorFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}