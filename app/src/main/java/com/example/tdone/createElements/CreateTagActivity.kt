package com.example.tdone.createElements

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tdone.R
import com.example.tdone.databinding.ActivityCreateTagBinding
import com.example.tdone.rvHoldersYAdapters.rvSelections.selectionBackground.SelectionColorAdapter

class CreateTagActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateTagBinding
    private lateinit var selectionColorAdapter: SelectionColorAdapter

    private val colors = listOf(
        R.color.tag_color1,
        R.color.tag_color2,
        R.color.tag_color3,
        R.color.tag_color4,
        R.color.tag_color5,
        R.color.tag_color6,
        R.color.tag_color7,
        R.color.tag_color8,
        R.color.tag_color9,
        R.color.tag_color10,
        R.color.tag_color11,
        R.color.tag_color12
    )
    private var selectedColor = colors[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTagBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
        initListeners()
    }

    private fun initUi() {
        selectionColorAdapter = SelectionColorAdapter(colors, selectedColor) { color ->
            updateColor(color)
        }
        binding.rvSelectionColor.apply {
            this.layoutManager =
                GridLayoutManager(binding.root.context, 4, GridLayoutManager.VERTICAL, false)
            this.adapter = selectionColorAdapter
        }
    }
    private fun initListeners() {
        binding.icCancel.setOnClickListener { onBackPressed() }
    }

    private fun updateColor(color: Int) {
        if (color != selectedColor) {
            selectedColor = color
            selectionColorAdapter.currentColor = selectedColor
            selectionColorAdapter.notifyDataSetChanged()
        }
    }
}