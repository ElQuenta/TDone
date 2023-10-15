package com.example.tdone.dataclasses

data class notes(
    val name:String,
    var content:String,
    val task: task,
    var vinculed: Boolean,
    var tag: List<tag> //TODO la verdad no se si una nota deberia tener prioridad
    )
