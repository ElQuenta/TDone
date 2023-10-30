package com.example.tdone.rvHoldersYAdapters.rvSelections.selectionTasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.R
import com.example.tdone.dataclasses.TaskDataClass

class SelectionTasksAdapter(val tasks: List<TaskDataClass>,
                            var currentTask: TaskDataClass,
                            val onSelectedTask: (TaskDataClass)-> Unit) :
    RecyclerView.Adapter<SelectionTasksViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionTasksViewHolder {
        return SelectionTasksViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        )
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: SelectionTasksViewHolder, position: Int) {
        holder.bind(tasks[position], currentTask)
        holder.itemView.setOnClickListener{
            onSelectedTask(tasks[position])
        }
    }
}