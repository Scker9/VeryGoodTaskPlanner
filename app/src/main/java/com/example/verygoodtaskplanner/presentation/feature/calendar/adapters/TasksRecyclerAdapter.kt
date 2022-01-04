package com.example.verygoodtaskplanner.presentation.feature.calendar.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.verygoodtaskplanner.R
import com.example.verygoodtaskplanner.data.entities.Task
import com.example.verygoodtaskplanner.data.entities.TimeRange
import com.example.verygoodtaskplanner.presentation.entities.TaskUI

//TODO() добавить диффутилс
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
            taskDescriptionTextView.text = taskUI.description
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

    @SuppressLint("NotifyDataSetChanged")
    fun fillRecycler(data: ArrayList<TaskUI>) {
        items = data
        notifyDataSetChanged() // поправить под диф утилы
    }
}