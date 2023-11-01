package com.example.tdone.createElements

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tdone.MainActivity
import com.example.tdone.R
import com.example.tdone.auth.SignUp
import com.example.tdone.databinding.ActivityCreateTagBinding
import com.example.tdone.dataclasses.TagDataClass
import com.example.tdone.rvHoldersYAdapters.rvSelections.selectionBackground.SelectionColorAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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
    private lateinit var tags: MutableList<TagDataClass>
    private val db = FirebaseFirestore.getInstance()
    private val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTagBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initValues()
        initUi()
        initListeners()
    }

    private fun initValues() {
        db.collection(SignUp.KEY_USER).document(user?.email!!).get().addOnSuccessListener {
            tags = it.get(SignUp.KEY_ALL_TAGS) as MutableList<TagDataClass>
        }
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
        binding.btnAdd.setOnClickListener {
            val name = binding.etTagName.text.toString()
            if (name != null) {
                val newTag = TagDataClass(
                    tagName = name,
                    tagColor = selectedColor
                )
                tags.add(newTag)
                val changes = hashMapOf<String, Any>(SignUp.KEY_ALL_TAGS to tags)
                updateData(changes)
            }
        }
    }

    private fun updateData(changes: java.util.HashMap<String, Any>) {
        db.collection(SignUp.KEY_USER).document(user?.email!!).update(changes)
            .addOnSuccessListener {
                onBackPressed()
            }
    }

    private fun updateColor(color: Int) {
        if (color != selectedColor) {
            selectedColor = color
            selectionColorAdapter.currentColor = selectedColor
            selectionColorAdapter.notifyDataSetChanged()
        }
    }
}