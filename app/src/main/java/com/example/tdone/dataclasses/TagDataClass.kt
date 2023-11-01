package com.example.tdone.dataclasses

import java.io.Serializable


data class TagDataClass(
    var tagName:String,
    var tagColor: Int
): Serializable
