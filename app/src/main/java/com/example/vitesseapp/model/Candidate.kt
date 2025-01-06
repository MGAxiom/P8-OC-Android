package com.example.vitesseapp.model

data class Candidate(
    val name: String,
    val description: String,
    val favorite: Boolean,
    val imageResId: Int,
    val phone: Int,
    val email: String,
    val expectedSalary: Int,
    val notes: String
)
