package com.example.tdone.createElements

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tdone.MainActivity
import com.example.tdone.R
import com.example.tdone.auth.SignUp
import com.example.tdone.createElements.CreateTaskActivity.ScreenState.NO_SELECT
import com.example.tdone.createElements.CreateTaskActivity.ScreenState.SELECT_DATE
import com.example.tdone.createElements.CreateTaskActivity.ScreenState.SELECT_GROUP
import com.example.tdone.createElements.CreateTaskActivity.ScreenState.SELECT_LINK
import com.example.tdone.createElements.CreateTaskActivity.ScreenState.SELECT_TAG
import com.example.tdone.databinding.ActivityCreateTaskBinding
import com.example.tdone.dataclasses.GroupDataClass
import com.example.tdone.dataclasses.NoteDataClass
import com.example.tdone.dataclasses.TagDataClass
import com.example.tdone.dataclasses.TaskDataClass
import com.example.tdone.rvHoldersYAdapters.rvSelections.selectionGroups.SelectionGroupsAdapter
import com.example.tdone.rvHoldersYAdapters.rvSelections.selectionNotes.SelectionNotesAdapter
import com.example.tdone.rvHoldersYAdapters.rvSelections.selectionTags.SelectionTagAdapter
import com.example.tdone.rvHoldersYAdapters.rvTags.TagsAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar
import java.util.Locale


class CreateTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateTaskBinding

    private lateinit var selectionTagsAdapter: SelectionTagAdapter
    private lateinit var selectedTagsAdapter: TagsAdapter
    private lateinit var selectionNoteAdapter: SelectionNotesAdapter
    private lateinit var selectionGroupAdapter: SelectionGroupsAdapter

    private lateinit var alltasks: MutableList<TaskDataClass>
    private lateinit var allNotes: MutableList<NoteDataClass>
    private lateinit var tags: MutableList<TagDataClass>
    private lateinit var notes: MutableList<NoteDataClass>
    private lateinit var groups: List<GroupDataClass>
    private val selectedTags = mutableListOf<TagDataClass>()
    private lateinit var selectedNote: NoteDataClass
    private lateinit var selectedGroup: GroupDataClass
    private var selectedDate: Long? = null
    private var selectedDateString: String? = null

    private val db = FirebaseFirestore.getInstance()
    private val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initValues()
        notes.add(NoteDataClass("Ninguna"," ", noteBackground = R.color.white))
        selectedNote=notes.last()
        tags.add(TagDataClass("Nueva Tag",R.color.white))
        selectedGroup=groups.last()
        initUi()
        initListeners()
    }

    private fun initValues() {
        db.collection(SignUp.KEY_USER).document(user?.email!!).get().addOnSuccessListener {
            allNotes = it.get(SignUp.KEY_ALL_NOTES) as MutableList<NoteDataClass>
            alltasks = it.get(SignUp.KEY_ALL_TASKS) as MutableList<TaskDataClass>
            groups = it.get(SignUp.KEY_ALL_GROUPS) as MutableList<GroupDataClass>
            tags = it.get(SignUp.KEY_ALL_TAGS) as MutableList<TagDataClass>
        }
        notes = allNotes.filter { notes -> !notes.hasVinculation }.toMutableList()
    }

    enum class ScreenState() {
        NO_SELECT, SELECT_LINK, SELECT_DATE, SELECT_GROUP, SELECT_TAG
    }

    private var screenState = NO_SELECT

    private fun initUi() {
        selectionTagsAdapter = SelectionTagAdapter(
            tags,
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
        binding.calendarView.setOnDateChangeListener { _, year, month, day ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, day)
            val currentDateInMillis = Calendar.getInstance().timeInMillis
            if (selectedDate.timeInMillis > currentDateInMillis) {
                // La fecha seleccionada es futura, puedes trabajar con ella aquí
                // Por ejemplo, puedes obtener la fecha en un formato específico
                val sdf1 = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val sdf2 = SimpleDateFormat("ddMMyyyy", Locale.getDefault())
                selectedDateString = sdf1.format(selectedDate.time)
                this.selectedDate = sdf2.format(selectedDate.time).toLong()
                Toast.makeText(
                    this,
                     "${R.string.Fecha_escogida} ${selectedDateString ?:""}",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // La fecha seleccionada es anterior o igual a la fecha actual
                // Puedes mostrar un mensaje o realizar otra acción si lo deseas
                Toast.makeText(this, R.string.Selecciona_una_fecha_futura, Toast.LENGTH_SHORT).show()
            }

        }
        binding.btnAdd.setOnClickListener {
            val newTaskTitle = binding.etTaskTittle.text.toString()
            if (newTaskTitle != null) {
                val newTask = TaskDataClass(
                    taskName = newTaskTitle,
                    taskTags = selectedTags,
                    taskGroup = if (selectedGroup == groups.last()) null else selectedGroup,
                    taskVinculation = if (selectedNote == notes.last()) null else selectedNote,
                    hasVinculation = selectedNote != notes.last(),
                    taskEndDate = selectedDate,
                    taskEndDateString = selectedDateString
                )
                alltasks.add(newTask)
                val changes: HashMap<String, Any>
                if (newTask.hasVinculation) {
                    allNotes[allNotes.indexOf(selectedNote)].hasVinculation = true
                    allNotes[allNotes.indexOf(selectedNote)].noteVinculation = newTask
                    changes = hashMapOf(
                        SignUp.KEY_ALL_TASKS to alltasks,
                        SignUp.KEY_ALL_NOTES to allNotes
                    )
                } else {
                    changes = hashMapOf(SignUp.KEY_ALL_TASKS to alltasks)
                }
                updateData(changes)
            }
        }
    }

    private fun updateData(changes: java.util.HashMap<String, Any>) {
        db.collection(SignUp.KEY_USER).document(user?.email!!).update(changes)
            .addOnSuccessListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
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