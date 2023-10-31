package com.example.tdone

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tdone.auth.SignIn
import com.example.tdone.createElements.CreateGroupActivity
import com.example.tdone.createElements.CreateNoteActivity
import com.example.tdone.createElements.CreateTaskActivity
import com.example.tdone.databinding.ActivityMainBinding
import com.example.tdone.dataclasses.GroupDataClass
import com.example.tdone.dataclasses.NoteDataClass
import com.example.tdone.dataclasses.TagDataClass
import com.example.tdone.dataclasses.TaskDataClass
import com.example.tdone.rvHoldersYAdapters.rvBase.baseGroups.BaseGroupsAdapter
import com.example.tdone.rvHoldersYAdapters.rvBase.baseNotes.BaseNotesAdapter
import com.example.tdone.rvHoldersYAdapters.rvBase.baseTasks.BaseTasksAdapter
import com.example.tdone.viewIntoElements.GroupViewActivity
import com.example.tdone.viewIntoElements.NoteViewActivity
import com.example.tdone.viewIntoElements.TaskViewActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.io.ByteArrayOutputStream
import java.util.Locale


class MainActivity : AppCompatActivity() {
    override fun onBackPressed() {
        if (isExpanded) {
            shrinkfab()
        } else {
            super.onBackPressed()
            finishAffinity()
        }
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private var user: FirebaseUser? = null

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

    private var screenState = Screens.HOME
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var fabAddNote: FloatingActionButton
    private lateinit var fabAddTask: FloatingActionButton
    private lateinit var fabAddGroup: FloatingActionButton

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

    private val notes = listOf<NoteDataClass>(
        NoteDataClass(
            noteTittle = "Prueba 1",
            noteBody = "En un mundo cada vez más conectado y globalizado, la comunicación efectiva se ha convertido en una habilidad esencial. Ya sea en el ámbito personal o profesional, la capacidad de expresar ideas de manera clara y coherente es fundamental para establecer relaciones significativas y alcanzar el éxito.\n\nEn el contexto actual, la tecnología desempeña un papel crucial en nuestras vidas. La omnipresencia de los dispositivos electrónicos y la disponibilidad de internet han transformado la forma en que interactuamos con el mundo que nos rodea. Estamos constantemente expuestos a una avalancha de información, lo que nos brinda la oportunidad de aprender y crecer, pero también nos desafía a discernir entre lo relevante y lo superfluo.\n\nEn este escenario, la educación se erige como el pilar fundamental sobre el cual se construye el futuro. Las instituciones educativas tienen la responsabilidad de preparar a las generaciones venideras para enfrentar los desafíos del siglo XXI. Esto implica no solo impartir conocimientos académicos, sino también fomentar habilidades como el pensamiento crítico, la creatividad y la colaboración. Los educadores desempeñan un papel vital al guiar y apoyar a los estudiantes en su viaje de aprendizaje.\n\nAdemás, la conciencia sobre cuestiones medioambientales y sociales está en aumento. La sostenibilidad se ha convertido en un tema central en las agendas de gobiernos, empresas y ciudadanos. La necesidad de preservar nuestro planeta para las generaciones futuras impulsa iniciativas en áreas como la energía renovable, la conservación de recursos y la reducción de emisiones de carbono.\n\nEn conclusión, vivimos en una era emocionante y desafiante, llena de oportunidades y responsabilidades. Es crucial que nos adaptemos a los cambios, aprendamos de las experiencias pasadas y trabajemos juntos para construir un futuro mejor y más equitativo para todos.",
            noteBackground = R.color.background_note_color2
        ), NoteDataClass(
            noteTittle = "Prueba 2", noteBody = "Prueba", noteBackground = R.color.background_note_color3
        ), NoteDataClass(
            noteTittle = "Prueba 3", noteBody = "Prueba", noteBackground = R.color.background_note_color4
        ), NoteDataClass(
            noteTittle = "Prueba 4", noteBody = "Prueba", noteBackground = R.color.background_note_color5
        ), NoteDataClass(
            noteTittle = "Prueba 5", noteBody = "Prueba", noteBackground = R.color.background_note_color1
        )
    )

    private val groups = listOf<GroupDataClass>(
        GroupDataClass(
            groupName = "Grupo Prueba1",
            groupDescription = "Grupo Prueba1"
        ), GroupDataClass(
            groupName = "Grupo Prueba2",
            groupDescription = "Grupo Prueba2"
        ), GroupDataClass(
            groupName = "Grupo Prueba1",
            groupDescription = "Grupo Prueba1"
        ), GroupDataClass(
            groupName = "Grupo Prueba2",
            groupDescription = "Grupo Prueba2"
        ), GroupDataClass(
            groupName = "Grupo Prueba1",
            groupDescription = "Grupo Prueba1"
        ), GroupDataClass(
            groupName = "Grupo Prueba2",
            groupDescription = "Grupo Prueba2"
        ), GroupDataClass(
            groupName = "Grupo Prueba1",
            groupDescription = "Grupo Prueba1"
        ), GroupDataClass(
            groupName = "Grupo Prueba2",
            groupDescription = "Grupo Prueba2"
        )
    )

    private val tags = listOf<TagDataClass>(
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
        )
    )

