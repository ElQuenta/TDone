package com.example.tdone.calendar

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tdone.databinding.ActivityCalendarBinding
import com.example.tdone.databinding.ActivityCreateTaskBinding
import com.example.tdone.notification.NotificationHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.sql.Date
import java.util.Locale

class Calendar : AppCompatActivity() {
    private lateinit var binding: ActivityCreateTaskBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var notificationHelper: NotificationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().getReference("Calendar")
        auth = FirebaseAuth.getInstance()

        // Inicializando notificationHelper
        notificationHelper = NotificationHelper(this)


        val calendarView = binding.calendarView
        val editText = binding.editTextText



        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val formattedDate = String.format("%04d%02d%02d", year, month + 1, dayOfMonth)
            updateEditTextWithDatabaseValue(formattedDate, editText)

        }
    }

    //actualizar cualquier cosa, en este caso actualiza el edit text con el string que se guardo en la data base
    private fun updateEditTextWithDatabaseValue(date: String, editText: EditText) {
        //uid user
        val uid = auth.currentUser?.uid
        if (uid != null) {
            val userCalendarReference = databaseReference.child(uid).child(date)
            userCalendarReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.value?.toString() ?: "null"
                    editText.setText(value)
                }

                override fun onCancelled(error: DatabaseError) {
                    // TODO error
                }
            })
        }
    }

    fun clickBoton(){
        val selectedDate = binding.calendarView.date
        val formattedDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date(selectedDate))
        val eventText = binding.editTextText.text.toString()

        val uid = auth.currentUser?.uid
        if (uid != null) {
            val userCalendarReference = databaseReference.child(uid).child(formattedDate)
            userCalendarReference.setValue(eventText)
            Toast.makeText(this, "Event saved successfully", Toast.LENGTH_SHORT).show()

            // Envía una notificación cuando se guarda el evento
            notificationHelper.showNotification("Título de la Notificación", "Clickeaste el botón")
        } else {
            // Handle the case where user is not authenticated
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
        }
    }
}
