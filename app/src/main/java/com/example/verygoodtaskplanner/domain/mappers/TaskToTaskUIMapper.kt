package com.example.verygoodtaskplanner.domain.mappers

import com.example.verygoodtaskplanner.data.entities.Task
import com.example.verygoodtaskplanner.presentation.entities.TaskUI

class TaskToTaskUIMapper {
    fun convertTaskToTaskUI(task: Task): TaskUI =
        TaskUI(task.dateStart, task.dateFinish, task.name, task.description, task.id!!)

    fun convertTaskToTaskUI(tasks: List<Task>): ArrayList<TaskUI> {
        val temp = arrayListOf<TaskUI>()
        temp.addAll(tasks.map {
            convertTaskToTaskUI(it)
        })
        return temp
    }

    fun convertTaskUIToTask(taskUI: TaskUI): Task =
        Task(taskUI.dateStart, taskUI.dateFinish, taskUI.name, taskUI.description, taskUI.id)
}