package com.example.tdone.rvHoldersYAdapters.rvSelections.selectionTags

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.R
import com.example.tdone.databinding.ItemDifficultBinding
import com.example.tdone.dataclasses.TagDataClass

class SelectionTagViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemDifficultBinding.bind(view)

    fun bind(tag: TagDataClass, funtion: (TagDataClass, Boolean) -> Unit) {
        binding.cvTag.setCardBackgroundColor(
            ContextCompat.getColor(
                binding.cvTag.context,
                tag.color
            )
        )
        binding.tvTagName.text = tag.name
        binding.cbSelectTag.setOnCheckedChangeListener { _, isChecked ->
            funtion(tag, isChecked)
        }

        if (tag.name == "Nueva Tag") {
            binding.tvTagName.setTextColor(
                ContextCompat.getColor(
                    binding.tvTagName.context,
                    R.color.black
                )
            )
            binding.fabEdit.visibility = View.INVISIBLE
            binding.fabEdit.isClickable = false
            binding.cbSelectTag.visibility = View.INVISIBLE
            binding.cbSelectTag.isClickable = false
        }

    }
}