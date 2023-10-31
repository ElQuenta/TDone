package com.example.tdone.rvHoldersYAdapters.rvSelections.selectionGroups

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.R
import com.example.tdone.dataclasses.GroupDataClass

class SelectionGroupsAdapter(
    var groups: List<GroupDataClass>,
    val onGroupSelected: (GroupDataClass) -> Unit
) : RecyclerView.Adapter<SelectionGroupsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionGroupsViewHolder {
        return SelectionGroupsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)
        )
    }

    override fun getItemCount(): Int = groups.size

    override fun onBindViewHolder(holder: SelectionGroupsViewHolder, position: Int) {
        holder.bind(groups[position])
        holder.itemView.setOnClickListener {
            onGroupSelected(groups[position])
        }
    }

    fun updateGroups(newGroups:List<GroupDataClass>){
        groups=newGroups
        notifyDataSetChanged()
    }
}