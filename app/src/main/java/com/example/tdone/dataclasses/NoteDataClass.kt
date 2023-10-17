package com.example.tdone.dataclasses

data class NoteDataClass(
    val name:String,
    var content:String,
    val TaskDataClass: TaskDataClass?,
    var vinculed: Boolean,
    var TagDataClass: List<TagDataClass>,
    var imagen: String?, //investigar referencia al archivo de las imagenes
    var color: Int
    )
