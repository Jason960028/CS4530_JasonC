package com.example.a2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a2.data.AppDatabase
import com.example.a2.data.CourseRepository
import com.example.a2.ui.screens.AddCourseDialog
import com.example.a2.ui.screens.CourseDetailsScreen
import com.example.a2.ui.screens.CourseListScreen
import com.example.a2.ui.theme.A2Theme
import com.example.a2.viewmodel.CourseViewModel
import com.example.a2.viewmodel.CourseViewModelFactory

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
fun CourseManagerApp() {
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val repository = CourseRepository.getInstance(database.courseDao())
    val viewModelFactory = CourseViewModelFactory(repository)
    val viewModel: CourseViewModel = viewModel(factory = viewModelFactory)

    val courses by viewModel.courses.collectAsState()
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