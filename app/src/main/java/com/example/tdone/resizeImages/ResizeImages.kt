package com.example.tdone.resizeImages

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.tdone.R
import com.example.tdone.databinding.ActivityResizeImagesBinding


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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK) {
            val imageUri = data?.data
            imageUri?.let {
                val inputStream = contentResolver.openInputStream(it)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()

                val resizedBitmap = viewModel.resizeImage(bitmap, targetWidth, targetHeight)

                binding.imageView.setImageBitmap(resizedBitmap)
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 100
        private const val targetWidth = 200
        private const val targetHeight = 200
    }
}