package com.example.verygoodtaskplanner.presentation.feature.calendar.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.randomdog.presentation.base.BaseFragment
import com.example.verygoodtaskplanner.data.database.TaskDatabase
import com.example.verygoodtaskplanner.data.entities.Hour
import com.example.verygoodtaskplanner.data.getFormattedDate
import com.example.verygoodtaskplanner.data.getFormattedTime
import com.example.verygoodtaskplanner.databinding.CalendarWithTasksBinding
import com.example.verygoodtaskplanner.presentation.feature.calendar.adapters.HourRecyclerAdapter
import com.example.verygoodtaskplanner.presentation.feature.calendar.dialog.CreateTaskDialogFragment
import com.example.verygoodtaskplanner.presentation.feature.calendar.presenter.CalendarTaskPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.presenter.InjectPresenter
import org.koin.core.component.inject

class CalendarTasksFragment : BaseFragment<CalendarWithTasksBinding>(), CalendarTasksView {
    private val TAG = this::class.java.simpleName
    private val adapter by lazy { HourRecyclerAdapter() }
    private val db by inject<TaskDatabase>()
    @InjectPresenter
    lateinit var presenter: CalendarTaskPresenter
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> CalendarWithTasksBinding
        get() = CalendarWithTasksBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calendar = binding.calendarView
        val recyclerHour = binding.hourTaskRecycler
        binding.addTaskButton.setOnClickListener {
            CreateTaskDialogFragment().show(childFragmentManager, DIALOG_TAG)
        }
        binding.clearTasks.setOnClickListener {
            db.taskDao().clearAllTasks().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
        }
        recyclerHour.adapter = adapter
        calendar.setOnDayClickListener {
            Log.d(
                TAG,
                "on click " + it.calendar.getFormattedDate() + " " + it.calendar.getFormattedTime()
            )
            presenter.getTasksByDay(it.calendar.timeInMillis)
        }
    }

    companion object {
        const val DIALOG_TAG = "CREATOR"
        fun newInstance(): CalendarTasksFragment = CalendarTasksFragment()
    }

    override fun displayDailyTasks(tasks: ArrayList<Hour>) {
        adapter.fillRecycler(tasks)
    }
}