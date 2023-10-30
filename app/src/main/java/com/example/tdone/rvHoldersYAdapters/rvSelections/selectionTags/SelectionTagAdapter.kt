package com.example.tdone.rvHoldersYAdapters.rvSelections.selectionTags

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.R
import com.example.tdone.dataclasses.TagDataClass

class SelectionTagAdapter(
    val tags: MutableList<TagDataClass>,
    var change: (TagDataClass,Boolean) -> Unit
) : RecyclerView.Adapter<SelectionTagViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionTagViewHolder {
        return SelectionTagViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_difficult, parent, false)
        )
    }

    override fun getItemCount(): Int = tags.size

    override fun onBindViewHolder(holder: SelectionTagViewHolder, position: Int) {
        holder.bind(tags[position],change)
    }
}