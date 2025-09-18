package com.example.a2

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCourseDialog(
    onDismiss: () -> Unit,
    onAddCourse: (String, String, String) -> Unit
) {
    var department by remember { mutableStateOf("") }
    var courseNumber by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    val isValid = department.isNotBlank() && courseNumber.isNotBlank() && location.isNotBlank()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Course") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = department,
                    onValueChange = { department = it },
                    label = { Text("Department") },
                    placeholder = { Text("e.g., CS") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = courseNumber,
                    onValueChange = { courseNumber = it },
                    label = { Text("Course Number") },
                    placeholder = { Text("e.g., 4530") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location") },
                    placeholder = { Text("e.g., Room 123") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (isValid) {
                        onAddCourse(department.trim(), courseNumber.trim(), location.trim())
                        onDismiss()
                    }
                },
                enabled = isValid
            ) {
                Text("Add Course")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}