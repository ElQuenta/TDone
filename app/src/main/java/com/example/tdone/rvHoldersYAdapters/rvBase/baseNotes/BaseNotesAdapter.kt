package com.example.tdone.rvHoldersYAdapters.rvBase.baseNotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.R
import com.example.tdone.dataclasses.NoteDataClass

class BaseNotesAdapter(
    private var notes: List<NoteDataClass>
) : RecyclerView.Adapter<BaseNotesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseNotesViewHolder {
        return BaseNotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_notes,parent,false)
        )
    }


    override fun getItemCount(): Int =notes.size

    override fun onBindViewHolder(holder: BaseNotesViewHolder, position: Int) {
        holder.bind(notes[position])
    }
}
