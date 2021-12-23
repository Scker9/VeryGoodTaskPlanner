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
import com.example.verygoodtaskplanner.data.entities.TimeRange
import com.example.verygoodtaskplanner.databinding.CalendarWithTasksBinding
import com.example.verygoodtaskplanner.domain.interactors.HourInteractor
import com.example.verygoodtaskplanner.data.getFormattedDate
import com.example.verygoodtaskplanner.data.getFormattedTime
import com.example.verygoodtaskplanner.presentation.feature.calendar.adapters.HourRecyclerAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.component.inject

class CalendarTasksFragment : BaseFragment<CalendarWithTasksBinding>() {
    private val TAG = this::class.java.simpleName
    val db by inject<TaskDatabase>()
    val interactor by inject<HourInteractor>()
    val adapter by lazy { HourRecyclerAdapter() }

    val fakeDataTasks = arrayListOf(
        Task(
            dateStart = 1640307794000,
            dateFinish = 1640307794000 + 3600_000 * 4,
            name = "Govno ",
            description = "Пися Пися ПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПися \n ПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПисяПися\n ПисяПисяПисяПисяПисяПися ПисяПисяПисяПисяПисяПися"
        ),
        Task(
            dateStart = 1640307794000,
            dateFinish = 1640307794000 + 3600_000 * 4,
            name = "Govno",
            description = "Пися"
        ),
        Task(
            dateStart = 1640307794000,
            dateFinish = 1640307794000 + 3600_000 * 4,
            name = "Govno",
            description = "Пися"
        )
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

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> CalendarWithTasksBinding
        get() = CalendarWithTasksBinding::inflate

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calendar = binding.calendarView
        val recyclerHour = binding.hourTaskRecycler
        recyclerHour.adapter = adapter
        db.taskDao().addTasks(fakeDataTasks).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d(TAG, "adding completed")
            }
        db.taskDao().getAllTasks().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    //       Log.d(TAG, it.toString())
                },
                {
                    Log.d(TAG, it.toString())
                },
            )
        calendar.setOnDayClickListener {
            Log.d(
                TAG,
                "on click " + it.calendar.getFormattedDate() + " " + it.calendar.getFormattedTime()
            )
            interactor.getHoursWithTasks(it.calendar.timeInMillis)
                .subscribe(
                    { list ->
                        adapter.fillRecycler(list)
                        //  Log.d(TAG, "got hours = $list")
                        list.forEach {
                            if (it.tasks.isNotEmpty()) {
                                Log.d(
                                    TAG,
                                    "Got hour with task hour range = ${
                                        it.getFormattedRange(TimeRange.ReturnType.TIME_ONLY)
                                    } task range ${it.tasks[0].getFormattedRange(TimeRange.ReturnType.TIME_ONLY)}"
                                )
                            }
                        }
                    },
                    {
                        Log.d(TAG, it.localizedMessage)
                    }
                )
        }
    }

    companion object {
        fun newInstance(): CalendarTasksFragment = CalendarTasksFragment()
    }
}