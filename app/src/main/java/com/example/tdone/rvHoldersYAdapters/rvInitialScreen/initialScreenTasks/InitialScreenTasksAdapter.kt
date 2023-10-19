package com.example.tdone.rvHoldersYAdapters.rvInitialScreen.initialScreenTasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.R
import com.example.tdone.dataclasses.TaskDataClass
import com.example.tdone.rvHoldersYAdapters.rvInitialScreen.initialScreenNotes.InitialScreenNotesViewHolder

class InitialScreenTasksAdapter(
    var nearToEndTasks: List<TaskDataClass>
): RecyclerView.Adapter<InitialScreenTasksViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InitialScreenTasksViewHolder {
        return InitialScreenTasksViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_task,parent,false)
        )
    }

    override fun getItemCount(): Int =nearToEndTasks.size

    override fun onBindViewHolder(holder: InitialScreenTasksViewHolder, position: Int) {
        holder.bind(nearToEndTasks[position])
    }
}