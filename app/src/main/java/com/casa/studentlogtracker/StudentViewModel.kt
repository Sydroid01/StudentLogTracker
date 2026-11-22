package com.casa.studentlogtracker

import androidx.lifecycle.ViewModel
import com.casa.studentlogtracker.data.Student
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class DashboardTab {
    DIRECTORY,
    ADD_STUDENT,
    ACADEMIC_INFO
}

data class StudentUiState(
    val isLoggedIn: Boolean = false,
    val loggedInUser: String = "",
    val loginUsernameInput: String = "",
    val loginPasswordInput: String = "",
    val loginError: String? = null,

    val activeTab: DashboardTab = DashboardTab.DIRECTORY,
    val students: List<Student> = initialSampleStudents(),
    val searchQuery: String = "",

    // Form fields
    val editingStudentId: String? = null,
    val studentNoInput: String = "",
    val firstNameInput: String = "",
    val lastNameInput: String = "",
    val middleNameInput: String = "",
    val programInput: String = "",
    val specializationInput: String = "",
    val yearLevelInput: String = "1st Year",
    val formError: String? = null,

    // Dialog state
    val showDialog: Boolean = false,
    val dialogTitle: String = "",
    val dialogMessage: String = "",
    val dialogType: com.casa.studentlogtracker.ui.components.AeroDialogType = com.casa.studentlogtracker.ui.components.AeroDialogType.Info,
    val studentToDelete: Student? = null
)

class StudentViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(StudentUiState())
    val uiState: StateFlow<StudentUiState> = _uiState.asStateFlow()

    // Login handlers
    fun updateLoginUsername(username: String) {
        _uiState.update { it.copy(loginUsernameInput = username, loginError = null) }
    }

    fun updateLoginPassword(password: String) {
        _uiState.update { it.copy(loginPasswordInput = password, loginError = null) }
    }

    fun login() {
        val username = _uiState.value.loginUsernameInput.trim()
        val password = _uiState.value.loginPasswordInput.trim()

        if (username.isEmpty() || password.isEmpty()) {
            _uiState.update { it.copy(loginError = "Please enter both username and password.") }
            return
        }

        // Allow any valid credentials (or default admin/admin)
        _uiState.update {
            it.copy(
                isLoggedIn = true,
                loggedInUser = username,
                loginError = null
            )
        }
    }

    fun logout() {
        _uiState.update {
            it.copy(
                isLoggedIn = false,
                loggedInUser = "",
                loginUsernameInput = "",
                loginPasswordInput = "",
                loginError = null
            )
        }
    }

    // Navigation
    fun selectTab(tab: DashboardTab) {
        _uiState.update { it.copy(activeTab = tab) }
    }

    // Form input handlers
    fun updateStudentNo(value: String) {
        _uiState.update { it.copy(studentNoInput = value, formError = null) }
    }

    fun updateFirstName(value: String) {
        _uiState.update { it.copy(firstNameInput = value, formError = null) }
    }

    fun updateLastName(value: String) {
        _uiState.update { it.copy(lastNameInput = value, formError = null) }
    }

    fun updateMiddleName(value: String) {
        _uiState.update { it.copy(middleNameInput = value) }
    }

    fun updateProgram(value: String) {
        _uiState.update { it.copy(programInput = value, formError = null) }
    }

    fun updateSpecialization(value: String) {
        _uiState.update { it.copy(specializationInput = value) }
    }

    fun updateYearLevel(value: String) {
        _uiState.update { it.copy(yearLevelInput = value) }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    // Student CRUD
    fun saveStudent() {
        val state = _uiState.value
        val studentNo = state.studentNoInput.trim()
        val firstName = state.firstNameInput.trim()
        val lastName = state.lastNameInput.trim()
        val program = state.programInput.trim()

        if (studentNo.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || program.isEmpty()) {
            _uiState.update {
                it.copy(formError = "Student No, First Name, Last Name, and Program are required.")
            }
            return
        }

        if (state.editingStudentId != null) {
            // Edit existing
            val updatedList = state.students.map {
                if (it.id == state.editingStudentId) {
                    it.copy(
                        studentNo = studentNo,
                        firstName = firstName,
                        lastName = lastName,
                        middleName = state.middleNameInput.trim(),
                        program = program,
                        specialization = state.specializationInput.trim(),
                        yearLevel = state.yearLevelInput
                    )
                } else {
                    it
                }
            }
            _uiState.update {
                it.copy(
                    students = updatedList,
                    activeTab = DashboardTab.DIRECTORY,
                    showDialog = true,
                    dialogTitle = "Student Updated",
                    dialogMessage = "Student record #$studentNo has been updated successfully.",
                    dialogType = com.casa.studentlogtracker.ui.components.AeroDialogType.Success
                )
            }
            clearForm()
        } else {
            // Check for duplicate Student No
            if (state.students.any { it.studentNo.equals(studentNo, ignoreCase = true) }) {
                _uiState.update {
                    it.copy(formError = "Student No '$studentNo' already exists in the system.")
                }
                return
            }

            val newStudent = Student(
                studentNo = studentNo,
                firstName = firstName,
                lastName = lastName,
                middleName = state.middleNameInput.trim(),
                program = program,
                specialization = state.specializationInput.trim(),
                yearLevel = state.yearLevelInput
            )
            _uiState.update {
                it.copy(
                    students = listOf(newStudent) + it.students,
                    activeTab = DashboardTab.DIRECTORY,
                    showDialog = true,
                    dialogTitle = "Student Registered",
                    dialogMessage = "New student $lastName, $firstName (#$studentNo) was added successfully.",
                    dialogType = com.casa.studentlogtracker.ui.components.AeroDialogType.Success
                )
            }
            clearForm()
        }
    }

    fun startEditStudent(student: Student) {
        _uiState.update {
            it.copy(
                editingStudentId = student.id,
                studentNoInput = student.studentNo,
                firstNameInput = student.firstName,
                lastNameInput = student.lastName,
                middleNameInput = student.middleName,
                programInput = student.program,
                specializationInput = student.specialization,
                yearLevelInput = student.yearLevel,
                activeTab = DashboardTab.ADD_STUDENT,
                formError = null
            )
        }
    }

    fun confirmDeleteStudent(student: Student) {
        _uiState.update {
            it.copy(
                studentToDelete = student,
                showDialog = true,
                dialogTitle = "Confirm Delete",
                dialogMessage = "Are you sure you want to delete student '${student.fullName}' (#${student.studentNo})?",
                dialogType = com.casa.studentlogtracker.ui.components.AeroDialogType.Warning
            )
        }
    }

    fun executeDeleteStudent() {
        val target = _uiState.value.studentToDelete ?: return
        _uiState.update {
            it.copy(
                students = it.students.filterNot { s -> s.id == target.id },
                studentToDelete = null,
                showDialog = true,
                dialogTitle = "Student Deleted",
                dialogMessage = "Student record #${target.studentNo} has been removed.",
                dialogType = com.casa.studentlogtracker.ui.components.AeroDialogType.Info
            )
        }
    }

    fun dismissDialog() {
        _uiState.update {
            it.copy(
                showDialog = false,
                studentToDelete = null
            )
        }
    }

    fun clearForm() {
        _uiState.update {
            it.copy(
                editingStudentId = null,
                studentNoInput = "",
                firstNameInput = "",
                lastNameInput = "",
                middleNameInput = "",
                programInput = "",
                specializationInput = "",
                yearLevelInput = "1st Year",
                formError = null
            )
        }
    }
}

private fun initialSampleStudents() = listOf(
    Student(
        studentNo = "2023-01042",
        firstName = "Alexander",
        lastName = "De la Cruz",
        middleName = "Santos",
        program = "BS Computer Science",
        specialization = "Artificial Intelligence",
        yearLevel = "3rd Year"
    ),
    Student(
        studentNo = "2024-00215",
        firstName = "Sophia Marie",
        lastName = "Reyes",
        middleName = "Garces",
        program = "BS Information Technology",
        specialization = "Cybersecurity",
        yearLevel = "2nd Year"
    ),
    Student(
        studentNo = "2022-09411",
        firstName = "Julian",
        lastName = "Mendoza",
        middleName = "Cruz",
        program = "BS Information Systems",
        specialization = "Enterprise Systems",
        yearLevel = "4th Year"
    ),
    Student(
        studentNo = "2025-00109",
        firstName = "Beatriz",
        lastName = "Aquino",
        middleName = "Villanueva",
        program = "BS Computer Engineering",
        specialization = "Embedded Systems",
        yearLevel = "1st Year"
    )
)
