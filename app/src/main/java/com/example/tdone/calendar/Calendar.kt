package com.example.tdone.calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.EditText
import com.example.tdone.databinding.ActivityCalendarBinding
import com.example.tdone.databinding.ActivityMainBinding
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseReference

class Calendar : AppCompatActivity() {
    private lateinit var binding: ActivityCalendarBinding
    private lateinit var stringDateSelected: String
    private lateinit var databaseReference: DatabaseReference
    private lateinit var calendarView: CalendarView
    private lateinit var editText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        calendarView = binding.calendarView
        editText = binding.editTextText

        databaseReference = FirebaseDatabase.getInstance().getReference("Calendar")

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            stringDateSelected = "$year${month + 1}$dayOfMonth"
            calendarClicked()
        }
    }

    private fun calendarClicked() {
        databaseReference.child(stringDateSelected).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value
                if (value != null) {
                    editText.setText(value.toString())
                } else {
                    editText.setText("null")
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun buttonSaveEvent(view: View) {
        databaseReference.child(stringDateSelected).setValue(editText.text.toString())
    }
}
