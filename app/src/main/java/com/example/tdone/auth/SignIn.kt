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

        // Inicializar la instancia de FirebaseAuth.
        firebaseAuth = FirebaseAuth.getInstance()

        // Configuramos un listener para el enlace a la pantalla de registro.
        binding.tvSingIn.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        // Configuramos un listener para el botón de inicio de sesión.
        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                // Intentamos iniciar sesión con el correo electrónico y la contraseña proporcionados.
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // El inicio de sesión fue exitoso.
                        userVerified(true)
                    } else {
                        // El inicio de sesión falló, mostrar un mensaje de error.
                        Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                // Los campos de correo electrónico y contraseña están vacíos.
                Toast.makeText(this, R.string.empty_fields, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        // Comprobamos si el usuario ya ha iniciado sesión.
        if (firebaseAuth.currentUser != null) {
            userVerified(true)
        }
    }

    // Función para verificar la autenticación del usuario y su estado de verificación de correo.
    fun userVerified(firstTime: Boolean) {
        val user = firebaseAuth.currentUser

        // Recargamos el estado del usuario para asegurarse de obtener la información más reciente
        user!!.reload().addOnCompleteListener { reloadTask ->
            if (reloadTask.isSuccessful) {
                if (user.isEmailVerified) {
                    // El usuario ha verificado su correo electrónico, lo redirigimos a la pantalla principal.
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    // El usuario no ha verificado su correo electrónico, mostrar un mensaje.
                    val message = R.string.not_verify
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

                    if (firstTime) {
                        // Enviamos un correo de verificación y establecemos un temporizador para verificar.
                        user.sendEmailVerification().addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val message2 = R.string.time_verify
                                Toast.makeText(this, message2, Toast.LENGTH_SHORT).show()

                                // Esperamos 15 segundos antes de volver a verificar.
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
