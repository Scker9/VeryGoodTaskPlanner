package com.example.verygoodtaskplanner.presentation.feature.calendar.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.randomdog.presentation.base.BaseFragment
import com.example.verygoodtaskplanner.Screens
import com.example.verygoodtaskplanner.data.entities.Hour
import com.example.verygoodtaskplanner.data.entities.Task
import com.example.verygoodtaskplanner.databinding.CalendarWithTasksBinding
import com.example.verygoodtaskplanner.presentation.feature.calendar.adapters.HourRecyclerAdapter
import com.example.verygoodtaskplanner.presentation.feature.calendar.dialog.CreateTaskDialogFragment
import com.example.verygoodtaskplanner.presentation.feature.calendar.presenter.CalendarTaskPresenter
import com.github.terrakok.cicerone.Router
import moxy.presenter.InjectPresenter
import org.koin.core.component.inject

class CalendarTasksFragment : BaseFragment<CalendarWithTasksBinding>(), CalendarTasksView {
    private val TAG = this::class.java.simpleName
    private val adapter by lazy { HourRecyclerAdapter() }
    private val router by inject<Router>()

    @InjectPresenter
    lateinit var presenter: CalendarTaskPresenter
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> CalendarWithTasksBinding
        get() = CalendarWithTasksBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calendar = binding.calendarView
        val recyclerHour = binding.hourTaskRecycler
        recyclerHour.setOnClickListener {
            Log.d(TAG, "214rf[safoiaskjfasfnafsn")
        }
        adapter.onTaskClicked =
            {
                navigateToEditor(it)
            }
        binding.addTaskButton.setOnClickListener {
            createDialog().show(childFragmentManager, DIALOG_TAG)
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

    override fun displayDailyTasks(tasks: ArrayList<Hour>) {
        adapter.fillRecycler(tasks)
    }

    private fun navigateToEditor(task: Task) {
        Log.d(TAG, task.toString())
        router.navigateTo(Screens.TASK_EDITOR(task))
    }

    companion object {
        const val DIALOG_TAG = "CREATOR"
        fun newInstance(): CalendarTasksFragment = CalendarTasksFragment()
    }
}