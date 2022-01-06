package com.example.verygoodtaskplanner.presentation.feature.editor.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.example.verygoodtaskplanner.presentation.base.BaseFragment
import com.example.verygoodtaskplanner.R
import com.example.verygoodtaskplanner.data.getByTimeAndDateCalendars
import com.example.verygoodtaskplanner.data.getFormattedDate
import com.example.verygoodtaskplanner.data.getFormattedTime
import com.example.verygoodtaskplanner.databinding.TaskEditorBinding
import com.example.verygoodtaskplanner.presentation.utils.Tags
import com.example.verygoodtaskplanner.presentation.utils.Tags.GOT_TASK_TAG
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
        task = this.arguments?.getParcelable(GOT_TASK_TAG)
        timePickerRange.onTimeChanged =
            { type, calendar ->
                presenter.displayNewTime(type, calendar)
            }
        datePickerRange.onDateChanged =
            { type, calendar ->
                presenter.displayNewDate(type, calendar)
            }
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    presenter.checkForChangesAndShowDialog(
                        oldTask = task!!,
                        newTask = collectUIDataIntoTask()
                    )
                }
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.displayDefault()
        with(binding)
        {
            //установка даты и времени
            chooseStartDateButton.setOnClickListener {
                datePickerRange.getDatePickerDialog(CalendarType.START).show()
            }
            chooseStartTimeButton.setOnClickListener {
                timePickerRange.getTimePickerDialog(CalendarType.START).show()
            }
            chooseFinishDateButton.setOnClickListener {
                datePickerRange.getDatePickerDialog(CalendarType.FINISH).show()
            }
            chooseFinishTimeButton.setOnClickListener {
                timePickerRange.getTimePickerDialog(CalendarType.FINISH).show()
            }
            saveEditedTask.setOnClickListener {
                presenter.saveChanges(
                    oldTask = task!!,
                    newTask = collectUIDataIntoTask()
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

    override fun onSuccess(hasTaskChanged: Boolean) {
        if (hasTaskChanged) {
            Toast.makeText(requireContext(), getString(R.string.task_saved), Toast.LENGTH_SHORT)
                .show()
        }
        router.sendResult(Tags.EDITOR_SAVING_RESULT_KEY, hasTaskChanged)
        closeEditor()
    }

    override fun showAreYouSureDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.are_you_sure_changes_is_not_saved))
            .setCancelable(true)
            .setPositiveButton(R.string.yes) { dialogInterface, _ ->
                dialogInterface.cancel()
                closeEditor()
            }
            .setNegativeButton(R.string.no) { dialogInterface, _ ->
                dialogInterface.cancel()
            }.create().show()
    }

    override fun closeEditor() {
        router.exit()
    }

    override fun showDateDialog(type: CalendarType) {
        datePickerRange.getDatePickerDialog(type).show()
    }

    override fun showTimeDialog(type: CalendarType) {
        timePickerRange.getTimePickerDialog(type).show()
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

    override fun showWhenCreated() {
        with(binding)
        {
            taskDescriptionEditText.setText(task!!.description)
            if (task!!.description.isNotEmpty()) {
                taskDescriptionEditText.setSelection(task!!.description.lastIndex)
            }
            chooseTaskNameEditText.setText(task!!.name)
        }
        updateDate(CalendarType.START, datePickerRange.startCalendar.getFormattedDate())
        updateDate(CalendarType.FINISH, datePickerRange.finishCalendar.getFormattedDate())
        updateTime(CalendarType.START, datePickerRange.startCalendar.getFormattedTime())
        updateTime(CalendarType.FINISH, datePickerRange.finishCalendar.getFormattedTime())
    }

    override fun onError(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onError(resId: Int) {
        Toast.makeText(requireContext(), getString(resId), Toast.LENGTH_SHORT).show()
    }

    private fun collectUIDataIntoTask(): TaskUI {
        return TaskUI(
            Calendar.Builder().getByTimeAndDateCalendars(
                timePickerRange.startCalendar,
                datePickerRange.startCalendar
            ).timeInMillis,
            Calendar.Builder().getByTimeAndDateCalendars(
                timePickerRange.finishCalendar,
                datePickerRange.finishCalendar
            ).timeInMillis,
            binding.chooseTaskNameEditText.text.toString(),
            binding.taskDescriptionEditText.text.toString(),
            task!!.id
        )
    }

    companion object {
        fun newInstance(taskUI: TaskUI): TaskEditorFragment {
            val bundle = Bundle()
            bundle.putParcelable(GOT_TASK_TAG, taskUI)
            val fragment = TaskEditorFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}