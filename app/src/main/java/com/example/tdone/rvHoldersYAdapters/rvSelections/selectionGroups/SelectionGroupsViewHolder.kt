package com.example.tdone.rvHoldersYAdapters.rvSelections.selectionGroups

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.R
import com.example.tdone.databinding.ItemGroupBinding
import com.example.tdone.dataclasses.GroupDataClass

class SelectionGroupsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemGroupBinding.bind(view)

    fun bind(data: GroupDataClass) {
        binding.tvGroupName.text = data.groupName
        binding.root.setCardBackgroundColor(
            ContextCompat.getColor(
                binding.root.context,
                if (data.groupSelected) {
                    R.color.white
                } else {
                    R.color.colorGris
                }
            )
        )
    }
}