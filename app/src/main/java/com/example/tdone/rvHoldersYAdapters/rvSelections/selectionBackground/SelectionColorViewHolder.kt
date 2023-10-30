package com.example.tdone.rvHoldersYAdapters.rvSelections.selectionBackground

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.R
import com.example.tdone.databinding.ItemColorBinding

class SelectionColorViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemColorBinding.bind(view)

    fun bind(color: Int, currentColor: Int) {
        binding.root.setCardBackgroundColor(
            ContextCompat.getColor(
                binding.root.context,
                if (color == currentColor) {
                    R.color.purple_200
                } else {
                    R.color.black
                }
            )
        )
        binding.color.setCardBackgroundColor(ContextCompat.getColor(binding.color.context, color))
    }

}