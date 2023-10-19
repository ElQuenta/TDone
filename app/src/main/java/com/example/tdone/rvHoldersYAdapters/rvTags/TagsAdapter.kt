package com.example.tdone.rvHoldersYAdapters.rvTags

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.R
import com.example.tdone.dataclasses.TagDataClass
import com.example.tdone.rvHoldersYAdapters.rvInitialScreen.initialScreenTasks.InitialScreenTasksViewHolder

class TagsAdapter(
    var tags:List<TagDataClass>
): RecyclerView.Adapter<TagsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsViewHolder {
        return TagsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_tag,parent,false)
        )
    }

    override fun getItemCount(): Int = tags.size

    override fun onBindViewHolder(holder: TagsViewHolder, position: Int) {
        holder.bind(tags[position])
    }
}