package com.example.tdone.rvHoldersYAdapters.rvBase.baseGroups

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.R
import com.example.tdone.dataclasses.GroupDataClass

class BaseGroupsAdapter(
    private var groups: List<GroupDataClass>,
    private var nav: (GroupDataClass) -> Unit
) : RecyclerView.Adapter<BaseGroupsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseGroupsViewHolder {
        return BaseGroupsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)
        )
    }

    override fun getItemCount(): Int = groups.size

    override fun onBindViewHolder(holder: BaseGroupsViewHolder, position: Int) {
        holder.bind(groups[position],nav)
    }
}