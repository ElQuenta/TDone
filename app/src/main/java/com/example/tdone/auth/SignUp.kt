package com.example.tdone.auth


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tdone.R
import com.example.tdone.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.tvLogIn.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }
        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()
            val username = binding.userNameEt.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty() && username.isNotEmpty()) {
                if (pass == confirmPass) {

                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val user = firebaseAuth.currentUser
                            user?.sendEmailVerification()?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    validateUser(username, user)
                                }
                            }
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }
                } else {
                    Toast.makeText(this, R.string.not_matching, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this,R.string.empty_fields, Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun validateUser(username: String, user: FirebaseUser) {

        Toast.makeText(this,R.string.verify_email, Toast.LENGTH_SHORT).show()
        val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(username).build()
        user.updateProfile(profileUpdates).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Handler().postDelayed({
                    user.reload().addOnCompleteListener { reloadTask ->
                        if (reloadTask.isSuccessful) {
                            if (user.isEmailVerified) {
                                val intent = Intent(this, SignIn::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this,R.string.not_verify, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }, 10000)
            }
        }


    }
}