package com.example.tdone.rvHoldersYAdapters.rvSelections.selectionFront

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.R
import com.example.tdone.databinding.ItemFrontBinding

class selectionFrontViewHolder(view: View, var size: Int) : RecyclerView.ViewHolder(view) {
    private val binding = ItemFrontBinding.bind(view)

    fun bind(image: Int, currentImage: Int) {
        binding.root.setCardBackgroundColor(
            ContextCompat.getColor(
                binding.root.context,
                if (image == currentImage) {
                    R.color.purple_700
                } else {
                    R.color.black
                }
            )
        )
        binding.ivFrontNote.setImageResource(image)
        //binding.root.layoutParams.width = (size * 46) * 100
    }
}