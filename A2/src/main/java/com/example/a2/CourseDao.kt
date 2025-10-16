/**
 * This File handles Data Access Object
 * Using Flow that allow the UI automatically updates without manual refresh calls when insert or delete course.
 */
package com.example.a2

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao //Data Access Object
interface CourseDao {
    @Query("SELECT * FROM courses")
    fun getAllCourses(): Flow<List<Course>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(course: Course)

    @Delete
    suspend fun delete(course: Course)

    @Query("DELETE FROM courses")
    suspend fun deleteAll()
}
