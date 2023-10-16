package com.example.tdone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.ImageView



class MainActivity : AppCompatActivity() {
    companion object {
        val IMAGE_CODE = 100
    }

    private lateinit var imageView: ImageView
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imgGalery)
        button = findViewById(R.id.btnCamera)

        button.setOnClickListener() {
            pickImage()
        }
    }
    fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMAGE_CODE && resultCode == RESULT_OK){
            imageView.setImageURI(data?.data)
        }
    }
}