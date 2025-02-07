package com.example.vitesseapp.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "candidates")
data class CandidateEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = 0,
    val name: String,
    val birthday: Long,
    val favorite: Boolean,
    val imageResUri: String,
    val phone: Int,
    val email: String,
    val expectedSalary: Int,
    val notes: String
)