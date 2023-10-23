package com.example.tdone.rvHoldersYAdapters.rvInitialScreen.initialScreenNotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.R
import com.example.tdone.dataclasses.NoteDataClass

class InitialScreenNotesAdapter(
    var currentNotes: List<NoteDataClass>
) : RecyclerView.Adapter<InitialScreenNotesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InitialScreenNotesViewHolder {
        return InitialScreenNotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_notes,parent,false)
        )
    }


    override fun getItemCount(): Int =currentNotes.size

    override fun onBindViewHolder(holder: InitialScreenNotesViewHolder, position: Int) {
        holder.bind(currentNotes[position])
    }
}
