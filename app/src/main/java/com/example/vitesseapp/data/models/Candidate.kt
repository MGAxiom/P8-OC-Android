package com.example.vitesseapp.data.models


data class Candidate(
    val id: Int = 0,
    val name: String,
    val description: String,
    val favorite: Boolean,
    val imageResId: Int,
    val phone: Int,
    val email: String,
    val expectedSalary: Int,
    val notes: String
)
