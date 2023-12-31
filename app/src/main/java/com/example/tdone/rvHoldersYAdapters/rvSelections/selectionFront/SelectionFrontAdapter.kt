package com.example.tdone.rvHoldersYAdapters.rvSelections.selectionFront

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.R
import com.example.tdone.dataclasses.ImageDataClass

class SelectionFrontAdapter(
    var images: List<ImageDataClass>,
    val function: (ImageDataClass) -> Unit
) : RecyclerView.Adapter<SelectionFrontViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionFrontViewHolder {
        return SelectionFrontViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_front, parent, false)
        )
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: SelectionFrontViewHolder, position: Int) {
        holder.bind(images[position])
        holder.itemView.setOnClickListener {
            function(images[position])
        }
    }

    fun updateImages(newImages:List<ImageDataClass>){
        images = newImages
        notifyDataSetChanged()
    }

}