package com.example.a2

data class Course(
    val id: Long = System.currentTimeMillis(),
    val department: String,
    val courseNumber: String,
    val location: String
) {
    fun getDisplayName(): String = "$department $courseNumber"
}