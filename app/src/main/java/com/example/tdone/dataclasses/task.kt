package com.example.tdone.dataclasses
import java.time.LocalDate

data class task(
    val name:String,
    var tag: List<tag>,
    val group: group,
    val notes: notes,
    var vinculed: Boolean,
    var date: LocalDate
)