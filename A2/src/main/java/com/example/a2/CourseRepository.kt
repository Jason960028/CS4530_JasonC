/**
 * This file acts as the middle layer between ViewModel and Room database
 *
 *
 */

package com.example.a2

import kotlinx.coroutines.flow.Flow

class CourseRepository private constructor(private val courseDao: CourseDao) {

    val allCourses: Flow<List<Course>> = courseDao.getAllCourses()

    suspend fun insert(course: Course) {
        courseDao.insert(course)
    }

    suspend fun delete(course: Course) {
        courseDao.delete(course)
    }

    suspend fun deleteAll() {
        courseDao.deleteAll()
    }

    companion object {
        @Volatile
        private var INSTANCE: CourseRepository? = null

        fun getInstance(courseDao: CourseDao): CourseRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = CourseRepository(courseDao)
                INSTANCE = instance
                instance
            }
        }
    }
}
