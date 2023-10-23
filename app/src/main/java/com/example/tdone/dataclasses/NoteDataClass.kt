package com.example.tdone.dataclasses

data class NoteDataClass(
    val name: String,
    var content: String,
    val TaskDataClass: TaskDataClass? = null,
    var vinculed: Boolean = false,
    var TagDataClass: List<TagDataClass>? = listOf(),
    var imagen: String? = null, //investigar referencia al archivo de las imagenes
    var color: Int
)
