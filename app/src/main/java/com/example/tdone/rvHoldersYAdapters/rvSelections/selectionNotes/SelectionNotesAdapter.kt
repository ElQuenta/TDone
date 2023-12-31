package com.example.tdone.rvHoldersYAdapters.rvSelections.selectionNotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.R
import com.example.tdone.dataclasses.NoteDataClass

class SelectionNotesAdapter(
    var notes: List<NoteDataClass>,
    val onNoteSelected: (NoteDataClass) -> Unit
) : RecyclerView.Adapter<SelectionNotesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionNotesViewHolder {
        return SelectionNotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_notes, parent, false)
        )
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: SelectionNotesViewHolder, position: Int) {
        holder.bind(notes[position])
        holder.itemView.setOnClickListener {
            onNoteSelected(notes[position])
        }
    }

    fun updateNotes(newNotes:List<NoteDataClass>){
        notes = newNotes
        notifyDataSetChanged()
    }

}