package com.example.tdone.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tdone.MainActivity
import com.example.tdone.R
import com.example.tdone.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignIn : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()
        binding.tvSingIn.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        userVerified(true)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(this, R.string.empty_fields, Toast.LENGTH_SHORT).show()

            }
        }
    }


    override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser != null) {
            userVerified(true)
        }
    }

    fun userVerified(firstTime: Boolean) {
        val user = firebaseAuth.currentUser
        user!!.reload().addOnCompleteListener { reloadTask ->
            if (reloadTask.isSuccessful) {
                if (user.isEmailVerified) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    val message = R.string.not_verify
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    if (firstTime) {
                        user.sendEmailVerification()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val message2 = R.string.time_verify
                                    Toast.makeText(this, message2, Toast.LENGTH_SHORT).show()
                                    Handler().postDelayed({
                                        userVerified(false)
                                    }, 15000)
                                }
                            }
                    }
                }
            }
        }
    }
}