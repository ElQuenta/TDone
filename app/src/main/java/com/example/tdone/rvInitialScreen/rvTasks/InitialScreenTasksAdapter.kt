package com.example.tdone.rvInitialScreen.rvTasks

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.dataclasses.TaskDataClass

class InitialScreenTasksAdapter(
    var nearToEndTasks: List<TaskDataClass>
): RecyclerView.Adapter<InitialScreenTasksViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InitialScreenTasksViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int =nearToEndTasks.size

    override fun onBindViewHolder(holder: InitialScreenTasksViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}