package com.example.tdone

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tdone.databinding.ActivityViewAllNotesBinding
import com.example.tdone.dataclasses.NoteDataClass
import com.example.tdone.rvHoldersYAdapters.rvInitialScreen.initialScreenNotes.InitialScreenNotesAdapter

class ViewAllNotesActivity : AppCompatActivity() {

    private lateinit var binding : ActivityViewAllNotesBinding
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
        ),NoteDataClass(
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
        ),NoteDataClass(
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAllNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        prepearingBurgerMenu()
        currentNotesAdapter = InitialScreenNotesAdapter(notes)
        binding.rvAllNotes.adapter = currentNotesAdapter
        binding.rvAllNotes.layoutManager =
            GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
    }

    private fun prepearingBurgerMenu() {
        binding.apply {
            navView.bringToFront()

            setSupportActionBar(toolbar)

            toggle = ActionBarDrawerToggle(
                this@ViewAllNotesActivity,
                drawerLayout,
                R.string.nav_open,
                R.string.nav_close
            )


            drawerLayout.addDrawerListener(toggle)

            navView.setNavigationItemSelectedListener {
                when (it.itemId) {

                    R.id.inic -> {
                        Toast.makeText(this@ViewAllNotesActivity, "INICIO", Toast.LENGTH_SHORT).show()
                        val intent = Intent(binding.root.context,MainActivity::class.java)
                        startActivity(intent)
                    }

                    R.id.mis -> {
                        Toast.makeText(this@ViewAllNotesActivity, "MIS GRUPOS", Toast.LENGTH_SHORT).show()
                    }

                    R.id.tod -> {
                        Toast.makeText(this@ViewAllNotesActivity, "TODAS LAS TAREAS", Toast.LENGTH_SHORT)
                            .show()
                    }

                    R.id.not -> {
                        Toast.makeText(this@ViewAllNotesActivity, "MIS NOTAS", Toast.LENGTH_SHORT).show()

                        drawerLayout.closeDrawer(GravityCompat.START)
                    }

                    R.id.his -> {
                        Toast.makeText(this@ViewAllNotesActivity, "HISTORIAL", Toast.LENGTH_SHORT).show()
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
}