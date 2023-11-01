package com.example.tdone.createElements

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tdone.MainActivity
import com.example.tdone.auth.SignUp
import com.example.tdone.databinding.ActivityCreateGroupBinding
import com.example.tdone.dataclasses.GroupDataClass
import com.example.tdone.dataclasses.NoteDataClass
import com.example.tdone.dataclasses.TagDataClass
import com.example.tdone.dataclasses.TaskDataClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CreateGroupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateGroupBinding

    private lateinit var groups:MutableList<GroupDataClass>
    private val db = FirebaseFirestore.getInstance()
    private val user = FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initValues()
        initListeners()
    }
    private fun initValues() {
        db.collection(SignUp.KEY_USER).document(user?.email!!).get().addOnSuccessListener {
            groups = it.get(SignUp.KEY_ALL_GROUPS) as MutableList<GroupDataClass>
        }
    }
    private fun initListeners() {
        binding.icCancel.setOnClickListener { onBackPressed() }
        binding.btnAdd.setOnClickListener {
            val titleGroup = binding.etGroupName.text.toString()
            if (titleGroup != null) {
                var newGroup = GroupDataClass(
                    groupName = titleGroup,
                    groupDescription = binding.etGroupDescription.text.toString()
                )
                groups.add(newGroup)
                val changes = hashMapOf<String,Any>(SignUp.KEY_ALL_GROUPS to groups)
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
}