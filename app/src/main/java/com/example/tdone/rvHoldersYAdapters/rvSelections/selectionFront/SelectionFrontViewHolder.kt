package com.example.tdone.rvHoldersYAdapters.rvSelections.selectionFront

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.R
import com.example.tdone.databinding.ItemFrontBinding
import com.example.tdone.dataclasses.ImageDataClass

class SelectionFrontViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemFrontBinding.bind(view)

    fun bind(image: ImageDataClass) {
        binding.root.setCardBackgroundColor(
            ContextCompat.getColor(
                binding.root.context,
                if (image.selected) {
                    R.color.purple_700
                } else {
                    R.color.black
                }
            )
        )
        //binding.root.layoutParams.width = (size * 46) * 100
        if (image.isReferenceImage) {
            binding.ivFrontNote.setImageResource(image.refImage)
        } else {
            binding.ivFrontNote.setImageURI(image.uriImage)
        }
    }
}