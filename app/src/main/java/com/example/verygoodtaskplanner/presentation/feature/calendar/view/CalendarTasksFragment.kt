package com.example.verygoodtaskplanner.presentation.feature.calendar.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.randomdog.presentation.base.BaseFragment
import com.example.verygoodtaskplanner.databinding.CalendarWithTasksBinding

class CalendarTasksFragment : BaseFragment<CalendarWithTasksBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> CalendarWithTasksBinding
        get() = CalendarWithTasksBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calendar = binding.calendarView
        calendar.setOnDayClickListener {
           // it.calendar.add()
        }
    }

    companion object {
        fun newInstance(): CalendarTasksFragment = CalendarTasksFragment()
    }
}