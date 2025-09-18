package com.example.a2

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel

class CourseViewModel : ViewModel() {
    private val _courses = mutableStateOf<List<Course>>(emptyList())
    val courses: State<List<Course>> = _courses

    private val _selectedCourse = mutableStateOf<Course?>(null)
    val selectedCourse: State<Course?> = _selectedCourse

    private val _showAddCourseDialog = mutableStateOf(false)
    val showAddCourseDialog: State<Boolean> = _showAddCourseDialog

    fun addCourse(department: String, courseNumber: String, location: String) {
        val newCourse = Course(
            department = department,
            courseNumber = courseNumber,
            location = location
        )
        _courses.value = _courses.value + newCourse
    }

    fun deleteCourse(course: Course) {
        _courses.value = _courses.value.filter { it.id != course.id }
        if (_selectedCourse.value?.id == course.id) {
            _selectedCourse.value = null
        }
    }

    fun selectCourse(course: Course) {
        _selectedCourse.value = course
    }

    fun clearSelectedCourse() {
        _selectedCourse.value = null
    }

    fun showAddCourseDialog() {
        _showAddCourseDialog.value = true
    }

    fun hideAddCourseDialog() {
        _showAddCourseDialog.value = false
    }
}