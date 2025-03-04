package com.example.vitesseapp.data.models

data class Candidate(
    val id: Int? = null,
    val name: String,
    val birthday: Long,
    val favorite: Boolean,
    val imageResUri: String,
    val phone: Int,
    val email: String,
    val expectedSalary: Int,
    val notes: String
)
