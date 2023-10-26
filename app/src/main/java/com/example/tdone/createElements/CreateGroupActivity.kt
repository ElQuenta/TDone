package com.example.tdone.createElements

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tdone.databinding.ActivityCreateGroupBinding

class CreateGroupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateGroupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateGroupBinding.inflate(layoutInflater)
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