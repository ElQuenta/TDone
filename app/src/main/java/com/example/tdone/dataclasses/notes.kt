package com.example.tdone.dataclasses

data class notes(
    val name:String,
    var content:String,
    val task: task?,
    var vinculed: Boolean,
    var tag: List<tag>, //TODO la verdad no se si una nota deberia tener prioridad
    var imagen: String?, //Todo referencia al archivo de las imagenes
    var color: Int
    )
