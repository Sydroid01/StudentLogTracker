package com.casa.studentlogtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.casa.studentlogtracker.ui.screens.DashboardScreen
import com.casa.studentlogtracker.ui.screens.LoginScreen
import com.casa.studentlogtracker.ui.theme.StudentLogTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentLogTrackerTheme {
                StudentLogTrackerApp()
            }
        }
    }
}

@Composable
fun StudentLogTrackerApp(
    viewModel: StudentViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    if (!state.isLoggedIn) {
        LoginScreen(
            state = state,
            onUsernameChange = viewModel::updateLoginUsername,
            onPasswordChange = viewModel::updateLoginPassword,
            onLoginClick = viewModel::login,
            modifier = Modifier.fillMaxSize()
        )
    } else {
        DashboardScreen(
            state = state,
            onTabSelected = viewModel::selectTab,
            onLogoutClick = viewModel::logout,
            onStudentNoChange = viewModel::updateStudentNo,
            onFirstNameChange = viewModel::updateFirstName,
            onLastNameChange = viewModel::updateLastName,
            onMiddleNameChange = viewModel::updateMiddleName,
            onProgramChange = viewModel::updateProgram,
            onSpecializationChange = viewModel::updateSpecialization,
            onYearLevelChange = viewModel::updateYearLevel,
            onSaveStudentClick = viewModel::saveStudent,
            onClearFormClick = viewModel::clearForm,
            onSearchQueryChange = viewModel::updateSearchQuery,
            onEditStudent = viewModel::startEditStudent,
            onDeleteStudent = viewModel::confirmDeleteStudent,
            onAddNewClick = {
                viewModel.clearForm()
                viewModel.selectTab(DashboardTab.ADD_STUDENT)
            },
            onDismissDialog = viewModel::dismissDialog,
            onConfirmDelete = viewModel::executeDeleteStudent,
            modifier = Modifier.fillMaxSize()
        )
    }
}
