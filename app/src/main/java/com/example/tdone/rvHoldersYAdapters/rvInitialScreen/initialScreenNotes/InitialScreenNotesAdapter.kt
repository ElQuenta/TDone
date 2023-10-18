package com.example.tdone.rvHoldersYAdapters.rvInitialScreen.initialScreenNotes

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.dataclasses.NoteDataClass

class InitialScreenNotesAdapter(
    var currentNotes: List<NoteDataClass>
) : RecyclerView.Adapter<InitialScreenNotesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InitialScreenNotesViewHolder {
        TODO("Not yet implemented")
    }


    override fun getItemCount(): Int =currentNotes.size

    override fun onBindViewHolder(holder: InitialScreenNotesViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}
