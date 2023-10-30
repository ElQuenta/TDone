package com.example.tdone.rvHoldersYAdapters.rvSelections.selectionNotes

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.databinding.ItemNotesBinding
import com.example.tdone.dataclasses.NoteDataClass

class SelectionNotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemNotesBinding.bind(view)

    fun bind(data: NoteDataClass, currentNote: NoteDataClass) {
        if (data.imagen != null) {
            binding.ivFrontNote.setImageResource(data.imagen!!)
        }
        binding.root.setCardBackgroundColor(ContextCompat.getColor(binding.root.context,data.color))
        binding.selected.visibility =
            if (data == currentNote) {
                View.VISIBLE
            } else {
                View.GONE
            }
        binding.tvNoteTittle.text = data.name
    }
}