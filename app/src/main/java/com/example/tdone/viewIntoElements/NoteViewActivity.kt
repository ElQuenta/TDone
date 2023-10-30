package com.example.tdone.viewIntoElements

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tdone.MainActivity
import com.example.tdone.databinding.ActivityNoteViewBinding
import com.example.tdone.dataclasses.NoteDataClass
import com.example.tdone.rvHoldersYAdapters.rvTags.TagsAdapter

class NoteViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteViewBinding
    private lateinit var data: NoteDataClass
    private lateinit var tagsAdapter: TagsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data = intent.getSerializableExtra(MainActivity.KEY_NOTE) as NoteDataClass

        initListeners()
        initUi()
    }

    private fun initUi() {
        binding.tvNoteTittle.text = data.name
        binding.tvNoteBody.text = data.content
        binding.root.setBackgroundColor(ContextCompat.getColor(this, data.color))

        tagsAdapter = TagsAdapter(data.TagDataClass)
        binding.rvSelectedTags.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvSelectedTags.adapter = tagsAdapter
    }

    private fun initListeners() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // Vuelve a la pantalla anterior al hacer clic en el botón de retroceso
        return true
    }
}