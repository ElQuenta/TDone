package com.example.tdone

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tdone.databinding.ActivityMainBinding
import com.example.tdone.dataclasses.GroupDataClass
import com.example.tdone.dataclasses.NoteDataClass
import com.example.tdone.dataclasses.TagDataClass
import com.example.tdone.dataclasses.TaskDataClass
import com.example.tdone.rvHoldersYAdapters.rvInitialScreen.initialScreenNotes.InitialScreenNotesAdapter
import com.example.tdone.rvHoldersYAdapters.rvInitialScreen.initialScreenTasks.InitialScreenTasksAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var currentNotesAdapter: InitialScreenNotesAdapter

    private val notes = listOf<NoteDataClass>(
        NoteDataClass(
            name = "Prueba 1",
            content = "Prueba",
            color = R.color.background_note_color2
        ),
        NoteDataClass(
            name = "Prueba 2",
            content = "Prueba",
            color = R.color.background_note_color3
        ),
        NoteDataClass(
            name = "Prueba 3",
            content = "Prueba",
            color = R.color.background_note_color4
        ),
        NoteDataClass(
            name = "Prueba 4",
            content = "Prueba",
            color = R.color.background_note_color5
        ),
        NoteDataClass(
            name = "Prueba 5",
            content = "Prueba",
            color = R.color.background_note_color1
        )
    )

    private val group1 = GroupDataClass(
        name = "Grupo Prueba1",
        description = "Grupo Prueba1"
    )
    private val group2 = GroupDataClass(
        name = "Grupo Prueba2",
        description = "Grupo Prueba2"
    )

    private val  tag1 = TagDataClass(
        name = "Tag Prueba1",
        color = R.color.tag_color1
    )
    private val  tag2 = TagDataClass(
        name = "Tag Prueba2",
        color = R.color.tag_color2
    )
    private val  tag3 = TagDataClass(
        name = "Tag Prueba3",
        color = R.color.tag_color3
    )
    private val  tag4 = TagDataClass(
        name = "Tag Prueba4",
        color = R.color.tag_color4
    )

    private lateinit var nearToEndTaskAdapter: InitialScreenTasksAdapter

    private val nearToEndTasks = listOf<TaskDataClass>(
        TaskDataClass(
            name = "Tarea Prueba 1",
            tag_DataClasses = listOf(tag1),
            group = group1
        ),
        TaskDataClass(
            name = "Tarea Prueba 2",
            tag_DataClasses = listOf(tag2),
            group = group2
        ),
        TaskDataClass(
            name = "Tarea Prueba 3",
            tag_DataClasses = listOf(tag3),
            group = group1
        ),
        TaskDataClass(
            name = "Tarea Prueba 4",
            tag_DataClasses = listOf(tag1,tag2),
            group = group1
        )
        ,TaskDataClass(
            name = "Tarea Prueba 5",
            tag_DataClasses = listOf(tag3,tag4),
            group = group2
        ),
        TaskDataClass(
            name = "Tarea Prueba 6",
            group = group1
        ),
        TaskDataClass(
            name = "Tarea Prueba 7",
        )
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()


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

                        drawerLayout.closeDrawer(GravityCompat.START)
                    }

                    R.id.mis -> {
                        Toast.makeText(this@MainActivity, "MIS GRUPOS", Toast.LENGTH_SHORT).show()
                    }

                    R.id.tod -> {
                        Toast.makeText(this@MainActivity, "TODAS LAS TAREAS", Toast.LENGTH_SHORT)
                            .show()
                    }

                    R.id.not -> {
                        Toast.makeText(this@MainActivity, "MIS NOTAS", Toast.LENGTH_SHORT).show()
                    }

                    R.id.his -> {
                        Toast.makeText(this@MainActivity, "HISTORIAL", Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }


        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return if (toggle.onOptionsItemSelected(item)) {
            true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }
    private fun initUi() {
        currentNotesAdapter = InitialScreenNotesAdapter(notes)
        binding.rvCurrentNotes.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCurrentNotes.adapter = currentNotesAdapter

        nearToEndTaskAdapter = InitialScreenTasksAdapter(nearToEndTasks)
        binding.rvNearToEndTasks.layoutManager = LinearLayoutManager(this)
        binding.rvNearToEndTasks.adapter = nearToEndTaskAdapter
    }
}
