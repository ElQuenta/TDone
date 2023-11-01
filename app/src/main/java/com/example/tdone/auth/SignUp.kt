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

        // Inicializamos la instancia de FirebaseAuth.
        firebaseAuth = FirebaseAuth.getInstance()

        // Configuramos un listener para el enlace a la pantalla de inicio de sesión.
        binding.tvLogIn.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }

        // Configurar un listener para el botón de registro.
        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()
            val username = binding.userNameEt.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty() && username.isNotEmpty()) {
                if (pass == confirmPass) {

                    // Creamos un usuario con correo electrónico y contraseña proporcionados.
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = firebaseAuth.currentUser

                            // Enviamos un correo de verificación al usuario recién registrado.
                            user?.sendEmailVerification()?.addOnCompleteListener { sendEmailTask ->
                                if (sendEmailTask.isSuccessful) {
                                    // Si el correo se envía correctamente, validamos al usuario.
                                    validateUser(username, user)
                                }
                            }
                        } else {
                            // El registro de usuario no tuvo éxito, mostramos un mensaje de error.
                            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    // Las contraseñas no coinciden, mostramos otro mensaje de error.
                    Toast.makeText(this, R.string.not_matching, Toast.LENGTH_SHORT).show()
                }
            } else {
                // Los campos de correo electrónico, contraseña, confirmación de contraseña y nombre de usuario están vacíos, mostrar mensaje de error.
                Toast.makeText(this, R.string.empty_fields, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateUser(username: String, user: FirebaseUser) {
        // Mostramos un mensaje de verificación de correo electrónico.
        Toast.makeText(this, R.string.verify_email, Toast.LENGTH_SHORT).show()

        // Actualizamos el perfil del usuario con el nombre de usuario proporcionado.
        val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(username).build()
        user.updateProfile(profileUpdates).addOnCompleteListener { updateProfileTask ->
            if (updateProfileTask.isSuccessful) {
                Handler().postDelayed({
                    user.reload().addOnCompleteListener { reloadTask ->
                        if (reloadTask.isSuccessful) {
                            if (user.isEmailVerified) {
                                // Redirigimos al usuario a la pantalla de inicio de sesión si el correo electrónico está verificado.
                                val intent = Intent(this, SignIn::class.java)
                                startActivity(intent)
                            } else {
                                // El correo electrónico no está verificado, mostramos un mensaje.
                                Toast.makeText(this, R.string.not_verify, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }, 10000)
            }
        }
    }
}
