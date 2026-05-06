package com.university.coursemanager

import java.io.Serializable

data class Course(
    val id: String = "",
    val name: String = "",
    val code: String = "",
    val instructor: String = "",
    val credits: Int = 0,
    val schedule: String = "",
    val room: String = "",
    val semester: String = ""
) : Serializable