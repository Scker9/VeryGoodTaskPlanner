package com.example.verygoodtaskplanner

import com.example.verygoodtaskplanner.presentation.feature.calendar.view.CalendarTasksFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    val CALENDAR_WITH_TASKS_SCREEN = FragmentScreen { CalendarTasksFragment.newInstance()}
}