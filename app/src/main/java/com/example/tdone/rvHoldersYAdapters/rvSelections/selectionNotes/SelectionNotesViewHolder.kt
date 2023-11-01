package com.example.tdone.rvHoldersYAdapters.rvSelections.selectionNotes

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.databinding.ItemNotesBinding
import com.example.tdone.dataclasses.NoteDataClass

class SelectionNotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemNotesBinding.bind(view)

    fun bind(data: NoteDataClass) {
        if(data.noteFront !=null){
            if (data.noteFront!!.isReferenceImage){
                binding.ivFrontNote.setImageResource(data.noteFront!!.refImage)
            }else{
                binding.ivFrontNote.setImageURI(data.noteFront!!.uriImage)
            }

        }
        binding.root.setCardBackgroundColor(ContextCompat.getColor(binding.root.context,data.noteBackground))
        binding.selected.visibility =
            if (data.hasVinculation) {
                View.VISIBLE
            } else {
                View.GONE
            }
        binding.tvNoteTittle.text = data.noteTittle
    }
}