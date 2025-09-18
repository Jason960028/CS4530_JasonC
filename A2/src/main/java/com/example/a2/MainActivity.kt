package com.example.a2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a2.ui.theme.A2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            A2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CourseManagerApp()
                }
            }
        }
    }
}

@Composable
fun CourseManagerApp(
    viewModel: CourseViewModel = viewModel()
) {
    val courses by viewModel.courses
    val selectedCourse by viewModel.selectedCourse
    val showAddCourseDialog by viewModel.showAddCourseDialog

    when {
        selectedCourse != null -> {
            CourseDetailsScreen(
                course = selectedCourse!!,
                onBackClick = { viewModel.clearSelectedCourse() },
                onDeleteClick = { course ->
                    viewModel.deleteCourse(course)
                    viewModel.clearSelectedCourse()
                }
            )
        }
        else -> {
            CourseListScreen(
                courses = courses,
                onCourseClick = { course -> viewModel.selectCourse(course) },
                onAddCourseClick = { viewModel.showAddCourseDialog() }
            )
        }
    }

    if (showAddCourseDialog) {
        AddCourseDialog(
            onDismiss = { viewModel.hideAddCourseDialog() },
            onAddCourse = { department, courseNumber, location ->
                viewModel.addCourse(department, courseNumber, location)
                viewModel.hideAddCourseDialog()
            }
        )
    }
}