    private val nearToEndTasks = listOf<TaskDataClass>(
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
        )
    )

    private val allNotes = mutableListOf<NoteDataClass>(
        NoteDataClass(
            noteTittle = "Prueba 1", noteBody = "Prueba", noteBackground = R.color.background_note_color2
        ), NoteDataClass(
            noteTittle = "Prueba 2", noteBody = "Prueba", noteBackground = R.color.background_note_color3
        ), NoteDataClass(
            noteTittle = "Prueba 3", noteBody = "Prueba", noteBackground = R.color.background_note_color4
        ), NoteDataClass(
            noteTittle = "Prueba 4", noteBody = "Prueba", noteBackground = R.color.background_note_color5
        ), NoteDataClass(
            noteTittle = "Prueba 5", noteBody = "Prueba", noteBackground = R.color.background_note_color1
        ), NoteDataClass(
            noteTittle = "Prueba 1", noteBody = "Prueba", noteBackground = R.color.background_note_color2
        ), NoteDataClass(
            noteTittle = "Prueba 2", noteBody = "Prueba", noteBackground = R.color.background_note_color3
        ), NoteDataClass(
            noteTittle = "Prueba 3", noteBody = "Prueba", noteBackground = R.color.background_note_color4
        ), NoteDataClass(
            noteTittle = "Prueba 4", noteBody = "Prueba", noteBackground = R.color.background_note_color5
        ), NoteDataClass(
            noteTittle = "Prueba 5", noteBody = "Prueba", noteBackground = R.color.background_note_color1
        ), NoteDataClass(
            noteTittle = "Prueba 1", noteBody = "Prueba", noteBackground = R.color.background_note_color2
        ), NoteDataClass(
            noteTittle = "Prueba 2", noteBody = "Prueba", noteBackground = R.color.background_note_color3
        ), NoteDataClass(
            noteTittle = "Prueba 3", noteBody = "Prueba", noteBackground = R.color.background_note_color4
        ), NoteDataClass(
            noteTittle = "Prueba 4", noteBody = "Prueba", noteBackground = R.color.background_note_color5
        ), NoteDataClass(
            noteTittle = "Prueba 5", noteBody = "Prueba", noteBackground = R.color.background_note_color1
        ), NoteDataClass(
            noteTittle = "Prueba 1", noteBody = "Prueba", noteBackground = R.color.background_note_color2
        ), NoteDataClass(
            noteTittle = "Prueba 2", noteBody = "Prueba", noteBackground = R.color.background_note_color3
        ), NoteDataClass(
            noteTittle = "Prueba 3", noteBody = "Prueba", noteBackground = R.color.background_note_color4
        ), NoteDataClass(
            noteTittle = "Prueba 4", noteBody = "Prueba", noteBackground = R.color.background_note_color5
        ), NoteDataClass(
            noteTittle = "Prueba 5", noteBody = "Prueba", noteBackground = R.color.background_note_color1
        )
    )

    private val allTasks = mutableListOf<TaskDataClass>(
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
            taskName = "Tarea Prueba 7",
        ), TaskDataClass(
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
            taskName = "Tarea Prueba 7",
        ), TaskDataClass(
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
            taskName = "Tarea Prueba 7",
        ), TaskDataClass(
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
            taskName = "Tarea Prueba 7",
        )
    )

    private val history = mutableListOf<TaskDataClass>(
        TaskDataClass(
            taskName = "Tarea Prueba 1",
            taskTags = listOf(tags[0]),
            taskGroup = groups[0],
            taskCompleted = true
        ), TaskDataClass(
            taskName = "Tarea Prueba 2",
            taskTags = listOf(tags[1]),
            taskGroup = groups[1],
            taskCompleted = true
        ), TaskDataClass(
            taskName = "Tarea Prueba 3",
            taskTags = listOf(tags[2]),
            taskGroup = groups[0],
            taskCompleted = true
        ), TaskDataClass(
            taskName = "Tarea Prueba 4",
            taskTags = listOf(tags[0], tags[1]),
            taskGroup = groups[0],
            taskCompleted = true
        ), TaskDataClass(
            taskName = "Tarea Prueba 5",
            taskTags = listOf(tags[2], tags[3]),
            taskGroup = groups[1],
            taskCompleted = true
        ), TaskDataClass(
            taskName = "Tarea Prueba 6",
            taskGroup = groups[0],
            taskCompleted = true
        ), TaskDataClass(
            taskName = "Tarea Prueba 7",
            taskCompleted = true
        )
    )





    private fun saveImageToSharedPreferences(bitmap: Bitmap) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("MisPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        editor.putString("imagen_de_perfil", Base64.encodeToString(byteArray, Base64.DEFAULT))
        editor.apply()
    }

    // Recuperar la imagen de SharedPreferences
    private fun retrieveImageFromSharedPreferences(): Bitmap? {
        val sharedPreferences: SharedPreferences = getSharedPreferences("MisPrefs", Context.MODE_PRIVATE)
        val encodedImage = sharedPreferences.getString("imagen_de_perfil", null)

        if (encodedImage != null) {
            val byteArray = Base64.decode(encodedImage, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }

        return null
    }










    private val PICK_IMAGE_REQUEST = 1
    private var isExpanded = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = FirebaseAuth.getInstance().currentUser

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val headerView = navigationView.getHeaderView(0) // 0 es el primer encabezado en NavigationView
        val headerImageView = headerView.findViewById<ImageView>(R.id.iv_profile)

        // Recuperar la imagen de perfil y establecerla en el ImageView
        val imagenDePerfil = retrieveImageFromSharedPreferences()
        if (imagenDePerfil != null) {
            headerImageView.setImageBitmap(imagenDePerfil)
        }

        // Establecer un listener de clic en el ImageView para seleccionar una nueva foto
        headerImageView.setOnClickListener {
            pickPhoto(it)
        }

        initUi()
        initListeners()
    }


