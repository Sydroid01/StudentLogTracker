package com.casa.studentlogtracker.data

import java.util.UUID

data class Student(
    val id: String = UUID.randomUUID().toString(),
    val studentNo: String,
    val firstName: String,
    val lastName: String,
    val middleName: String = "",
    val program: String,
    val specialization: String = "",
    val yearLevel: String
) {
    val fullName: String
        get() = if (middleName.isNotBlank()) {
            "$lastName, $firstName ${middleName.first().uppercaseChar()}."
        } else {
            "$lastName, $firstName"
        }

    val displayProgram: String
        get() = if (specialization.isNotBlank()) {
            "$program ($specialization)"
        } else {
            program
        }
}
