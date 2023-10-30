package com.example.tdone.rvHoldersYAdapters.rvSelections.selectionBackground

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.R

class SelectionColorAdapter(
    val colors: List<Int>,
    var currentColor: Int,
    val onColorSelected: (Int) -> Unit
) : RecyclerView.Adapter<SelectionColorViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionColorViewHolder {
        return SelectionColorViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false)
        )
    }

    override fun getItemCount(): Int =colors.size

    override fun onBindViewHolder(holder: SelectionColorViewHolder, position: Int) {
        holder.bind(colors[position],currentColor)
        holder.view.setOnClickListener {
            onColorSelected(colors[position])
        }
    }
}