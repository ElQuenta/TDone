package com.example.tdone

import android.content.Intent
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
        HOME, NOTES, TASKS, GROUPS, HISTORY;

        fun navMessage(): String = when (this) {
            HOME -> "Inicio😀"
            NOTES -> "Notas"
            TASKS -> "Tareas"
            GROUPS -> "Grupos"
            HISTORY -> "Historal"
        }
    }

    companion object {
        const val KEY_TASK = "key_task"
        const val KEY_NOTE = "key_note"
        const val KEY_GROUP = "key_group"
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


    private val notes = listOf<NoteDataClass>(
        NoteDataClass(
            name = "Prueba 1",
            content = "En un mundo cada vez más conectado y globalizado, la comunicación efectiva se ha convertido en una habilidad esencial. Ya sea en el ámbito personal o profesional, la capacidad de expresar ideas de manera clara y coherente es fundamental para establecer relaciones significativas y alcanzar el éxito.\n\nEn el contexto actual, la tecnología desempeña un papel crucial en nuestras vidas. La omnipresencia de los dispositivos electrónicos y la disponibilidad de internet han transformado la forma en que interactuamos con el mundo que nos rodea. Estamos constantemente expuestos a una avalancha de información, lo que nos brinda la oportunidad de aprender y crecer, pero también nos desafía a discernir entre lo relevante y lo superfluo.\n\nEn este escenario, la educación se erige como el pilar fundamental sobre el cual se construye el futuro. Las instituciones educativas tienen la responsabilidad de preparar a las generaciones venideras para enfrentar los desafíos del siglo XXI. Esto implica no solo impartir conocimientos académicos, sino también fomentar habilidades como el pensamiento crítico, la creatividad y la colaboración. Los educadores desempeñan un papel vital al guiar y apoyar a los estudiantes en su viaje de aprendizaje.\n\nAdemás, la conciencia sobre cuestiones medioambientales y sociales está en aumento. La sostenibilidad se ha convertido en un tema central en las agendas de gobiernos, empresas y ciudadanos. La necesidad de preservar nuestro planeta para las generaciones futuras impulsa iniciativas en áreas como la energía renovable, la conservación de recursos y la reducción de emisiones de carbono.\n\nEn conclusión, vivimos en una era emocionante y desafiante, llena de oportunidades y responsabilidades. Es crucial que nos adaptemos a los cambios, aprendamos de las experiencias pasadas y trabajemos juntos para construir un futuro mejor y más equitativo para todos.",
            color = R.color.background_note_color2
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
        prepearingBurgerMenu()
    }

    private fun initUi() {
        /*
        Screen Home
         */

        currentNotesAdapter = BaseNotesAdapter(notes) { note ->
            navigateNote(note)
        }
        binding.rvCurrentNotes.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCurrentNotes.adapter = currentNotesAdapter

        nearToEndTaskAdapter = BaseTasksAdapter(nearToEndTasks) { task ->
            navigateTask(task)
        }
        binding.rvNearToEndTasks.layoutManager = LinearLayoutManager(this)
        binding.rvNearToEndTasks.adapter = nearToEndTaskAdapter

        /*
        Screen Group
         */

        allGroupsAdapter = BaseGroupsAdapter(groups){ group ->
            navigateGroup(group)
        }
        binding.rvAllGroups.layoutManager = LinearLayoutManager(this)
        binding.rvAllGroups.adapter = allGroupsAdapter

        /*
        Screen Tasks
         */

        allTasksAdapter = BaseTasksAdapter(allTasks) { task ->
            navigateTask(task)
        }
        binding.rvAllTasks.layoutManager = LinearLayoutManager(this)
        binding.rvAllTasks.adapter = allTasksAdapter

        /*
        Screen Notes
         */

        allNotesAdapter = BaseNotesAdapter(allNotes) { note ->
            navigateNote(note)
        }
        binding.rvAllNotes.layoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        binding.rvAllNotes.adapter = allNotesAdapter

        /*
        Screen History
         */
        historyAdapter = BaseTasksAdapter(history) { task ->
            navigateTask(task)
        }
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

                    R.id.item_nav_home -> {
                        changeScreen(Screens.HOME)
                        Toast.makeText(
                            this@MainActivity,
                            screenState.navMessage(),
                            Toast.LENGTH_SHORT
                        ).show()
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }

                    R.id.item_nav_groups -> {
                        changeScreen(Screens.GROUPS)
                        Toast.makeText(
                            this@MainActivity,
                            screenState.navMessage(),
                            Toast.LENGTH_SHORT
                        ).show()
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }

                    R.id.item_nav_tasks -> {
                        changeScreen(Screens.TASKS)
                        Toast.makeText(
                            this@MainActivity,
                            screenState.navMessage(),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }

                    R.id.item_nav_notes -> {
                        changeScreen(Screens.NOTES)
                        Toast.makeText(
                            this@MainActivity,
                            screenState.navMessage(),
                            Toast.LENGTH_SHORT
                        ).show()
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }

                    R.id.item_nav_history -> {
                        changeScreen(Screens.HISTORY)
                        Toast.makeText(
                            this@MainActivity,
                            screenState.navMessage(),
                            Toast.LENGTH_SHORT
                        ).show()
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
            editButton.visibility = View.GONE
            groupButton.visibility = View.GONE
            noteButton.visibility = View.GONE
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
                Screens.HISTORY -> binding.groupHistoryScreen.visibility = View.GONE
            }
            screenState = newState
            when (newState) {
                Screens.HOME -> binding.groupHomeScreen.visibility = View.VISIBLE
                Screens.NOTES -> binding.groupNotesScreen.visibility = View.VISIBLE
                Screens.TASKS -> binding.groupTasksScreen.visibility = View.VISIBLE
                Screens.GROUPS -> binding.groupGroupsScreen.visibility = View.VISIBLE
                Screens.HISTORY -> binding.groupHistoryScreen.visibility = View.VISIBLE
            }
        }
    }

    private fun navigateTask(task: TaskDataClass) {
        val intent = Intent(this, TaskViewActivity::class.java)
        intent.putExtra(KEY_TASK, task)
        startActivity(intent)
    }

    private fun navigateNote(note: NoteDataClass) {
        val intent = Intent(this, NoteViewActivity::class.java)
        intent.putExtra(KEY_NOTE, note)
        startActivity(intent)
    }

    private fun navigateGroup(group: GroupDataClass) {
        val intent = Intent(this,GroupViewActivity::class.java)
        intent.putExtra(KEY_GROUP,group)
        startActivity(intent)
    }

}
