package com.example.tdone.dataclasses

import java.io.Serializable

data class GroupDataClass(

    var groupName:String,
    var groupDescription: String,
    var groupSelected: Boolean = false
): Serializable

