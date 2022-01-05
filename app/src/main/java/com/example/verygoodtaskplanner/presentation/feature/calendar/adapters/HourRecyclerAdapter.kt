package com.example.verygoodtaskplanner.presentation.feature.calendar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.verygoodtaskplanner.R
import com.example.verygoodtaskplanner.data.entities.TimeRange
import com.example.verygoodtaskplanner.presentation.entities.HourUI
import com.example.verygoodtaskplanner.presentation.entities.TaskUI

//TODO() добавить диффутилс
class HourRecyclerAdapter : RecyclerView.Adapter<HourRecyclerAdapter.HourViewHolder>() {
    private var items: List<HourUI> = listOf()
    var onTaskClicked: ((TaskUI) -> Unit)? = null

    inner class HourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val hourRangeTextView = itemView.findViewById<TextView>(R.id.timeTextView)
        private val taskRecycler = itemView.findViewById<RecyclerView>(R.id.taskRecycler)
        fun bind(hourUI: HourUI) {
            val adapter = TasksRecyclerAdapter()
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

    class HourItemDiffCallback(
        private var oldHourList: List<HourUI>,
        private var newHourList: List<HourUI>
    ) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldHourList.count()

        override fun getNewListSize(): Int = newHourList.count()

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldHourList[oldItemPosition].dateStart == newHourList[newItemPosition].dateStart
                    &&
                    oldHourList[oldItemPosition].dateFinish == newHourList[newItemPosition].dateFinish)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldHourList[oldItemPosition] == newHourList[newItemPosition]
        }
    }

    fun fillRecycler(data: List<HourUI>) {
        val oldList = items
        val diffResult = DiffUtil.calculateDiff(
            HourItemDiffCallback(oldList, data)
        )
        items = data
        diffResult.dispatchUpdatesTo(this)
    }
}