//        user = FirebaseAuth.getInstance().currentUser
//
//        val navigationView = findViewById<NavigationView>(R.id.nav_view)
//
//        // Buscar el ImageView dentro del encabezado
//        val headerView = navigationView.getHeaderView(0) // 0 es el primer encabezado en la NavigationView
//        val headerImageView = headerView.findViewById<ImageView>(R.id.iv_profile)
//
//        // Configura un click listener en el ImageView del encabezado
//        headerImageView.setOnClickListener {
//            pickPhoto(it)
//        }
//        initUi()
//        initListeners()
//        }

    private fun shrinkfab(){

        binding.transparent.visibility = View.GONE
        binding.fabAdd.startAnimation(rotateClose)
        binding.fabAddNote.startAnimation(toBottom)
        binding.fabAddTask.startAnimation(toBottom)
        binding.fabAddGroup.startAnimation(toBottom)

        isExpanded = !isExpanded
    }

    private fun expandFab(){

        binding.transparent.visibility = View.VISIBLE
        binding.fabAdd.startAnimation(rotateOpen)
        binding.fabAddNote.startAnimation(fromBottom)
        binding.fabAddTask.startAnimation(fromBottom)
        binding.fabAddGroup.startAnimation(fromBottom)

        isExpanded = !isExpanded
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

        if(ev?.action == MotionEvent.ACTION_DOWN){

            if(isExpanded){
                val outRect = Rect()
                binding.favContaint.getGlobalVisibleRect(outRect)

                if(!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())){
                    shrinkfab()
                }
            }
        }

        return super.dispatchTouchEvent(ev)
    }

    fun pickPhoto(view: View) {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            val selectedImage = data?.data

            if (selectedImage != null) {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)

                // Guardar la imagen seleccionada en SharedPreferences
                saveImageToSharedPreferences(bitmap)

                // Mostrar la imagen seleccionada en el ImageView
                val navigationView = findViewById<NavigationView>(R.id.nav_view)
                val headerView = navigationView.getHeaderView(0)
                val headerImageView = headerView.findViewById<ImageView>(R.id.iv_profile)

                headerImageView.setImageBitmap(bitmap)
            }
        }
    }





