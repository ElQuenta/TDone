package com.example.tdone.dataclasses

import java.io.Serializable
import java.time.LocalDate

data class TaskDataClass(
    var name: String,
    var tag_DataClasses: List<TagDataClass> = listOf(),
    var group: GroupDataClass? = null,
    var NoteDataClass: NoteDataClass? = null,
    var vinculed: Boolean = false,
    var date: LocalDate? = null,
    var checked: Boolean = false
): Serializable