package com.example.tdone.createElements

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tdone.R
import com.example.tdone.createElements.CreateTaskActivity.ScreenState.NO_SELECT
import com.example.tdone.createElements.CreateTaskActivity.ScreenState.SELECT_DATE
import com.example.tdone.createElements.CreateTaskActivity.ScreenState.SELECT_GROUP
import com.example.tdone.createElements.CreateTaskActivity.ScreenState.SELECT_LINK
import com.example.tdone.createElements.CreateTaskActivity.ScreenState.SELECT_TAG
import com.example.tdone.databinding.ActivityCreateTaskBinding
import com.example.tdone.dataclasses.GroupDataClass
import com.example.tdone.dataclasses.NoteDataClass
import com.example.tdone.dataclasses.TagDataClass
import com.example.tdone.rvHoldersYAdapters.rvSelections.selectionGroups.SelectionGroupsAdapter
import com.example.tdone.rvHoldersYAdapters.rvSelections.selectionNotes.SelectionNotesAdapter
import com.example.tdone.rvHoldersYAdapters.rvSelections.selectionTags.SelectionTagAdapter
import com.example.tdone.rvHoldersYAdapters.rvTags.TagsAdapter


class CreateTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateTaskBinding

    private lateinit var selectionTagsAdapter: SelectionTagAdapter
    private lateinit var selectedTagsAdapter: TagsAdapter
    private lateinit var selectionNoteAdapter: SelectionNotesAdapter
    private lateinit var selectionGroupAdapter: SelectionGroupsAdapter

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
    private val notes = listOf<NoteDataClass>(
        NoteDataClass(
            name = "Prueba 1",
            content = "Prueba 1",
            color = R.color.background_note_color2
        ), NoteDataClass(
            name = "Prueba 2", content = "Prueba", color = R.color.background_note_color3
        ), NoteDataClass(
            name = "Prueba 3", content = "Prueba", color = R.color.background_note_color4
        ), NoteDataClass(
            name = "Prueba 4", content = "Prueba", color = R.color.background_note_color5
        ), NoteDataClass(
            name = "Prueba 5", content = "Prueba", color = R.color.background_note_color1
        ), NoteDataClass(
            name = "Ninguno", content = "", color = R.color.background_note_color1
        )
    )
    private val groups = listOf<GroupDataClass>(
        GroupDataClass(
            name = "Grupo Prueba1",
            description = "Grupo Prueba1"
        ), GroupDataClass(
            name = "Grupo Prueba2",
            description = "Grupo Prueba2"
        ),GroupDataClass(
            name = "Grupo Prueba3",
            description = "Grupo Prueba1"
        ), GroupDataClass(
            name = "Grupo Prueba4",
            description = "Grupo Prueba2"
        ),GroupDataClass(
            name = "Grupo Prueba5",
            description = "Grupo Prueba1"
        ), GroupDataClass(
            name = "ninguno",
            description = ""
        )
    )
    private val selectedTags = mutableListOf<TagDataClass>()
    private var selectedNote = notes.last()
    private var selectedGroup = groups.last()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
        initUi()
    }

    enum class ScreenState() {
        NO_SELECT, SELECT_LINK, SELECT_DATE, SELECT_GROUP, SELECT_TAG
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

        selectionNoteAdapter = SelectionNotesAdapter(notes, selectedNote) { note ->
            updateNote(note)
        }
        binding.rvSelectionNote.apply {
            this.layoutManager =
                GridLayoutManager(binding.root.context, 2, GridLayoutManager.VERTICAL, false)
            this.adapter = selectionNoteAdapter
        }

        selectionGroupAdapter = SelectionGroupsAdapter(groups, selectedGroup) { group ->
            updateGroup(group)
        }
        binding.rvSelectionGroup.apply {
            this.layoutManager =
                LinearLayoutManager(binding.root.context)
            this.adapter = selectionGroupAdapter
        }
    }

    private fun initListeners() {
        binding.icCancel.setOnClickListener { onBackPressed() }
        binding.btnAddLink.setOnClickListener { changeScreenState(SELECT_LINK) }
        binding.btnAddDate.setOnClickListener { changeScreenState(SELECT_DATE) }
        binding.btnAddGroup.setOnClickListener { changeScreenState(SELECT_GROUP) }
        binding.btnAddTag.setOnClickListener { changeScreenState(SELECT_TAG) }
    }

    private fun changeScreenState(newScreenState: ScreenState) {
        binding.selectionLink.visibility = View.GONE
        binding.selectionDate.visibility = View.GONE
        binding.selectionGroup.visibility = View.GONE
        binding.selectionTag.visibility = View.GONE
        if (screenState != newScreenState) {
            screenState = newScreenState
            when (newScreenState) {
                NO_SELECT -> {}
                SELECT_LINK -> binding.selectionLink.visibility = View.VISIBLE
                SELECT_DATE -> binding.selectionDate.visibility = View.VISIBLE
                SELECT_GROUP -> binding.selectionGroup.visibility = View.VISIBLE
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

    private fun updateSelectedTags() {
        selectedTagsAdapter.tags = selectedTags
        selectedTagsAdapter.notifyDataSetChanged()
    }

    private fun updateNote(note: NoteDataClass) {
        if (selectedNote != note) {
            selectedNote = note
        } else {
            selectedNote = notes.last()
        }
        selectionNoteAdapter.currentNote = selectedNote
        selectionNoteAdapter.notifyDataSetChanged()
    }

    private fun updateGroup(group: GroupDataClass) {
        if (group != selectedGroup) {
            selectedNote
        }
    }

}