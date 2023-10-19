package com.example.tdone

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.tdone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
}