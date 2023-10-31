package com.example.tdone.dataclasses

import java.io.Serializable

data class NoteDataClass(
    val noteTittle: String,
    var noteBody: String,
    val noteVinculation: TaskDataClass? = null,
    var hasVinculation: Boolean = false,
    var noteTags: List<TagDataClass> = listOf(),
    var noteFront: ImageDataClass? = null, //investigar referencia al archivo de las imagenes
    var noteBackground: Int
): Serializable
