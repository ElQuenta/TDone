package com.example.tdone.createElements

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tdone.databinding.ActivityCreateGroupBinding
import com.example.tdone.dataclasses.GroupDataClass

class CreateGroupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateGroupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
    }

    private fun initListeners() {
        binding.icCancel.setOnClickListener { onBackPressed() }
        binding.btnAdd.setOnClickListener {
            val titleGroup = binding.etGroupName.text.toString()
            if (titleGroup != null) {
                var newGroup = GroupDataClass(
                    groupName = titleGroup,
                    groupDescription = binding.etGroupDescription.text.toString()
                )
            }
        }
    }
}