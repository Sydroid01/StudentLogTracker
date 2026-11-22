package com.casa.studentlogtracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.casa.studentlogtracker.DashboardTab
import com.casa.studentlogtracker.StudentUiState
import com.casa.studentlogtracker.data.Student
import com.casa.studentlogtracker.ui.components.AeroBackground
import com.casa.studentlogtracker.ui.components.AeroMessageBox
import com.casa.studentlogtracker.ui.components.AeroWindow
import com.casa.studentlogtracker.ui.theme.AeroTextDark
import com.casa.studentlogtracker.ui.theme.AeroTextMuted

@Composable
fun DashboardScreen(
    state: StudentUiState,
    onTabSelected: (DashboardTab) -> Unit,
    onLogoutClick: () -> Unit,
    // Form actions
    onStudentNoChange: (String) -> Unit,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onMiddleNameChange: (String) -> Unit,
    onProgramChange: (String) -> Unit,
    onSpecializationChange: (String) -> Unit,
    onYearLevelChange: (String) -> Unit,
    onSaveStudentClick: () -> Unit,
    onClearFormClick: () -> Unit,
    // List actions
    onSearchQueryChange: (String) -> Unit,
    onEditStudent: (Student) -> Unit,
    onDeleteStudent: (Student) -> Unit,
    onAddNewClick: () -> Unit,
    // Dialog actions
    onDismissDialog: () -> Unit,
    onConfirmDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tabScrollState = rememberScrollState()

    AeroBackground(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 16.dp)
        ) {
            AeroWindow(
                title = "Student Info System - Windows Aero",
                icon = Icons.Default.School,
                onCloseClick = onLogoutClick,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    // Top User Header & Logout Bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = null,
                                tint = Color(0xFF1B3B5A),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "User: ${state.loggedInUser.ifEmpty { "Admin" }}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = AeroTextDark
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(3.dp))
                                .background(Color(0xFFFFEBEE))
                                .border(1.dp, Color(0xFFEF9A9A), RoundedCornerShape(3.dp))
                                .clickable { onLogoutClick() }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.ExitToApp,
                                    contentDescription = "Log Out",
                                    tint = Color(0xFFD32F2F),
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Log Out",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFD32F2F)
                                )
                            }
                        }
                    }

                    // Vista Navigation Tab Bar (Scrollable for compact screens)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color(0xFFF2F7FD), Color(0xFFD3E4F5))
                                )
                            )
                            .border(1.dp, Color(0xFFB0C4DE), RoundedCornerShape(4.dp))
                            .padding(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(tabScrollState),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AeroTabButton(
                                label = "Directory",
                                icon = Icons.Default.ListAlt,
                                isSelected = state.activeTab == DashboardTab.DIRECTORY,
                                onClick = { onTabSelected(DashboardTab.DIRECTORY) }
                            )

                            AeroTabButton(
                                label = if (state.editingStudentId != null) "Edit Student" else "Register Student",
                                icon = Icons.Default.Add,
                                isSelected = state.activeTab == DashboardTab.ADD_STUDENT,
                                onClick = { onTabSelected(DashboardTab.ADD_STUDENT) }
                            )

                            AeroTabButton(
                                label = "Academic Info",
                                icon = Icons.Default.Assessment,
                                isSelected = state.activeTab == DashboardTab.ACADEMIC_INFO,
                                onClick = { onTabSelected(DashboardTab.ACADEMIC_INFO) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Active Tab Screen Content
                    Box(modifier = Modifier.weight(1f)) {
                        when (state.activeTab) {
                            DashboardTab.DIRECTORY -> {
                                StudentListScreen(
                                    state = state,
                                    onSearchQueryChange = onSearchQueryChange,
                                    onEditStudent = onEditStudent,
                                    onDeleteStudent = onDeleteStudent,
                                    onAddNewClick = onAddNewClick
                                )
                            }
                            DashboardTab.ADD_STUDENT -> {
                                StudentFormScreen(
                                    state = state,
                                    onStudentNoChange = onStudentNoChange,
                                    onFirstNameChange = onFirstNameChange,
                                    onLastNameChange = onLastNameChange,
                                    onMiddleNameChange = onMiddleNameChange,
                                    onProgramChange = onProgramChange,
                                    onSpecializationChange = onSpecializationChange,
                                    onYearLevelChange = onYearLevelChange,
                                    onSaveClick = onSaveStudentClick,
                                    onClearClick = onClearFormClick
                                )
                            }
                            DashboardTab.ACADEMIC_INFO -> {
                                AcademicInfoScreen(state = state)
                            }
                        }
                    }
                }
            }

            // Dialog popup (if active)
            if (state.showDialog) {
                AeroMessageBox(
                    title = state.dialogTitle,
                    message = state.dialogMessage,
                    type = state.dialogType,
                    onDismiss = onDismissDialog,
                    onConfirm = {
                        if (state.studentToDelete != null) {
                            onConfirmDelete()
                        }
                    },
                    cancelText = if (state.studentToDelete != null) "Cancel" else null,
                    onCancel = onDismissDialog
                )
            }
        }
    }
}

@Composable
private fun AeroTabButton(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgBrush = if (isSelected) {
        Brush.verticalGradient(
            colors = listOf(Color(0xFFFFFFFF), Color(0xFFE0EEFA))
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(Color(0x60FFFFFF), Color(0x20FFFFFF))
        )
    }

    val borderColor = if (isSelected) Color(0xFF3892D6) else Color.Transparent

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(3.dp))
            .background(bgBrush)
            .border(1.2.dp, borderColor, RoundedCornerShape(3.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 7.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) Color(0xFF0078D7) else AeroTextMuted,
                modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color(0xFF1B3B5A) else AeroTextDark
            )
        }
    }
}
