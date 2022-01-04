package com.example.verygoodtaskplanner.presentation.feature.calendar.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.verygoodtaskplanner.R
import com.example.verygoodtaskplanner.data.entities.Hour
import com.example.verygoodtaskplanner.data.entities.Task
import com.example.verygoodtaskplanner.data.entities.TimeRange
import com.example.verygoodtaskplanner.presentation.entities.HourUI
import com.example.verygoodtaskplanner.presentation.entities.TaskUI

//TODO() добавить диффутилс
class HourRecyclerAdapter : RecyclerView.Adapter<HourRecyclerAdapter.HourViewHolder>() {
    private val TAG = this::class.java.simpleName
    private var items: List<HourUI> = listOf()
    var onTaskClicked: ((TaskUI) -> Unit)? = null

    inner class HourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val hourRangeTextView = itemView.findViewById<TextView>(R.id.timeTextView)
        private val taskRecycler = itemView.findViewById<RecyclerView>(R.id.taskRecycler)
        fun bind(hourUI: HourUI) {
            val adapter = TasksRecyclerAdapter()
            adapter.setHasStableIds(true)
            adapter.onItemClick =
                {
                    onTaskClicked?.invoke(it)
                }
            hourRangeTextView.text = hourUI.getFormattedRange(TimeRange.ReturnType.TIME_ONLY)
            taskRecycler.adapter = adapter
            adapter.fillRecycler(hourUI.tasks)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        return HourViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_hour_item, parent, false)
        )
    }


    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.count()

    @SuppressLint("NotifyDataSetChanged")
    fun fillRecycler(data: List<HourUI>) {
        items = data
        notifyDataSetChanged() //поправить под диф утилс
    }
}