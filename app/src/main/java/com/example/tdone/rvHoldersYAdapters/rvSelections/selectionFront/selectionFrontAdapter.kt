package com.example.tdone.rvHoldersYAdapters.rvSelections.selectionFront

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.R

class selectionFrontAdapter(
    val images: List<Int>,
    var currentImage: Int,
    var size : Int,
    val function: (Int) -> Unit
) : RecyclerView.Adapter<selectionFrontViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): selectionFrontViewHolder {
        return selectionFrontViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_front, parent, false),
            size
        )
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: selectionFrontViewHolder, position: Int) {
        holder.bind(images[position],currentImage)
        holder.itemView.setOnClickListener {
            function(images[position])
        }
    }

}