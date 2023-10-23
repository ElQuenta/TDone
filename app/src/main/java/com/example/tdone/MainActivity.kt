package com.example.tdone

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tdone.databinding.ActivityMainBinding
import com.example.tdone.dataclasses.GroupDataClass
import com.example.tdone.dataclasses.NoteDataClass
import com.example.tdone.dataclasses.TagDataClass
import com.example.tdone.dataclasses.TaskDataClass
import com.example.tdone.rvHoldersYAdapters.rvBase.baseGroups.BaseGroupsAdapter
import com.example.tdone.rvHoldersYAdapters.rvBase.baseNotes.BaseNotesAdapter
import com.example.tdone.rvHoldersYAdapters.rvBase.baseTasks.BaseTasksAdapter
import com.example.tdone.rvHoldersYAdapters.rvInitialScreen.initialScreenNotes.InitialScreenNotesAdapter
import com.example.tdone.rvHoldersYAdapters.rvInitialScreen.initialScreenTasks.InitialScreenTasksAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var currentNotesAdapter: BaseNotesAdapter
    private lateinit var nearToEndTaskAdapter: BaseTasksAdapter
    private lateinit var allNotesAdapter: BaseNotesAdapter
    private lateinit var allTasksAdapter: BaseTasksAdapter
    private lateinit var allGroupsAdapter: BaseGroupsAdapter
    private lateinit var historyAdapter: BaseTasksAdapter

    enum class Screens() {
        HOME, NOTES, TASKS, GROUPS, HISTOY

    }

    var screenState = Screens.HOME
    private lateinit var plusButton: FloatingActionButton
    private lateinit var editButton: FloatingActionButton
    private lateinit var groupButton: FloatingActionButton
    private lateinit var noteButton: FloatingActionButton

    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_open_plus
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_close_plus
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.from_bottom_anim
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.to_bottom_anim
        )
    }
    private var clicked = false

    private lateinit var currentNotesAdapter: InitialScreenNotesAdapter

    private val notes = listOf<NoteDataClass>(
        NoteDataClass(
            name = "Prueba 1", content = "Prueba", color = R.color.background_note_color2
        ), NoteDataClass(
            name = "Prueba 2", content = "Prueba", color = R.color.background_note_color3
        ), NoteDataClass(
            name = "Prueba 3", content = "Prueba", color = R.color.background_note_color4
        ), NoteDataClass(
            name = "Prueba 4", content = "Prueba", color = R.color.background_note_color5
        ), NoteDataClass(
            name = "Prueba 5", content = "Prueba", color = R.color.background_note_color1
        )
    )

    private val groups = listOf<GroupDataClass>(
        GroupDataClass(
            name = "Grupo Prueba1",
            description = "Grupo Prueba1"
        ), GroupDataClass(
            name = "Grupo Prueba2",
            description = "Grupo Prueba2"
        ), GroupDataClass(
            name = "Grupo Prueba1",
            description = "Grupo Prueba1"
        ), GroupDataClass(
            name = "Grupo Prueba2",
            description = "Grupo Prueba2"
        ), GroupDataClass(
            name = "Grupo Prueba1",
            description = "Grupo Prueba1"
        ), GroupDataClass(
            name = "Grupo Prueba2",
            description = "Grupo Prueba2"
        ), GroupDataClass(
            name = "Grupo Prueba1",
            description = "Grupo Prueba1"
        ), GroupDataClass(
            name = "Grupo Prueba2",
            description = "Grupo Prueba2"
        )
    )

    private val tags = listOf<TagDataClass>(
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
        )
    )

    private val nearToEndTasks = listOf<TaskDataClass>(
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
        )
    )

    private val allNotes = mutableListOf<NoteDataClass>(
        NoteDataClass(
            name = "Prueba 1", content = "Prueba", color = R.color.background_note_color2
        ), NoteDataClass(
            name = "Prueba 2", content = "Prueba", color = R.color.background_note_color3
        ), NoteDataClass(
            name = "Prueba 3", content = "Prueba", color = R.color.background_note_color4
        ), NoteDataClass(
            name = "Prueba 4", content = "Prueba", color = R.color.background_note_color5
        ), NoteDataClass(
            name = "Prueba 5", content = "Prueba", color = R.color.background_note_color1
        ), NoteDataClass(
            name = "Prueba 1", content = "Prueba", color = R.color.background_note_color2
        ), NoteDataClass(
            name = "Prueba 2", content = "Prueba", color = R.color.background_note_color3
        ), NoteDataClass(
            name = "Prueba 3", content = "Prueba", color = R.color.background_note_color4
        ), NoteDataClass(
            name = "Prueba 4", content = "Prueba", color = R.color.background_note_color5
        ), NoteDataClass(
            name = "Prueba 5", content = "Prueba", color = R.color.background_note_color1
        ), NoteDataClass(
            name = "Prueba 1", content = "Prueba", color = R.color.background_note_color2
        ), NoteDataClass(
            name = "Prueba 2", content = "Prueba", color = R.color.background_note_color3
        ), NoteDataClass(
            name = "Prueba 3", content = "Prueba", color = R.color.background_note_color4
        ), NoteDataClass(
            name = "Prueba 4", content = "Prueba", color = R.color.background_note_color5
        ), NoteDataClass(
            name = "Prueba 5", content = "Prueba", color = R.color.background_note_color1
        ), NoteDataClass(
            name = "Prueba 1", content = "Prueba", color = R.color.background_note_color2
        ), NoteDataClass(
            name = "Prueba 2", content = "Prueba", color = R.color.background_note_color3
        ), NoteDataClass(
            name = "Prueba 3", content = "Prueba", color = R.color.background_note_color4
        ), NoteDataClass(
            name = "Prueba 4", content = "Prueba", color = R.color.background_note_color5
        ), NoteDataClass(
            name = "Prueba 5", content = "Prueba", color = R.color.background_note_color1
        )
    )

    private val allTasks = mutableListOf<TaskDataClass>(
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
            name = "Tarea Prueba 7",
        ), TaskDataClass(
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
            name = "Tarea Prueba 7",
        ), TaskDataClass(
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
            name = "Tarea Prueba 7",
        ), TaskDataClass(
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
            name = "Tarea Prueba 7",
        )
    )

    private val history = mutableListOf<TaskDataClass>(
        TaskDataClass(
            name = "Tarea Prueba 1",
            tag_DataClasses = listOf(tags[0]),
            group = groups[0],
            checked = true
        ), TaskDataClass(
            name = "Tarea Prueba 2",
            tag_DataClasses = listOf(tags[1]),
            group = groups[1],
            checked = true
        ), TaskDataClass(
            name = "Tarea Prueba 3",
            tag_DataClasses = listOf(tags[2]),
            group = groups[0],
            checked = true
        ), TaskDataClass(
            name = "Tarea Prueba 4",
            tag_DataClasses = listOf(tags[0], tags[1]),
            group = groups[0],
            checked = true
        ), TaskDataClass(
            name = "Tarea Prueba 5",
            tag_DataClasses = listOf(tags[2], tags[3]),
            group = groups[1],
            checked = true
        ), TaskDataClass(
            name = "Tarea Prueba 6",
            group = groups[0],
            checked = true
        ), TaskDataClass(
            name = "Tarea Prueba 7",
            checked = true
        )
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
        initListeners()

    }

    private fun initListeners() {
        prepearingBurgerMenu()
    }

    private fun initUi() {
        /*
        Screen Home
         */

        currentNotesAdapter = BaseNotesAdapter(notes)
        binding.rvCurrentNotes.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCurrentNotes.adapter = currentNotesAdapter

        nearToEndTaskAdapter = BaseTasksAdapter(nearToEndTasks)
        binding.rvNearToEndTasks.layoutManager = LinearLayoutManager(this)
        binding.rvNearToEndTasks.adapter = nearToEndTaskAdapter

        /*
        Screen Group
         */

        allGroupsAdapter = BaseGroupsAdapter(groups)
        binding.rvAllGroups.layoutManager = LinearLayoutManager(this)
        binding.rvAllGroups.adapter = allGroupsAdapter

        /*
        Screen Tasks
         */

        allTasksAdapter = BaseTasksAdapter(allTasks)
        binding.rvAllTasks.layoutManager = LinearLayoutManager(this)
        binding.rvAllTasks.adapter = allTasksAdapter

        /*
        Screen Notes
         */

        allNotesAdapter = BaseNotesAdapter(allNotes)
        binding.rvAllNotes.layoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        binding.rvAllNotes.adapter = allNotesAdapter
        plusButton.setOnClickListener {
            onAddButtonClicked()
        }
        editButton.setOnClickListener {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
        }
        groupButton.setOnClickListener {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
        }
        noteButton.setOnClickListener {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
        }


        /*
        Screen History
         */
        historyAdapter = BaseTasksAdapter(history)
        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        binding.rvHistory.adapter = historyAdapter

        plusButton = binding.plus
        editButton = binding.edit
        groupButton = binding.group
        noteButton = binding.note

    }

    private fun prepearingBurgerMenu() {
        binding.apply {
            navView.bringToFront()

            setSupportActionBar(toolbar)

            toggle = ActionBarDrawerToggle(
                this@MainActivity,
                drawerLayout,
                R.string.nav_open,
                R.string.nav_close
            )


            drawerLayout.addDrawerListener(toggle)

            navView.setNavigationItemSelectedListener {
                when (it.itemId) {

                    R.id.inic -> {
                        Toast.makeText(this@MainActivity, "INICIO", Toast.LENGTH_SHORT).show()
                        changeScreen(Screens.HOME)
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }

                    R.id.mis -> {
                        Toast.makeText(this@MainActivity, "MIS GRUPOS", Toast.LENGTH_SHORT).show()
                        changeScreen(Screens.GROUPS)
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }

                    R.id.tod -> {
                        Toast.makeText(this@MainActivity, "TODAS LAS TAREAS", Toast.LENGTH_SHORT)
                            .show()
                        changeScreen(Screens.TASKS)
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }

                    R.id.not -> {
                        Toast.makeText(this@MainActivity, "MIS NOTAS", Toast.LENGTH_SHORT).show()
                        changeScreen(Screens.NOTES)
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }

                    R.id.his -> {
                        Toast.makeText(this@MainActivity, "HISTORIAL", Toast.LENGTH_SHORT).show()
                        changeScreen(Screens.HISTOY)
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }
                }
                true
            }


        }

    }


    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            editButton.visibility = View.VISIBLE
            groupButton.visibility = View.VISIBLE
            noteButton.visibility = View.VISIBLE
        } else {
            editButton.visibility = View.INVISIBLE
            groupButton.visibility = View.INVISIBLE
            noteButton.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            editButton.startAnimation(fromBottom)
            groupButton.startAnimation(fromBottom)
            noteButton.startAnimation(fromBottom)
            plusButton.startAnimation(rotateOpen)
        } else {
            editButton.startAnimation(toBottom)
            groupButton.startAnimation(toBottom)
            noteButton.startAnimation(toBottom)
            plusButton.startAnimation(rotateClose)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return if (toggle.onOptionsItemSelected(item)) {
            true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    private fun changeScreen(newState: Screens) {
        if (screenState != newState) {
            when (screenState) {
                Screens.HOME -> binding.groupHomeScreen.visibility = View.GONE
                Screens.NOTES -> binding.groupNotesScreen.visibility = View.GONE
                Screens.TASKS -> binding.groupTasksScreen.visibility = View.GONE
                Screens.GROUPS -> binding.groupGroupsScreen.visibility = View.GONE
                Screens.HISTOY -> binding.groupHistoryScreen.visibility = View.GONE
            }
            screenState = newState
            when (newState) {
                Screens.HOME -> binding.groupHomeScreen.visibility = View.VISIBLE
                Screens.NOTES -> binding.groupNotesScreen.visibility = View.VISIBLE
                Screens.TASKS -> binding.groupTasksScreen.visibility = View.VISIBLE
                Screens.GROUPS -> binding.groupGroupsScreen.visibility = View.VISIBLE
                Screens.HISTOY -> binding.groupHistoryScreen.visibility = View.VISIBLE
            }


        }


}
