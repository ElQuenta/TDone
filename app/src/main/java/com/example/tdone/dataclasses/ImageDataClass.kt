package com.example.tdone.dataclasses

import android.net.Uri
import java.io.Serializable

data class ImageDataClass(
    val refImage: Int=0,
    var isReferenceImage: Boolean = true,
    var selected: Boolean = false,
    var uriImage: Uri? = null
): Serializable