//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
//            val selectedImage = data?.data
//
//            // Mostrar la imagen seleccionada en el ImageView del encabezado
//            if (selectedImage != null) {
//                val navigationView = findViewById<NavigationView>(R.id.nav_view)
//                val headerView = navigationView.getHeaderView(0)
//                val headerImageView = headerView.findViewById<ImageView>(R.id.iv_profile)
//
//                // Configurar la imagen seleccionada en el ImageView del encabezado
//                headerImageView.setImageURI(selectedImage)
//            }
//        }
//    }

    private fun initListeners() {
        binding.fabAdd.setOnClickListener{
            if(isExpanded){
                shrinkfab()
            }else{
                expandFab()
            }
        }
        fabAddNote.setOnClickListener {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,CreateNoteActivity::class.java)
            startActivity(intent)
        }
        fabAddTask.setOnClickListener {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,CreateTaskActivity::class.java)
            startActivity(intent)
        }
        fabAddGroup.setOnClickListener {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,CreateGroupActivity::class.java)
            startActivity(intent)
        }
        prepearingBurgerMenu()
    }

    private fun initUi() {
        /*
        Screen Home
         */

        currentNotesAdapter = BaseNotesAdapter(
            notes,
            getSize().getMainScreenWidth(this)
        ) { note ->
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

        allNotesAdapter = BaseNotesAdapter(
            allNotes,
            getSize().getMainScreenWidth(this)
        ) { note ->
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


        fabAdd = binding.fabAdd
        fabAddNote = binding.fabAddNote
        fabAddTask = binding.fabAddTask
        fabAddGroup = binding.fabAddGroup


        binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tv_user_email).text =
            user?.email ?: "email_example@gmail.com"
        binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tv_user_name).text =
            user?.displayName ?: "Usuario"
    }





    private fun setAppLocale(localeCode: String) {
        val locale = Locale(localeCode)
        Locale.setDefault(locale)
        val resources = resources
        val configuration = resources.configuration
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)

        // Guardar la configuración del idioma en SharedPreferences para futuras sesiones
        val prefs = getSharedPreferences("Settings", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("My_Lang", localeCode)
        editor.apply()

        // Reiniciar la actividad para aplicar los cambios de idioma
        val refresh = Intent(this, MainActivity::class.java)
        startActivity(refresh)
        finish()
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
                    R.id.log -> {
                        FirebaseAuth.getInstance().signOut()
                        val intent = Intent(this@MainActivity, SignIn::class.java)
                        startActivity(intent)
                        finish()
                    }R.id.language_english->{
                    setAppLocale("en") // Cambiar a inglés
                    true
                    }R.id.language_spanish->{
                    setAppLocale("es") // Cambiar a español
                    true
                    }
                }
                true
            }


        }


    }


    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            fabAddNote.visibility = View.VISIBLE
            fabAddTask.visibility = View.VISIBLE
            fabAddGroup.visibility = View.VISIBLE

            fabAddNote.setClickable(true)
            fabAddTask.setClickable(true)
            fabAddGroup.setClickable(true)
        } else {
            fabAddNote.visibility = View.GONE
            fabAddTask.visibility = View.GONE
            fabAddGroup.visibility = View.GONE

            fabAddNote.setClickable(false)
            fabAddTask.setClickable(false)
            fabAddGroup.setClickable(false)
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            fabAddNote.startAnimation(fromBottom)
            fabAddTask.startAnimation(fromBottom)
            fabAddGroup.startAnimation(fromBottom)
            fabAdd.startAnimation(rotateOpen)
        } else {
            fabAddNote.startAnimation(toBottom)
            fabAddTask.startAnimation(toBottom)
            fabAddGroup.startAnimation(toBottom)
            fabAdd.startAnimation(rotateClose)
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
        val intent = Intent(this, GroupViewActivity::class.java)
        intent.putExtra(KEY_GROUP,group)
        startActivity(intent)
    }

}

