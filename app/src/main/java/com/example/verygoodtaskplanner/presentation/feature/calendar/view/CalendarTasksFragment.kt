package com.example.verygoodtaskplanner.presentation.feature.calendar.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.randomdog.presentation.base.BaseFragment
import com.example.verygoodtaskplanner.data.database.TaskDatabase
import com.example.verygoodtaskplanner.data.entities.Hour
import com.example.verygoodtaskplanner.data.entities.Task
import com.example.verygoodtaskplanner.databinding.CalendarWithTasksBinding
import com.example.verygoodtaskplanner.presentation.feature.calendar.adapters.HourRecyclerAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.component.inject

class CalendarTasksFragment : BaseFragment<CalendarWithTasksBinding>() {
    private val TAG = this::class.java.simpleName
    val db by inject<TaskDatabase>()
    val fakeDataTasks = arrayListOf(
        Task(0, 0, "Говно писи", "почесать попы"),
        Task(0, 0, "Говно писи", "почесать попы"),
        Task(0, 0, "Говно писи", "почесать попы"),
        Task(0, 0, "Говно писи", "почесать попы"),
        Task(0, 0, "Говно писи", "почесать попы"),
        Task(0, 0, "Говно писи", "почесать попы"),
        Task(0, 0, "Говно писи", "почесать попы"),
        Task(0, 0, "Говно писи", "почесать попы"),
        Task(0, 0, "Говно писи", "почесать попы")
    )
    val fakeHourData = arrayListOf(
        Hour(0, 1, fakeDataTasks),
        Hour(0, 1, fakeDataTasks),
        Hour(0, 1, fakeDataTasks),
        Hour(0, 1, fakeDataTasks),
        Hour(0, 1, fakeDataTasks),
        Hour(0, 1, fakeDataTasks),
        Hour(0, 1, fakeDataTasks),
        Hour(0, 1, fakeDataTasks),
        Hour(0, 1, fakeDataTasks),
        Hour(0, 1, fakeDataTasks),
        Hour(0, 1, fakeDataTasks),
    )
    val adapter by lazy { HourRecyclerAdapter() }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> CalendarWithTasksBinding
        get() = CalendarWithTasksBinding::inflate

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calendar = binding.calendarView
        val recyclerHour = binding.hourTaskRecycler
        recyclerHour.adapter = adapter
        adapter.fillRecycler(fakeHourData)
        db.taskDao().addTasks(fakeDataTasks).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d(TAG, "adding completed")
            }
        db.taskDao().getAllTasks().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d(TAG, it.toString())
                },
                {
                    Log.d(TAG, it.toString())
                },
            )
        calendar.setOnDayClickListener {
            // it.calendar.add()
        }
    }

    companion object {
        fun newInstance(): CalendarTasksFragment = CalendarTasksFragment()
    }
}