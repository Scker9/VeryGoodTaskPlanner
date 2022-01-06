package com.example.verygoodtaskplanner.presentation.feature.calendar.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.verygoodtaskplanner.presentation.base.BaseFragment
import com.example.verygoodtaskplanner.Screens
import com.example.verygoodtaskplanner.databinding.CalendarWithTasksBinding
import com.example.verygoodtaskplanner.presentation.utils.Tags
import com.example.verygoodtaskplanner.presentation.utils.Tags.TASK_CREATOR_DIALOG
import com.example.verygoodtaskplanner.presentation.entities.HourUI
import com.example.verygoodtaskplanner.presentation.entities.TaskUI
import com.example.verygoodtaskplanner.presentation.feature.calendar.adapters.HourRecyclerAdapter
import com.example.verygoodtaskplanner.presentation.feature.calendar.dialog.view.CreateTaskDialogFragment
import com.example.verygoodtaskplanner.presentation.feature.calendar.presenter.CalendarTaskPresenter
import com.github.terrakok.cicerone.Router
import moxy.presenter.InjectPresenter
import org.koin.core.component.inject

class CalendarTasksFragment : BaseFragment<CalendarWithTasksBinding>(), CalendarTasksView {
    private val adapter by lazy { HourRecyclerAdapter() }
    private val router by inject<Router>()

    @InjectPresenter
    lateinit var presenter: CalendarTaskPresenter
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> CalendarWithTasksBinding
        get() = CalendarWithTasksBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        router.setResultListener(Tags.EDITOR_SAVING_RESULT_KEY)
        {
            if (it as Boolean) {
                presenter.getTasksByDay(binding.calendarView.selectedDates[0].timeInMillis)
            }
        }
        val calendar = binding.calendarView
        val recyclerHour = binding.hourTaskRecycler
        adapter.onTaskClicked =
            {
                navigateToEditor(it)
            }
        binding.addTaskButton.setOnClickListener {
            showCreatorDialog()
        }
        recyclerHour.adapter = adapter
        calendar.setOnDayClickListener {
            presenter.getTasksByDay(it.calendar.timeInMillis)
        }
    }

    override fun displayDailyTasks(tasks: List<HourUI>) {
        adapter.fillRecycler(tasks)
    }

    override fun showCreatorDialog() {
        val dialog = CreateTaskDialogFragment()
        dialog.isCancelable = false
        dialog.onTaskCreated =
            {
                presenter.getTasksByDay(binding.calendarView.selectedDates.first().timeInMillis)
            }
        dialog.show(childFragmentManager, TASK_CREATOR_DIALOG)
    }

    override fun onError(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToEditor(taskUI: TaskUI) {
        router.navigateTo(Screens.getTaskEditorScreen(taskUI))
    }

    companion object {
        fun newInstance(): CalendarTasksFragment = CalendarTasksFragment()
    }
}