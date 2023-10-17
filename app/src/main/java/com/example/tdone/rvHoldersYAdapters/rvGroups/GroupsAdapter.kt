package com.example.tdone.rvHoldersYAdapters.rvGroups

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.dataclasses.GroupDataClass

class GroupsAdapter(
    var allGroups: List<GroupDataClass>
): RecyclerView.Adapter<GroupsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = allGroups.size

    override fun onBindViewHolder(holder: GroupsViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}