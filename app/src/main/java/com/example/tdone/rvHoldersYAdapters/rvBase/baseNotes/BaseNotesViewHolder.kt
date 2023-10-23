package com.example.tdone.rvHoldersYAdapters.rvBase.baseNotes

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.databinding.ItemNotesBinding
import com.example.tdone.dataclasses.NoteDataClass


class BaseNotesViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemNotesBinding.bind(view)
    fun bind(note: NoteDataClass) {
        binding.cvNote.setCardBackgroundColor(ContextCompat.getColor(binding.cvNote.context,note.color))
        binding.tvTittleNote.text = note.name
    }

}