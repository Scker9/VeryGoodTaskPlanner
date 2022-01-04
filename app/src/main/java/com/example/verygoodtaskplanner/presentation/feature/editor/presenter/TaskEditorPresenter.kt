package com.example.verygoodtaskplanner.presentation.feature.editor.presenter

import com.example.randomdog.presentation.base.BasePresenter
import com.example.verygoodtaskplanner.data.entities.Task
import com.example.verygoodtaskplanner.domain.interactors.DailyTasksInteractor
import com.example.verygoodtaskplanner.domain.mappers.TaskToTaskUIMapper
import com.example.verygoodtaskplanner.presentation.entities.TaskUI
import com.example.verygoodtaskplanner.presentation.feature.editor.view.TaskEditorView
import org.koin.core.component.inject

class TaskEditorPresenter : BasePresenter<TaskEditorView>() {
    private val dailyTasksInteractor by inject<DailyTasksInteractor>()
    fun deleteTask(id: Long) {
        dailyTasksInteractor.deleteTaskById(id).subscribe(
            {
                viewState.onSuccess(TASK_DELETED, true)
            },
            {
                viewState.onError(it.localizedMessage)
            }
        ).addToCompositeDisposable()
    }

    fun saveChanges(oldTask: TaskUI, newTask: TaskUI) {
        if (oldTask != newTask) {
            dailyTasksInteractor.updateTask(TaskToTaskUIMapper().convertTaskUIToTask(newTask))
                .subscribe(
                    {
                        viewState.onSuccess(TASK_SAVED, true)
                    },
                    {
                        viewState.onError(it.localizedMessage)
                    }
                ).addToCompositeDisposable()
        } else {
            viewState.onSuccess(TASK_SAVED, false)
        }
    }

    fun displayTaskProperties() {
        viewState.showTaskProperties()
    }

    companion object {
        const val TASK_SAVED = "Изменения сохранены!"
        const val TASK_DELETED = "Задача удалена!"
    }
}