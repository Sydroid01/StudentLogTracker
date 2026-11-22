package com.casa.studentlogtracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.casa.studentlogtracker.StudentUiState
import com.casa.studentlogtracker.ui.components.AeroButton
import com.casa.studentlogtracker.ui.components.AeroButtonStyle
import com.casa.studentlogtracker.ui.components.AeroDropdown
import com.casa.studentlogtracker.ui.components.AeroTextField
import com.casa.studentlogtracker.ui.theme.AeroTextDark
import com.casa.studentlogtracker.ui.theme.AeroTextMuted

val standardPrograms = listOf(
    "BS Computer Science",
    "BS Information Technology",
    "BS Information Systems",
    "BS Computer Engineering",
    "BS Business Administration",
    "BS Accountancy",
    "BS Nursing",
    "BA Communication"
)

val yearLevels = listOf(
    "1st Year",
    "2nd Year",
    "3rd Year",
    "4th Year"
)

@Composable
fun StudentFormScreen(
    state: StudentUiState,
    onStudentNoChange: (String) -> Unit,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onMiddleNameChange: (String) -> Unit,
    onProgramChange: (String) -> Unit,
    onSpecializationChange: (String) -> Unit,
    onYearLevelChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(12.dp)
    ) {
        // Section Header: Student Identification
        FormSectionHeader(title = "1. Student Identification")

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .background(Color(0xFBFDFF))
                .border(1.dp, Color(0xFFD0DCE5), RoundedCornerShape(6.dp))
                .padding(12.dp)
        ) {
            AeroTextField(
                value = state.studentNoInput,
                onValueChange = onStudentNoChange,
                label = "Student No.",
                placeholder = "e.g. 2025-00123",
                icon = Icons.Default.Badge,
                errorMessage = state.formError?.takeIf { it.contains("Student No") }
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Section Header: Student Name Details
        FormSectionHeader(title = "2. Student Name")

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .background(Color(0xFBFDFF))
                .border(1.dp, Color(0xFFD0DCE5), RoundedCornerShape(6.dp))
                .padding(12.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                AeroTextField(
                    value = state.lastNameInput,
                    onValueChange = onLastNameChange,
                    label = "Lastname:",
                    placeholder = "e.g. Dela Cruz",
                    icon = Icons.Default.Person
                )

                AeroTextField(
                    value = state.firstNameInput,
                    onValueChange = onFirstNameChange,
                    label = "Firstname:",
                    placeholder = "e.g. Juan",
                    icon = Icons.Default.Person
                )

                AeroTextField(
                    value = state.middleNameInput,
                    onValueChange = onMiddleNameChange,
                    label = "Middlename (Optional):",
                    placeholder = "e.g. Santos",
                    icon = Icons.Default.Person
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Section Header: Student Academic Info
        FormSectionHeader(title = "3. Student Academic Info")

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .background(Color(0xFBFDFF))
                .border(1.dp, Color(0xFFD0DCE5), RoundedCornerShape(6.dp))
                .padding(12.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                AeroDropdown(
                    label = "Program:",
                    options = standardPrograms,
                    selectedOption = state.programInput,
                    onOptionSelected = onProgramChange,
                    icon = Icons.Default.School
                )

                AeroTextField(
                    value = state.specializationInput,
                    onValueChange = onSpecializationChange,
                    label = "Specialization (if any...):",
                    placeholder = "e.g. Software Engineering / Data Science",
                    icon = Icons.Default.Star
                )

                AeroDropdown(
                    label = "Year Level:",
                    options = yearLevels,
                    selectedOption = state.yearLevelInput,
                    onOptionSelected = onYearLevelChange,
                    icon = Icons.Default.DateRange
                )
            }
        }

        if (state.formError != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFFFDE8E8))
                    .border(1.dp, Color(0xFFF8B4B4), RoundedCornerShape(4.dp))
                    .padding(10.dp)
            ) {
                Text(
                    text = state.formError,
                    color = Color(0xFF9B1C1C),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bottom Form Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            AeroButton(
                text = "Clear",
                onClick = onClearClick,
                style = AeroButtonStyle.Secondary,
                icon = Icons.Default.Clear
            )

            Spacer(modifier = Modifier.width(8.dp))

            AeroButton(
                text = if (state.editingStudentId != null) "Update Record" else "Save Student Record",
                onClick = onSaveClick,
                style = AeroButtonStyle.Primary,
                icon = Icons.Default.Check
            )
        }
    }
}

@Composable
private fun FormSectionHeader(title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(14.dp)
                .background(Color(0xFF3892D6))
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = title,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = AeroTextDark
        )
    }
}
