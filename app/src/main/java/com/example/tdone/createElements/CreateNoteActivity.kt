package com.example.tdone.createElements

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tdone.MainActivity
import com.example.tdone.R
import com.example.tdone.auth.SignUp
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CreateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateNoteBinding

    private lateinit var selectionTagsAdapter: SelectionTagAdapter
    private lateinit var selectedTagsAdapter: TagsAdapter
    private lateinit var selectionTaskAdapter: SelectionTasksAdapter
    private lateinit var selectionColorsAdapter: SelectionColorAdapter
    private lateinit var selectionFrontAdapter: SelectionFrontAdapter

    private lateinit var tags : MutableList<TagDataClass>
    private lateinit var groups : List<GroupDataClass>
    private lateinit var tasks : MutableList<TaskDataClass>
    private lateinit var allNotes: MutableList<NoteDataClass>
    private lateinit var alltasks: MutableList<TaskDataClass>
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
    private lateinit var selectedTask : TaskDataClass
    private val selectedTags = mutableListOf<TagDataClass>()
    private var selectedColor = colors[0]
    private var selectedFront = fronts[0]

    private val db = FirebaseFirestore.getInstance()
    private val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initValues()
        tasks.add(TaskDataClass("none"))
        tags.add(TagDataClass("Nueva Tag",R.color.white))
        selectedTask=tasks.last()
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
    private fun initValues() {
        db.collection(SignUp.KEY_USER).document(user?.email!!).get().addOnSuccessListener {
            allNotes = it.get(SignUp.KEY_ALL_NOTES) as MutableList<NoteDataClass>
            alltasks = it.get(SignUp.KEY_ALL_TASKS) as MutableList<TaskDataClass>
            groups = it.get(SignUp.KEY_ALL_GROUPS) as MutableList<GroupDataClass>
            tags = it.get(SignUp.KEY_ALL_TAGS) as MutableList<TagDataClass>
        }
        tasks = alltasks.filter { task -> !task.hasVinculation }.toMutableList()
    }
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
                allNotes.add(newNote)
                val changes :HashMap<String,Any>
                if (newNote.hasVinculation){
                    alltasks[alltasks.indexOf(selectedTask)].hasVinculation=true
                    alltasks[alltasks.indexOf(selectedTask)].taskVinculation=newNote
                    changes = hashMapOf<String,Any>(SignUp.KEY_ALL_NOTES to allNotes,
                        SignUp.KEY_ALL_TASKS to alltasks)
                }else{
                    changes = hashMapOf<String,Any>(SignUp.KEY_ALL_NOTES to allNotes)
                }
                updateData(changes)
            }

        }
    }

    private fun updateData(changes: java.util.HashMap<String, Any>) {
        db.collection(SignUp.KEY_USER).document(user?.email!!).update(changes).addOnSuccessListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            fronts.last().uriImage= data.data
            selectionFrontAdapter.updateImages(fronts)

            // También puedes actualizar la vista de la imagen seleccionada
            binding.ivFrontNote.setImageURI(selectedImageUri)
        }
    }

    private var selectedImageUri: Uri? = null
    private val IMAGE_PICK_CODE = 1000
    private fun updateFront(image: ImageDataClass) {
        fronts[fronts.indexOf(selectedFront)].selected = false
        if (selectedFront != image) {
            when (image) {
                fronts[0] -> {
                    binding.ivFrontNote.visibility = View.GONE
                }

                fronts.last() -> {
                    image.isReferenceImage = false

                    if (image.isReferenceImage) {
                        binding.ivFrontNote.setImageResource(image.refImage)
                    } else {
                        // Abre la galería para seleccionar una imagen
                        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(intent, IMAGE_PICK_CODE)
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