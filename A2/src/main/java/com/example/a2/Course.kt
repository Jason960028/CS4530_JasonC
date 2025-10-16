/**
 * Assignment 2 Change List
 * Converted Course to Room @Entity with table name "courses"
 * Make ID to auto-generate with @PrimaryKey(autoGenerate = true)
 */

package com.example.a2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class Course(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val department: String,
    val courseNumber: String,
    val location: String
) {
    fun getDisplayName(): String = "$department $courseNumber"
}