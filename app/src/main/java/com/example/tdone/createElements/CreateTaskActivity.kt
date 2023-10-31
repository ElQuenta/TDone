package com.example.tdone.createElements

import android.content.Intent
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
            tagName = "Tag Prueba1",
            tagColor = R.color.tag_color1
        ), TagDataClass(
            tagName = "Tag Prueba2",
            tagColor = R.color.tag_color2
        ), TagDataClass(
            tagName = "Tag Prueba3",
            tagColor = R.color.tag_color3
        ), TagDataClass(
            tagName = "Tag Prueba4",
            tagColor = R.color.tag_color4
        ), TagDataClass(
            tagName = "Tag Prueba5",
            tagColor = R.color.tag_color5
        ), TagDataClass(
            tagName = "Tag Prueba6",
            tagColor = R.color.tag_color6
        ), TagDataClass(
            tagName = "Tag Prueba7",
            tagColor = R.color.tag_color7
        ), TagDataClass(
            tagName = "Tag Prueba8",
            tagColor = R.color.tag_color8
        ), TagDataClass(
            tagName = "Tag Prueba9",
            tagColor = R.color.tag_color9
        ), TagDataClass(
            tagName = "Tag Prueba10",
            tagColor = R.color.tag_color10
        ), TagDataClass(
            tagName = "Tag Prueba11",
            tagColor = R.color.tag_color11
        ), TagDataClass(
            tagName = "Nueva Tag",
            tagColor = R.color.white
        )

    )
    private val notes = listOf<NoteDataClass>(
        NoteDataClass(
            noteTittle = "Prueba 1",
            noteBody = "Prueba 1",
            noteBackground = R.color.background_note_color2
        ), NoteDataClass(
            noteTittle = "Prueba 2", noteBody = "Prueba", noteBackground = R.color.background_note_color3
        ), NoteDataClass(
            noteTittle = "Prueba 3", noteBody = "Prueba", noteBackground = R.color.background_note_color4
        ), NoteDataClass(
            noteTittle = "Prueba 4", noteBody = "Prueba", noteBackground = R.color.background_note_color5
        ), NoteDataClass(
            noteTittle = "Prueba 5", noteBody = "Prueba", noteBackground = R.color.background_note_color1
        ), NoteDataClass(
            noteTittle = "Ninguno", noteBody = "", noteBackground = R.color.background_note_color1, hasVinculation = true
        )
    )
    private val groups = listOf<GroupDataClass>(
        GroupDataClass(
            groupName = "Grupo Prueba1",
            groupDescription = "Grupo Prueba1"
        ), GroupDataClass(
            groupName = "Grupo Prueba2",
            groupDescription = "Grupo Prueba2"
        ),GroupDataClass(
            groupName = "Grupo Prueba3",
            groupDescription = "Grupo Prueba1"
        ), GroupDataClass(
            groupName = "Grupo Prueba4",
            groupDescription = "Grupo Prueba2"
        ),GroupDataClass(
            groupName = "Grupo Prueba5",
            groupDescription = "Grupo Prueba1"
        ), GroupDataClass(
            groupName = "ninguno",
            groupDescription = "",
            groupSelected = true
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
        selectionTagsAdapter = SelectionTagAdapter(tags,
            change = { tag, isChecked ->
                updateTag(tag, isChecked)
            },
            create = {
                createTag()
            })
        binding.rvSelectionTag.layoutManager = LinearLayoutManager(this)
        binding.rvSelectionTag.adapter = selectionTagsAdapter

        selectedTagsAdapter = TagsAdapter(selectedTags)
        binding.rvSelectedTags.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvSelectedTags.adapter = selectedTagsAdapter

        selectionNoteAdapter = SelectionNotesAdapter(notes) { note ->
            updateNote(note)
        }
        binding.rvSelectionNote.apply {
            this.layoutManager =
                GridLayoutManager(binding.root.context, 2, GridLayoutManager.VERTICAL, false)
            this.adapter = selectionNoteAdapter
        }

        selectionGroupAdapter = SelectionGroupsAdapter(groups) { group ->
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
        notes[notes.indexOf(selectedNote)].hasVinculation=false
        if (selectedNote != note) {
            selectedNote = note
        } else {
            selectedNote = notes.last()
        }
        notes[notes.indexOf(selectedNote)].hasVinculation=true
        selectionNoteAdapter.updateNotes(notes)
    }

    private fun updateGroup(group: GroupDataClass) {
        groups[groups.indexOf(selectedGroup)].groupSelected = false
        if (group != selectedGroup) {
            selectedGroup = group
        } else {
            selectedGroup = groups.last()
        }
        groups[groups.indexOf(selectedGroup)].groupSelected = true
        selectionGroupAdapter.updateGroups(groups)
    }

    private fun createTag() {
        val intent = Intent(this, CreateTagActivity::class.java)
        startActivity(intent)
    }

}