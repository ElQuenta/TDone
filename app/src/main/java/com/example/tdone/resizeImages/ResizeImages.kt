package com.example.tdone.resizeImages

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.tdone.R
import com.example.tdone.databinding.ActivityResizeImagesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream


class ResizeImages : AppCompatActivity() {

    private lateinit var binding: ActivityResizeImagesBinding
    private val viewModel: ResizeImagesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_resize_images)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    fun onPickImageClick(view: View) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
    }


    //manejar el resultado de la seleccion de las imagenes
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK) { //verificar si la imagen se subio de la manera correcta
            //se obtiene el uri de la data
            val imageUri = data?.data
            //si el uri no es nulo
            imageUri?.let {
                //leer los datos en forma binaria, es un flujo de entrada
                val inputStream = contentResolver.openInputStream(it)

                val originalBitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()

                // Redimensionar la imagen utilizando el ViewModel
                val resizedBitmap = viewModel.resizeImage(originalBitmap, targetWidth, targetHeight)

                // Subir la imagen redimensionada a Firebase Storage
                val currentUser = FirebaseAuth.getInstance().currentUser
                val userUid = currentUser?.uid
                val storage = FirebaseStorage.getInstance()
                val storageRef = storage.reference
                val imagesRef = storageRef.child("images/$userUid/${System.currentTimeMillis()}.jpg")

                val baos = ByteArrayOutputStream()
                //comprime el bitmap
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                //array de bytes del baos
                val data = baos.toByteArray()

                val uploadTask = imagesRef.putBytes(data)
                //si al subida es exitosa se ejecuta el codigo
                uploadTask.addOnSuccessListener {
                    // La imagen se ha subido exitosamente
                    // Puedes obtener la URL de descarga de la imagen
                    imagesRef.downloadUrl.addOnSuccessListener { uri ->
                        val downloadUrl = uri.toString()

                        // Guarda la URL en la base de datos junto con el UID del usuario
                        val database = FirebaseDatabase.getInstance()
                        val userImagesRef = database.getReference("user_images").child(userUid!!)
                        userImagesRef.push().setValue(downloadUrl)

                        // Asigna la URL de descarga al viewModel
                        viewModel.imageUri.set(downloadUrl)
                    }
                }.addOnFailureListener {
                    // Ocurrió un error al subir la imagen
                }
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 100
        private const val targetWidth = 320
        private const val targetHeight = 320
    }
}