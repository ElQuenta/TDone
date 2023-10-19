package com.example.tdone.rvHoldersYAdapters.rvTags

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.databinding.ItemTagBinding
import com.example.tdone.dataclasses.TagDataClass

class TagsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemTagBinding.bind(view)

    fun bind(tag: TagDataClass) {
        binding.root.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, tag.color))
    }
}