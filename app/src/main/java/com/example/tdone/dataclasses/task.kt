package com.example.tdone.dataclasses

import java.time.LocalDate

data class task(
    var name: String,
    var tags: List<tag>,
    var group: group,
    var notes: notes,
    var vinculed: Boolean,
    var date: LocalDate?,
    var checked: Boolean
)