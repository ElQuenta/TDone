package com.example.tdone.viewIntoElements

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tdone.MainActivity
import com.example.tdone.databinding.ActivityTaskViewBinding
import com.example.tdone.dataclasses.TaskDataClass
import com.example.tdone.rvHoldersYAdapters.rvTags.TagsAdapter

class TaskViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskViewBinding
    private lateinit var data : TaskDataClass
    private lateinit var tagsAdapter: TagsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        data = intent.getSerializableExtra(MainActivity.KEY_TASK) as TaskDataClass

        initListeners()
        initUi()

    }

    private fun initUi() {
        binding.tvTaskTittle.text = data.taskName
        tagsAdapter = TagsAdapter(data.taskTags)
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