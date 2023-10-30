package com.example.tdone.createElements

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tdone.R
import com.example.tdone.createElements.CreateNoteActivity.ScreenState.NO_SELECT
import com.example.tdone.createElements.CreateNoteActivity.ScreenState.SELECT_BACKGROUND
import com.example.tdone.createElements.CreateNoteActivity.ScreenState.SELECT_FRONT
import com.example.tdone.createElements.CreateNoteActivity.ScreenState.SELECT_LINK
import com.example.tdone.createElements.CreateNoteActivity.ScreenState.SELECT_TAG
import com.example.tdone.databinding.ActivityCreateNoteBinding
import com.example.tdone.dataclasses.GroupDataClass
import com.example.tdone.dataclasses.TagDataClass
import com.example.tdone.dataclasses.TaskDataClass
import com.example.tdone.getSize
import com.example.tdone.rvHoldersYAdapters.rvSelections.selectionBackground.SelectionColorAdapter
import com.example.tdone.rvHoldersYAdapters.rvSelections.selectionFront.selectionFrontAdapter
import com.example.tdone.rvHoldersYAdapters.rvSelections.selectionTags.SelectionTagAdapter
import com.example.tdone.rvHoldersYAdapters.rvSelections.selectionTasks.SelectionTasksAdapter
import com.example.tdone.rvHoldersYAdapters.rvTags.TagsAdapter

class CreateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateNoteBinding

    private lateinit var selectionTagsAdapter: SelectionTagAdapter
    private lateinit var selectedTagsAdapter: TagsAdapter
    private lateinit var selectionTaskAdapter: SelectionTasksAdapter
    private lateinit var selectionColorsAdapter: SelectionColorAdapter
    private lateinit var selectionFrontAdapter: selectionFrontAdapter

    private val tags = mutableListOf(
        TagDataClass(
            name = "Tag Prueba1",
            color = R.color.tag_color1
        ), TagDataClass(
            name = "Tag Prueba2",
            color = R.color.tag_color2
        ), TagDataClass(
            name = "Tag Prueba3",
            color = R.color.tag_color3
        ), TagDataClass(
            name = "Tag Prueba4",
            color = R.color.tag_color4
        ), TagDataClass(
            name = "Tag Prueba5",
            color = R.color.tag_color5
        ), TagDataClass(
            name = "Tag Prueba6",
            color = R.color.tag_color6
        ), TagDataClass(
            name = "Tag Prueba7",
            color = R.color.tag_color7
        ), TagDataClass(
            name = "Tag Prueba8",
            color = R.color.tag_color8
        ), TagDataClass(
            name = "Tag Prueba9",
            color = R.color.tag_color9
        ), TagDataClass(
            name = "Tag Prueba10",
            color = R.color.tag_color10
        ), TagDataClass(
            name = "Tag Prueba11",
            color = R.color.tag_color11
        ), TagDataClass(
            name = "Nueva Tag",
            color = R.color.white
        )

    )
    private val groups = listOf<GroupDataClass>(
        GroupDataClass(
            name = "Grupo Prueba1",
            description = "Grupo Prueba1"
        ), GroupDataClass(
            name = "Grupo Prueba2",
            description = "Grupo Prueba2"
        )
    )
    private val tasks = listOf<TaskDataClass>(
        TaskDataClass(
            name = "Tarea Prueba 1",
            tag_DataClasses = listOf(tags[0]),
            group = groups[0]
        ), TaskDataClass(
            name = "Tarea Prueba 2",
            tag_DataClasses = listOf(tags[1]),
            group = groups[1]
        ), TaskDataClass(
            name = "Tarea Prueba 3",
            tag_DataClasses = listOf(tags[2]),
            group = groups[0]
        ), TaskDataClass(
            name = "Tarea Prueba 4",
            tag_DataClasses = listOf(tags[0], tags[1]),
            group = groups[0]
        ), TaskDataClass(
            name = "Tarea Prueba 5",
            tag_DataClasses = listOf(tags[2], tags[3]),
            group = groups[1]
        ), TaskDataClass(
            name = "Tarea Prueba 6",
            group = groups[0]
        ), TaskDataClass(
            name = "Tarea Prueba 7"
        ), TaskDataClass(
            name = "Tarea Prueba 8",
            tag_DataClasses = listOf(tags[2], tags[3])
        ), TaskDataClass(name = "Ninguna Tarea")
    )
    private val colors = listOf(
        R.color.background_note_color1,
        R.color.background_note_color2,
        R.color.background_note_color3,
        R.color.background_note_color4,
        R.color.background_note_color5,
        R.color.background_note_color6,
        R.color.background_note_color7,
        R.color.background_note_color8,
        R.color.background_note_color9,
        R.color.background_note_color10,
        R.color.background_note_color11,
        R.color.background_note_color12
    )
    private val fronts = listOf(
        R.drawable.avatar1,
        R.drawable.frontpage1,
        R.drawable.frontpage2,
        R.drawable.frontpage3,
        R.drawable.frontpage4,
        R.drawable.frontpage5,
        R.drawable.frontpage6,
        R.drawable.frontpage7,
        R.drawable.frontpage8,
        R.drawable.avatar
    )
    private var selectedTask = tasks.last()
    private val selectedTags = mutableListOf<TagDataClass>()
    private var selectedColor = colors[0]
    private var selectedFront = fronts[0]
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
        initListeners()
    }

    /*
    enum class marcando las opciones avanzadas
     */
    enum class ScreenState() {
        NO_SELECT, SELECT_LINK, SELECT_FRONT, SELECT_BACKGROUND, SELECT_TAG
    }

    private var screenState = NO_SELECT

    private fun initUi() {
        selectionTagsAdapter = SelectionTagAdapter(tags) { tag, isChecked ->
            updateTag(tag, isChecked)
        }
        binding.rvSelectionTag.layoutManager = LinearLayoutManager(this)
        binding.rvSelectionTag.adapter = selectionTagsAdapter

        selectedTagsAdapter = TagsAdapter(selectedTags)
        binding.rvSelectedTags.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvSelectedTags.adapter = selectedTagsAdapter

        selectionTaskAdapter = SelectionTasksAdapter(tasks, selectedTask) { task ->
            updateTask(task)
        }
        binding.rvSelectionTask.apply {
            this.layoutManager = LinearLayoutManager(binding.root.context)
            this.adapter = selectionTaskAdapter
        }

        selectionColorsAdapter = SelectionColorAdapter(colors, selectedColor) { color ->
            updateColor(color)
        }
        binding.rvSelectionColor.apply {
            this.layoutManager =
                GridLayoutManager(binding.root.context, 4, GridLayoutManager.VERTICAL, false)
            this.adapter = selectionColorsAdapter
        }

        selectionFrontAdapter = selectionFrontAdapter(fronts,selectedFront,getSize().getCreateNoteScreenWidth(this)){ image ->
            updateFront(image)
        }
        binding.rvSelectionFront.apply {
            this.layoutManager =
                GridLayoutManager(binding.root.context, 2, GridLayoutManager.HORIZONTAL, false)
            this.adapter = selectionFrontAdapter
        }
    }

    private fun initListeners() {
        binding.icCancel.setOnClickListener { onBackPressed() }
        binding.btnAddLink.setOnClickListener { changeScreenState(SELECT_LINK) }
        binding.btnAddFront.setOnClickListener { changeScreenState(SELECT_FRONT) }
        binding.btnAddBackground.setOnClickListener { changeScreenState(SELECT_BACKGROUND) }
        binding.btnAddTag.setOnClickListener { changeScreenState(SELECT_TAG) }
    }

    private fun changeScreenState(newScreenState: ScreenState) {
        binding.selectionLink.visibility = View.GONE
        binding.selectionFront.visibility = View.GONE
        binding.selectionBackground.visibility = View.GONE
        binding.selectionTag.visibility = View.GONE
        if (screenState != newScreenState) {
            screenState = newScreenState
            when (newScreenState) {
                NO_SELECT -> {}
                SELECT_LINK -> binding.selectionLink.visibility = View.VISIBLE
                SELECT_FRONT -> binding.selectionFront.visibility = View.VISIBLE
                SELECT_BACKGROUND -> binding.selectionBackground.visibility = View.VISIBLE
                SELECT_TAG -> binding.selectionTag.visibility = View.VISIBLE
            }
        } else {
            screenState = NO_SELECT
        }
    }

    private fun updateTag(tag: TagDataClass, isChecked: Boolean) {
        if (isChecked) {
            selectedTags.add(tag)
        } else {
            selectedTags.remove(tag)
        }
        updateSelectedTags()
    }

    private fun updateTask(task: TaskDataClass) {
        if (selectedTask != task) {
            selectedTask = task
            selectionTaskAdapter.currentTask = selectedTask
            selectionTaskAdapter.notifyDataSetChanged()
        }
    }

    private fun updateColor(color: Int) {
        if (color != selectedColor) {
            selectedColor = color
            binding.root.setBackgroundColor(ContextCompat.getColor(this, selectedColor))
            selectionColorsAdapter.currentColor = selectedColor
            selectionColorsAdapter.notifyDataSetChanged()
        }
    }

    private fun updateFront(image: Int) {
        if (selectedFront != image) {
            when(image){
                fronts[0] -> {
                    binding.ivFrontNote.visibility = View.GONE
                }
                fronts.last() -> {
                    binding.ivFrontNote.setImageResource(image)
                    binding.ivFrontNote.visibility = View.VISIBLE
                }
                else -> {
                    binding.ivFrontNote.setImageResource(image)
                    binding.ivFrontNote.visibility = View.VISIBLE
                }
            }
            selectedFront = image
            selectionFrontAdapter.currentImage = selectedFront
            selectionFrontAdapter.notifyDataSetChanged()
        }
    }

    private fun updateSelectedTags() {
        selectedTagsAdapter.tags = selectedTags
        selectedTagsAdapter.notifyDataSetChanged()
    }

}