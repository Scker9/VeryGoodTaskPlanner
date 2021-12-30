package com.example.verygoodtaskplanner.presentation.feature.editor.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import com.example.randomdog.presentation.base.BaseFragment
import com.example.verygoodtaskplanner.R
import com.example.verygoodtaskplanner.data.entities.Task
import com.example.verygoodtaskplanner.data.entities.TimeRange
import com.example.verygoodtaskplanner.databinding.TaskEditorBinding

class TaskEditorFragment : BaseFragment<TaskEditorBinding>(), TaskEditorView {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> TaskEditorBinding
        get() = TaskEditorBinding::inflate
    var task: Task? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayTaskProperties()
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_delete_task -> {
                    Toast.makeText(requireContext(), "delete clicked", Toast.LENGTH_SHORT).show()
                }
            }
            return@setOnMenuItemClickListener true
        }
    }


    override fun displayTaskProperties() {
        binding.chooseStartTimeButton.text = task?.getFormattedRange(TimeRange.ReturnType.TIME_ONLY)
        binding.chooseStartDateButton.text = task?.getFormattedRange(TimeRange.ReturnType.DATE_ONLY)
        binding.chooseFinishTimeButton.text =
            task?.getFormattedRange(TimeRange.ReturnType.TIME_ONLY)
        binding.chooseFinishDateButton.text =
            task?.getFormattedRange(TimeRange.ReturnType.DATE_ONLY)
        binding.chooseTaskNameEditText.setText(task?.name)
        binding.taskDescriptionEditText.setText(task?.description)
    }

    companion object {
        fun newInstance(task: Task): TaskEditorFragment {
            val fragment = TaskEditorFragment()
            fragment.task = task
            return fragment
        }
    }
}