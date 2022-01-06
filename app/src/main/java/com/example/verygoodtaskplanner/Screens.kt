package com.example.verygoodtaskplanner

import com.example.verygoodtaskplanner.presentation.entities.TaskUI
import com.example.verygoodtaskplanner.presentation.feature.calendar.view.CalendarTasksFragment
import com.example.verygoodtaskplanner.presentation.feature.editor.view.TaskEditorFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun getCalendarWithTasksScreen() = FragmentScreen { CalendarTasksFragment.newInstance() }
    fun getTaskEditorScreen(taskUI: TaskUI) =
        FragmentScreen { TaskEditorFragment.newInstance(taskUI) }
}