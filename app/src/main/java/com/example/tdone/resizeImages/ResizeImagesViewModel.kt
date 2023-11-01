package com.example.tdone.resizeImages

import android.graphics.Bitmap
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class ResizeImagesViewModel : ViewModel() {
    val imageUri = ObservableField<String>()
    fun resizeImage(bitmap: Bitmap, targetWidth: Int, targetHeight: Int): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true)
    }
}