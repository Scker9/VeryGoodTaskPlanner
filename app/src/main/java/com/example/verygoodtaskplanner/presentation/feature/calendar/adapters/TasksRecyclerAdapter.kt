package com.example.verygoodtaskplanner.presentation.feature.calendar.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.verygoodtaskplanner.R
import com.example.verygoodtaskplanner.data.entities.Task

class TasksRecyclerAdapter : RecyclerView.Adapter<TasksRecyclerAdapter.TasksViewHolder>() {
    private var items: ArrayList<Task> = arrayListOf()

    inner class TasksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskNameTextView = itemView.findViewById<TextView>(R.id.taskNameTextView)
        val taskDescriptionTextView = itemView.findViewById<TextView>(R.id.taskDescriptionTextView)
        fun bind(task: Task) {
            taskNameTextView.text = task.name
            taskDescriptionTextView.text = task.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder =
        TasksViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_task_item, parent, false)
        )

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.count()

    @SuppressLint("NotifyDataSetChanged")
    fun fillRecycler(data: ArrayList<Task>) {
        items = data
        notifyDataSetChanged() // поправить под диф утилы
    }
}