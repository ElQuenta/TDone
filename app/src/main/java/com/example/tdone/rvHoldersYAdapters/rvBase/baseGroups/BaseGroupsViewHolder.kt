package com.example.tdone.rvHoldersYAdapters.rvBase.baseGroups

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.databinding.ItemGroupBinding
import com.example.tdone.dataclasses.GroupDataClass

class BaseGroupsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemGroupBinding.bind(view)

    fun bind(group: GroupDataClass, nav: (GroupDataClass) -> Unit) {
        binding.tvGroupName.text = group.groupName
        binding.root.setOnClickListener { nav(group) }
    }
}