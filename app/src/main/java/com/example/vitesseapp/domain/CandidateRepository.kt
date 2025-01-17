package com.example.vitesseapp.domain

import com.example.vitesseapp.R
import com.example.vitesseapp.data.models.Candidate
import com.example.vitesseapp.data.dao.CandidateDao
import com.example.vitesseapp.data.toDomainModel
import com.example.vitesseapp.data.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CandidateRepository(private val candidateDao: CandidateDao) {

    suspend fun insertInitialCandidates() {
        val candidates = listOf(
            Candidate(
                name = "John Doe",
                description = "Software Developer",
                favorite = false,
                imageResId = R.drawable.avatar_gris_placeholder,
                phone = 123456789,
                email = "john@example.com",
                expectedSalary = 75000,
                notes = "Experienced in Kotlin"
            ),
            Candidate(
                name = "Jane Smith",
                description = "UX Designer",
                favorite = false,
                imageResId = R.drawable.avatar_gris_placeholder,
                phone = 987654321,
                email = "jane@example.com",
                expectedSalary = 80000,
                notes = "Creative and detail-oriented"
            ),
            Candidate(
                name = "Alice Johnson",
                description = "Project Manager",
                favorite = true,
                imageResId = R.drawable.avatar_gris_placeholder,
                phone = 555555555,
                email = "alice@example.com",
                expectedSalary = 90000,
                notes = "Certified PMP"
            ),
            Candidate(
                name = "Bob Wilson",
                description = "Data Scientist",
                favorite = true,
                imageResId = R.drawable.avatar_gris_placeholder,
                phone = 444444444,
                email = "bob@example.com",
                expectedSalary = 85000,
                notes = "Expert in machine learning"
            )
        )
        candidates.forEach { candidate ->
            candidateDao.insertCandidate(candidate.toEntity())
        }
    }

    fun getAllCandidates(): Flow<List<Candidate>> {
        return candidateDao.getAllCandidates().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    suspend fun insertCandidate(candidate: Candidate) {
        candidateDao.insertCandidate(candidate.toEntity())
    }

    suspend fun updateCandidate(candidate: Candidate) {
        candidateDao.updateCandidate(candidate.toEntity())
    }

    suspend fun deleteCandidate(candidate: Candidate) {
        candidateDao.deleteCandidate(candidate.toEntity())
    }

    suspend fun getCandidateById(id: Int): Candidate? {
        return candidateDao.getCandidateById(id)?.toDomainModel()
    }

    suspend fun searchCandidates(query: String): List<Candidate> {
        return candidateDao.searchCandidates(query).map { it.toDomainModel() }
    }

    fun getAllFavoriteCandidates(): Flow<List<Candidate>> {
        return candidateDao.getFavoriteCandidates().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
}