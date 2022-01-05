package com.example.verygoodtaskplanner.presentation.feature.calendar.adapters

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.verygoodtaskplanner.R
import com.example.verygoodtaskplanner.presentation.entities.TaskUI

class TasksRecyclerAdapter : RecyclerView.Adapter<TasksRecyclerAdapter.TasksViewHolder>() {
    private var items: ArrayList<TaskUI> = arrayListOf()
    var onItemClick: ((TaskUI) -> Unit)? = null

    inner class TasksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskNameTextView = itemView.findViewById<TextView>(R.id.taskNameTextView)
        private val taskDescriptionTextView =
            itemView.findViewById<TextView>(R.id.taskDescriptionTextView)
        private val taskTimeRange = itemView.findViewById<TextView>(R.id.taskTimeRange)
        fun bind(taskUI: TaskUI) {
            itemView.setOnClickListener {
                onItemClick?.invoke(taskUI)
            }
            taskNameTextView.text = taskUI.name
            if (taskUI.description.isBlank()) {
                taskDescriptionTextView.visibility = View.GONE
            } else {
                taskDescriptionTextView.text = taskUI.description
            }
            taskTimeRange.text = taskUI.getFormattedRange()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        return TasksViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_task_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.count()

    class TaskItemDiffCallback(var oldTaskList: List<TaskUI>, var newTaskList: List<TaskUI>) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldTaskList.count()

        override fun getNewListSize(): Int = newTaskList.count()

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldTaskList[oldItemPosition].id == newTaskList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldTaskList[oldItemPosition] == newTaskList[newItemPosition]
        }
    }

    fun fillRecycler(data: ArrayList<TaskUI>) {
        val oldList = items
        val diffResult = DiffUtil.calculateDiff(
            TaskItemDiffCallback(oldList, data)
        )
        items = data
        diffResult.dispatchUpdatesTo(this)
    }
}