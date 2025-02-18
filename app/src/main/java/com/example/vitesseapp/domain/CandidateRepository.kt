package com.example.vitesseapp.domain

import com.example.vitesseapp.data.models.Candidate
import com.example.vitesseapp.data.dao.CandidateDao
import com.example.vitesseapp.data.toDomainModel
import com.example.vitesseapp.data.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CandidateRepository(private val candidateDao: CandidateDao) {

    fun getAllCandidates(): Flow<List<Candidate>> {
        return candidateDao.getAllCandidates().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    suspend fun insertOrUpdateCandidate(candidate: Candidate): Long {
        return candidateDao.insertCandidateOrUpdate(candidate.toEntity())
    }

    suspend fun toggleFavoriteStatus(candidate: Candidate) {
        candidate.id?.let { candidateDao.toggleFavoriteStatus(it) }
    }

    suspend fun deleteCandidate(candidate: Candidate) {
        candidateDao.deleteCandidate(candidate.toEntity())
    }

    suspend fun getCandidateById(id: Int): Candidate? {
        return candidateDao.getCandidateById(id)?.toDomainModel()
    }

    fun searchCandidates(query: String): Flow<List<Candidate>> {
        return candidateDao.searchCandidates(query).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    fun searchFavouriteCandidates(query: String): Flow<List<Candidate>> {
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
