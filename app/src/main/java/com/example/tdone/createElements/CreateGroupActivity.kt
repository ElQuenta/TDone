package com.example.tdone.createElements

import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import androidx.appcompat.app.AppCompatActivity
import com.example.tdone.databinding.ActivityCreateGroupBinding

class CreateGroupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateGroupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
        initUi()
    }

    private fun initUi() {

    }

    private fun initListeners() {
        binding.icCancel.setOnClickListener { onBackPressed() }
    }
}