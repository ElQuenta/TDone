package com.example.tdone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.tdone.databinding.ActivityNoteViewBinding
import com.example.tdone.dataclasses.NoteDataClass

class NoteViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteViewBinding
    private lateinit var data: NoteDataClass
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