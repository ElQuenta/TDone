package com.example.tdone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tdone.databinding.ActivityTaskViewBinding
import com.example.tdone.dataclasses.TaskDataClass

class TaskViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskViewBinding
    private lateinit var data : TaskDataClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        data = intent.getSerializableExtra(MainActivity.KEY_TASK) as TaskDataClass

        initListeners()
        initUi()

    }

    private fun initUi() {
        binding.tvTaskTittle.text = data.name
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