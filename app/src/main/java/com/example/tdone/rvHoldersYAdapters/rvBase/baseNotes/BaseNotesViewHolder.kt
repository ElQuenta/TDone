package com.example.tdone.rvHoldersYAdapters.rvBase.baseNotes

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.databinding.ItemNotesBinding
import com.example.tdone.dataclasses.NoteDataClass


class BaseNotesViewHolder(
    private val view: View,
    private val screenWidth: Int
) : RecyclerView.ViewHolder(view) {

    private val binding = ItemNotesBinding.bind(view)
    fun bind(note: NoteDataClass, nav: (NoteDataClass) -> Unit) {
        binding.root.setCardBackgroundColor(
            ContextCompat.getColor(
                binding.root.context,
                note.noteBackground
            )
        )
        //Log.i("Miguel","Tamaño antiguo: ${binding.root.layoutParams.width}")
        binding.root.layoutParams.width=(screenWidth*40)/100
        //Log.i("Miguel","Tamaño Nuevo: ${binding.root.layoutParams.width}")
        binding.tvNoteTittle.text = note.noteTittle
        binding.root.setOnClickListener {
            nav(note)
        }
    }

}