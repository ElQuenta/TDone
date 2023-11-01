package com.example.tdone.dataclasses

import java.io.Serializable

data class TaskDataClass(
    var taskName: String,
    var taskTags: List<TagDataClass> = listOf(),
    var taskGroup: GroupDataClass? = null,
    var taskVinculation: NoteDataClass? = null,
    var hasVinculation: Boolean = false,
    var taskCompleted: Boolean = false,
    var taskEndDate: Long? = null,
    var taskEndDateString: String? = null
): Serializable