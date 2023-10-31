package com.example.tdone.dataclasses

import java.io.Serializable
import java.time.LocalDate

data class TaskDataClass(
    var taskName: String,
    var taskTags: List<TagDataClass> = listOf(),
    var taskGroup: GroupDataClass? = null,
    var taskVinculation: NoteDataClass? = null,
    var hasVinculation: Boolean = false,
    var taskEndDate: LocalDate? = null,
    var taskCompleted: Boolean = false
): Serializable