package com.example.tdone.dataclasses

import android.icu.text.SimpleDateFormat
import java.io.Serializable
import java.util.Calendar
import java.util.Locale

data class NoteDataClass(
    val noteTittle: String,
    var noteBody: String,
    var noteVinculation: TaskDataClass? = null,
    var hasVinculation: Boolean = false,
    var noteTags: List<TagDataClass> = listOf(),
    var noteFront: ImageDataClass? = null, //investigar referencia al archivo de las imagenes
    var noteBackground: Int,
    var lastUpdate: Long = SimpleDateFormat(
        "ddMMyyyy",
        Locale.getDefault()
    ).format(Calendar.getInstance().timeInMillis).toLong()
): Serializable
