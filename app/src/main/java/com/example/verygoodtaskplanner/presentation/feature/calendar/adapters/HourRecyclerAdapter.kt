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

//TODO() добавить диффутилс
class HourRecyclerAdapter : RecyclerView.Adapter<HourRecyclerAdapter.HourViewHolder>() {
    private val TAG = this::class.java.simpleName
    private var items: ArrayList<Hour> = arrayListOf()
    var onTaskClicked: ((Task) -> Unit)? = null

    inner class HourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val hourRangeTextView = itemView.findViewById<TextView>(R.id.timeTextView)
        private val taskRecycler = itemView.findViewById<RecyclerView>(R.id.taskRecycler)
        fun bind(hour: Hour) {
            val adapter = TasksRecyclerAdapter()
            adapter.setHasStableIds(true)
            adapter.onItemClick =
                {
                    onTaskClicked?.invoke(it)
                }
            hourRangeTextView.text = hour.getFormattedRange(TimeRange.ReturnType.TIME_ONLY)
            taskRecycler.adapter = adapter
            adapter.fillRecycler(hour.tasks)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder =
        HourViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_hour_item, parent, false)
        )

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.count()

    @SuppressLint("NotifyDataSetChanged")
    fun fillRecycler(data: ArrayList<Hour>) {
        items = data
        notifyDataSetChanged() //поправить под диф утилс
    }
}