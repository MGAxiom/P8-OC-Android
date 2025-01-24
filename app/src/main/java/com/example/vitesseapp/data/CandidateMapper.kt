package com.example.vitesseapp.data

import com.example.vitesseapp.data.models.Candidate
import com.example.vitesseapp.domain.CandidateEntity

fun Candidate.toEntity() = CandidateEntity(
    id = id,
    name = name,
    birthday = birthday,
    favorite = favorite,
    imageResUri = imageResUri,
    phone = phone,
    email = email,
    expectedSalary = expectedSalary,
    notes = notes
)

fun CandidateEntity.toDomainModel() = Candidate(
    id = id,
    name = name,
    birthday = birthday,
    favorite = favorite,
    imageResUri = imageResUri,
    phone = phone,
    email = email,
    expectedSalary = expectedSalary,
    notes = notes
)