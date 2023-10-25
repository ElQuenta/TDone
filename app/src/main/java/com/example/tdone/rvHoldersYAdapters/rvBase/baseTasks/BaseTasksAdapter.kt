package com.example.tdone.rvHoldersYAdapters.rvBase.baseTasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.R
import com.example.tdone.dataclasses.TaskDataClass

class BaseTasksAdapter(
    private var tasks: List<TaskDataClass>,
    var nav: (TaskDataClass) -> Unit
): RecyclerView.Adapter<BaseTasksViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseTasksViewHolder {
        return BaseTasksViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_task,parent,false)
        )
    }

    override fun getItemCount(): Int =tasks.size

    override fun onBindViewHolder(holder: BaseTasksViewHolder, position: Int) {
        holder.bind(tasks[position],nav)
    }
}