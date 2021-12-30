package com.example.verygoodtaskplanner

import com.example.verygoodtaskplanner.data.entities.Task
import com.example.verygoodtaskplanner.presentation.feature.calendar.view.CalendarTasksFragment
import com.example.verygoodtaskplanner.presentation.feature.editor.view.TaskEditorFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    val CALENDAR_WITH_TASKS_SCREEN = FragmentScreen { CalendarTasksFragment.newInstance() }
    fun TASK_EDITOR(task: Task) = FragmentScreen { TaskEditorFragment.newInstance(task) }
}