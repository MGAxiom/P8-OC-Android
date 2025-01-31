package com.example.vitesseapp.domain

import android.net.Uri
import com.example.vitesseapp.R
import com.example.vitesseapp.data.models.Candidate
import com.example.vitesseapp.data.dao.CandidateDao
import com.example.vitesseapp.data.toDomainModel
import com.example.vitesseapp.data.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CandidateRepository(private val candidateDao: CandidateDao) {

    suspend fun insertInitialCandidates() {
        val placeholderImageUri =
            Uri.parse("android.resource://com.example.vitesseapp/${R.drawable.avatar_gris_placeholder}")
        val placeholderImageString = placeholderImageUri.toString()

        val candidates = listOf(
            Candidate(
                name = "John Doe",
                birthday = System.currentTimeMillis(),
                favorite = false,
                imageResUri = placeholderImageString,
                phone = 123456789,
                email = "john@example.com",
                expectedSalary = 75000,
                notes = "Experienced in Kotlin"
            ),
            Candidate(
                name = "Alice Johnson",
                birthday = System.currentTimeMillis(),
                favorite = true,
                imageResUri = placeholderImageString,
                phone = 555555555,
                email = "alice@example.com",
                expectedSalary = 90000,
                notes = "Certified PMP"
            ),
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

    suspend fun insertCandidate(candidate: Candidate): Long {
        return candidateDao.insertCandidate(candidate.toEntity())
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

    suspend fun searchCandidates(query: String): Flow<List<Candidate>> {
        return candidateDao.searchCandidates(query).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    suspend fun searchFavouriteCandidates(query: String): Flow<List<Candidate>> {
        return candidateDao.searchFavoriteCandidates(query).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    fun getAllFavoriteCandidates(): Flow<List<Candidate>> {
        return candidateDao.getFavoriteCandidates().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
}
