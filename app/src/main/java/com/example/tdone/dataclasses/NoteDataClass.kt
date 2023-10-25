package com.example.tdone.dataclasses

import java.io.Serializable

data class NoteDataClass(
    val name: String,
    var content: String,
    val TaskDataClass: TaskDataClass? = null,
    var vinculed: Boolean = false,
    var TagDataClass: List<TagDataClass>? = listOf(),
    var imagen: Int? = null, //investigar referencia al archivo de las imagenes
    var color: Int
): Serializable
