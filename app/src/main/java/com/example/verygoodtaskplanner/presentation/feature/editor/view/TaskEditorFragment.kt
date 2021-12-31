package com.example.verygoodtaskplanner.presentation.feature.editor.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import com.example.randomdog.presentation.base.BaseFragment
import com.example.verygoodtaskplanner.R
import com.example.verygoodtaskplanner.data.entities.Task
import com.example.verygoodtaskplanner.data.getFormattedDate
import com.example.verygoodtaskplanner.data.getFormattedTime
import com.example.verygoodtaskplanner.databinding.TaskEditorBinding
import com.example.verygoodtaskplanner.presentation.base.time.TimeRangePicker
import com.example.verygoodtaskplanner.presentation.feature.editor.presenter.TaskEditorPresenter
import com.github.terrakok.cicerone.Router
import moxy.presenter.InjectPresenter
import org.koin.core.component.inject
import java.util.*

class TaskEditorFragment : BaseFragment<TaskEditorBinding>(), TaskEditorView, TimeRangePicker {
    private val router by inject<Router>()
    private var task: Task? = null

    @InjectPresenter
    lateinit var presenter: TaskEditorPresenter
    override val startCalendar: Calendar by lazy {
        Calendar.Builder().setInstant(task!!.dateStart).build()
    }
    override val finishCalendar: Calendar by lazy {
        Calendar.Builder().setInstant(task!!.dateFinish).build()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> TaskEditorBinding
        get() = TaskEditorBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        task = this.arguments?.getParcelable(TASK_TAG)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayTaskProperties()
        with(binding)
        {
            //установка даты и времени
            chooseStartDateButton.setOnClickListener {
                getDatePickerDialog(requireContext(), TimeRangePicker.Type.START).show()
            }
            chooseStartTimeButton.setOnClickListener {
                getTimePickerDialog(requireContext(), TimeRangePicker.Type.START).show()
            }
            chooseFinishDateButton.setOnClickListener {
                getDatePickerDialog(requireContext(), TimeRangePicker.Type.FINISH).show()
            }
            chooseFinishTimeButton.setOnClickListener {
                getTimePickerDialog(requireContext(), TimeRangePicker.Type.FINISH).show()
            }
            saveEditedTask.setOnClickListener {
                presenter.saveChanges(
                    oldTask = task!!,
                    newTask = Task(
                        startCalendar.timeInMillis,
                        finishCalendar.timeInMillis,
                        binding.chooseTaskNameEditText.text.toString(),
                        binding.taskDescriptionEditText.text.toString(),
                        task!!.id
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

    private fun displayTaskProperties() {
        binding.chooseStartDateButton.text = startCalendar.getFormattedDate()
        binding.chooseStartTimeButton.text = startCalendar.getFormattedTime()
        binding.chooseFinishDateButton.text = finishCalendar.getFormattedDate()
        binding.chooseFinishTimeButton.text = finishCalendar.getFormattedTime()
        binding.chooseTaskNameEditText.setText(task?.name)
        binding.taskDescriptionEditText.setText(task?.description)
    }

    override fun onSuccess(message: String, didTaskChanged: Boolean) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        router.sendResult(SAVING_RESULT_KEY, didTaskChanged)
        router.exit()
    }

    override fun onError(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        router.exit()
    }

    override fun onDateChanged(type: TimeRangePicker.Type, calendar: Calendar) {
        when (type) {
            TimeRangePicker.Type.START -> binding.chooseStartDateButton.text =
                calendar.getFormattedDate()
            TimeRangePicker.Type.FINISH -> binding.chooseFinishDateButton.text =
                calendar.getFormattedDate()
        }
    }

    override fun onTimeChanged(type: TimeRangePicker.Type, calendar: Calendar) {
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
        fun newInstance(task: Task): TaskEditorFragment {
            val bundle = Bundle()
            bundle.putParcelable(TASK_TAG, task)
            val fragment = TaskEditorFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}