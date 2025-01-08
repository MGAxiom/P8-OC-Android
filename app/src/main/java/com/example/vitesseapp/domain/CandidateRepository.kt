package com.example.vitesseapp.domain

import com.example.vitesseapp.R
import com.example.vitesseapp.data.models.Candidate
import com.example.vitesseapp.data.dao.CandidateDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

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
            candidateDao.insertCandidate(candidate)
        }
    }

    suspend fun getAllCandidates(): StateFlow<List<Candidate>> {
        return candidateDao.getAllCandidates().stateIn(
            scope = CoroutineScope(Dispatchers.IO),
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )
    }

    suspend fun insertCandidate(candidate: Candidate) {
        candidateDao.insertCandidate(candidate)
    }

    suspend fun updateCandidate(candidate: Candidate) {
        candidateDao.updateCandidate(candidate)
    }

    suspend fun deleteCandidate(candidate: Candidate) {
        candidateDao.deleteCandidate(candidate)
    }

    suspend fun getCandidateById(id: Int): Candidate? {
        return candidateDao.getCandidateById(id)
    }

    suspend fun searchCandidates(query: String): List<Candidate> {
        return candidateDao.searchCandidates("%$query%")
    }
}