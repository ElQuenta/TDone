package com.example.tdone.viewIntoElements

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tdone.MainActivity
import com.example.tdone.databinding.ActivityGroupViewBinding
import com.example.tdone.dataclasses.GroupDataClass

class GroupViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGroupViewBinding
    private lateinit var data: GroupDataClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        data = intent.getSerializableExtra(MainActivity.KEY_GROUP) as GroupDataClass

        initListeners()
        initUi()
    }

    private fun initUi() {
        binding.tvGroupName.text = data.groupName
        binding.tvGroupDescription.text = data.groupDescription
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