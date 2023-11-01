package com.example.tdone.dataclasses

import com.example.tdone.R
import com.google.firebase.auth.FirebaseUser

data class RecoverUserInfoDataClass(
    val user: FirebaseUser,
    val allNotes: List<NoteDataClass> = listOf(
        NoteDataClass(
            noteTittle = R.string.welcom_user_title.toString(),
            noteBody = R.string.welcom_user_body.toString(),
            noteBackground = R.color.background_note_color2
        )
    ),
    val allTasks: List<TaskDataClass> = listOf(),
    val allGroups: List<GroupDataClass> = listOf(),
    val allTags: List<TagDataClass> = listOf()
)
