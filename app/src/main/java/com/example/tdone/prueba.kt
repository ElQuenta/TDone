package com.example.tdone

import android.os.Bundle
import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class prueba : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var notesReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        database = FirebaseDatabase.getInstance()

        // Obtén el UID del usuario actual desde Firebase Authentication
        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.uid ?: ""

        // Verifica si se obtuvo correctamente el UID del usuario
        if (userId.isNotEmpty()) {
            notesReference = database.getReference("users").child(userId).child("notes")

            // Llamar a la función para obtener datos desde Firebase
            fetchDataFromFirebase()

            // Agregar una nueva nota de ejemplo
            val newNote = ContactsContract.CommonDataKinds.Note("Nueva Nota", "Contenido de la nueva nota")
            addNoteToFirebase(newNote)
        } else {
            // Si no se pudo obtener el UID del usuario, maneja el caso según tu lógica de la aplicación
            // Por ejemplo, redirige al usuario a la pantalla de inicio de sesión
            // o muestra un mensaje de error.
        }
    }

    private fun fetchDataFromFirebase() {
        notesReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val notesList = mutableListOf<ContactsContract.CommonDataKinds.Note>()
                for (noteSnapshot in snapshot.children) {
                    val note = noteSnapshot.getValue(ContactsContract.CommonDataKinds.Note::class.java)
                    note?.let { notesList.add(it) }
                }
                // Actualizar la interfaz de usuario con las notas obtenidas
                // Esto puede implicar actualizar un RecyclerView, por ejemplo
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar errores
            }
        })
    }

    private fun addNoteToFirebase(note: ContactsContract.CommonDataKinds.Note) {
        val noteId = notesReference.push().key ?: ""
        notesReference.child(noteId).setValue(note)
    }
}
