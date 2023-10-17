package com.example.tdone.dataclasses

import java.time.LocalDate

data class TaskDataClass(
    var name: String,
    var tag_DataClasses: List<TagDataClass>,
    var group: GroupDataClass,
    var NoteDataClass: NoteDataClass,
    var vinculed: Boolean,
    var date: LocalDate?,
    var checked: Boolean
)