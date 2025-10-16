package com.example.a2.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.a2.data.Course
import com.example.a2.data.CourseRepository

class CourseViewModel(private val repository: CourseRepository) : ViewModel() {
    val courses = repository.allCourses.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

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
        viewModelScope.launch {
            repository.insert(newCourse)
        }
    }

    fun deleteCourse(course: Course) {
        viewModelScope.launch {
            repository.delete(course)
        }
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