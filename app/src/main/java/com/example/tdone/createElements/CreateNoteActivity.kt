package com.example.tdone.createElements

import android.content.Intent
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
import com.example.tdone.dataclasses.ImageDataClass
import com.example.tdone.dataclasses.NoteDataClass
import com.example.tdone.dataclasses.TagDataClass
import com.example.tdone.dataclasses.TaskDataClass
import com.example.tdone.rvHoldersYAdapters.rvSelections.selectionBackground.SelectionColorAdapter
import com.example.tdone.rvHoldersYAdapters.rvSelections.selectionFront.SelectionFrontAdapter
import com.example.tdone.rvHoldersYAdapters.rvSelections.selectionTags.SelectionTagAdapter
import com.example.tdone.rvHoldersYAdapters.rvSelections.selectionTasks.SelectionTasksAdapter
import com.example.tdone.rvHoldersYAdapters.rvTags.TagsAdapter

class CreateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateNoteBinding

    private lateinit var selectionTagsAdapter: SelectionTagAdapter
    private lateinit var selectedTagsAdapter: TagsAdapter
    private lateinit var selectionTaskAdapter: SelectionTasksAdapter
    private lateinit var selectionColorsAdapter: SelectionColorAdapter
    private lateinit var selectionFrontAdapter: SelectionFrontAdapter

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
    private val groups = listOf<GroupDataClass>(
        GroupDataClass(
            groupName = "Grupo Prueba1",
            groupDescription = "Grupo Prueba1"
        ), GroupDataClass(
            groupName = "Grupo Prueba2",
            groupDescription = "Grupo Prueba2"
        )
    )
    private val tasks = listOf<TaskDataClass>(
        TaskDataClass(
            taskName = "Tarea Prueba 1",
            taskTags = listOf(tags[0]),
            taskGroup = groups[0]
        ), TaskDataClass(
            taskName = "Tarea Prueba 2",
            taskTags = listOf(tags[1]),
            taskGroup = groups[1]
        ), TaskDataClass(
            taskName = "Tarea Prueba 3",
            taskTags = listOf(tags[2]),
            taskGroup = groups[0]
        ), TaskDataClass(
            taskName = "Tarea Prueba 4",
            taskTags = listOf(tags[0], tags[1]),
            taskGroup = groups[0]
        ), TaskDataClass(
            taskName = "Tarea Prueba 5",
            taskTags = listOf(tags[2], tags[3]),
            taskGroup = groups[1]
        ), TaskDataClass(
            taskName = "Tarea Prueba 6",
            taskGroup = groups[0]
        ), TaskDataClass(
            taskName = "Tarea Prueba 7"
        ), TaskDataClass(
            taskName = "Tarea Prueba 8",
            taskTags = listOf(tags[2], tags[3])
        ), TaskDataClass(taskName = "Ninguna Tarea")
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
    private val fronts = listOf<ImageDataClass>(
        ImageDataClass(
            refImage = R.drawable.avatar,
            isReferenceImage = true,
            selected = true
        ), ImageDataClass(
            refImage = R.drawable.frontpage1,
            isReferenceImage = true,
        ), ImageDataClass(
            refImage = R.drawable.frontpage2,
            isReferenceImage = true,
        ), ImageDataClass(
            refImage = R.drawable.frontpage3,
            isReferenceImage = true,
        ), ImageDataClass(
            refImage = R.drawable.frontpage4,
            isReferenceImage = true,
        ), ImageDataClass(
            refImage = R.drawable.frontpage5,
            isReferenceImage = true,
        ), ImageDataClass(
            refImage = R.drawable.frontpage6,
            isReferenceImage = true,
        ), ImageDataClass(
            refImage = R.drawable.frontpage7,
            isReferenceImage = true,
        ), ImageDataClass(
            refImage = R.drawable.frontpage8,
            isReferenceImage = true,
        ), ImageDataClass(
            refImage = R.drawable.avatar1,
            isReferenceImage = true,
        )


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

        selectionTaskAdapter = SelectionTasksAdapter(tasks) { task ->
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

        selectionFrontAdapter = SelectionFrontAdapter(fronts) { image ->
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
        binding.btnAdd.setOnClickListener {
            val tittle = binding.etNoteTitle.text.toString()
            val body = binding.etNoteBody.text.toString()
            if (tittle != null && body != null) {
                val newNote = NoteDataClass(
                    noteTittle = tittle,
                    noteBody = body,
                    noteBackground = selectedColor,
                    noteVinculation = if (selectedTask == tasks.last()) null else selectedTask,
                    hasVinculation = selectedTask != tasks.last(),
                    noteTags = selectedTags,
                    noteFront = if (selectedFront == fronts[0]) null else selectedFront
                )
            }

        }
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
        tasks[tasks.indexOf(selectedTask)].hasVinculation = false
        if (selectedTask != task) {
            selectedTask = task
        } else {
            selectedTask != tasks.last()
        }
        tasks[tasks.indexOf(selectedTask)].hasVinculation = true
        selectionTaskAdapter.updateTasks(tasks)

    }

    private fun updateColor(color: Int) {
        if (color != selectedColor) {
            selectedColor = color
            binding.root.setBackgroundColor(ContextCompat.getColor(this, selectedColor))
            selectionColorsAdapter.currentColor = selectedColor
            selectionColorsAdapter.notifyDataSetChanged()
        }
    }

    private fun updateFront(image: ImageDataClass) {
        fronts[fronts.indexOf(selectedFront)].selected = false
        if (selectedFront != image) {
            when (image) {
                fronts[0] -> {
                    binding.ivFrontNote.visibility = View.GONE
                }

                fronts.last() -> {
                    if (image.isReferenceImage) {
                        binding.ivFrontNote.setImageResource(image.refImage)
                    } else {
                        binding.ivFrontNote.setImageURI(image.uriImage)
                    }
                    binding.ivFrontNote.visibility = View.VISIBLE
                }

                else -> {
                    if (image.isReferenceImage) {
                        binding.ivFrontNote.setImageResource(image.refImage)
                    } else {
                        binding.ivFrontNote.setImageURI(image.uriImage)
                    }
                    binding.ivFrontNote.visibility = View.VISIBLE
                }
            }
            selectedFront = image
        } else {
            selectedFront = fronts[0]
        }
        fronts[fronts.indexOf(selectedFront)].selected = true

        selectionFrontAdapter.updateImages(fronts)
    }

    private fun updateSelectedTags() {
        selectedTagsAdapter.tags = selectedTags
        selectedTagsAdapter.notifyDataSetChanged()
    }

    private fun createTag() {
        val intent = Intent(this, CreateTagActivity::class.java)
        startActivity(intent)
    }

}