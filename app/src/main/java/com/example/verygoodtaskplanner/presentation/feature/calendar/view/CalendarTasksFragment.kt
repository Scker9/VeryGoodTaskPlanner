package com.example.verygoodtaskplanner.presentation.feature.calendar.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.verygoodtaskplanner.presentation.base.BaseFragment
import com.example.verygoodtaskplanner.R
import com.example.verygoodtaskplanner.Screens
import com.example.verygoodtaskplanner.databinding.CalendarWithTasksBinding
import com.example.verygoodtaskplanner.presentation.Tags.task_creator_dialog
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
        router.setResultListener(getString(R.string.saving_result_key))
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
            createDialog().show(childFragmentManager, task_creator_dialog)
        }
        recyclerHour.adapter = adapter
        calendar.setOnDayClickListener {
            presenter.getTasksByDay(it.calendar.timeInMillis)
        }
    }

    private fun createDialog(): CreateTaskDialogFragment {
        val dialog = CreateTaskDialogFragment()
        dialog.isCancelable = false
        dialog.onTaskCreated =
            {
                presenter.getTasksByDay(binding.calendarView.selectedDates[0].timeInMillis)
            }
        return dialog
    }

    override fun displayDailyTasks(tasks: List<HourUI>) {
        adapter.fillRecycler(tasks)
    }

    private fun navigateToEditor(taskUI: TaskUI) {
        router.navigateTo(Screens.TASK_EDITOR(taskUI))
    }

    companion object {
        fun newInstance(): CalendarTasksFragment = CalendarTasksFragment()
    }
}