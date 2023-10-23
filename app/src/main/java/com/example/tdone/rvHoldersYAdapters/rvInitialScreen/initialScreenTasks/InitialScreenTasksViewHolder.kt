package com.example.tdone.rvHoldersYAdapters.rvInitialScreen.initialScreenTasks

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.databinding.ItemTaskBinding
import com.example.tdone.dataclasses.TaskDataClass
import com.example.tdone.rvHoldersYAdapters.rvTags.TagsAdapter

class InitialScreenTasksViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemTaskBinding.bind(view)

    fun bind(task: TaskDataClass) {
        binding.tvTaskTittle.text =task.name
        if (task.group != null) {
            binding.tvTaskGroup.text = task.group!!.name
        }
        binding.tvTaskEndDate.text = "12/12/2023"

        if(task.tag_DataClasses.isNotEmpty()){
            val tagsAdapter = TagsAdapter(task.tag_DataClasses)
            binding.rvTaskTags.layoutManager = LinearLayoutManager(binding.root.context)
            binding.rvTaskTags.adapter = tagsAdapter
        }
    }
}