package com.example.tdone.createElements

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tdone.databinding.ActivityCreateTaskBinding

class CreateTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
//        initUi()
    }

    private fun initUi() {
        TODO("Not yet implemented")
    }

    private fun initListeners() {
        binding.icCancel.setOnClickListener { onBackPressed() }
    }
}