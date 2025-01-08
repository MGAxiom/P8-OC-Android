package com.example.vitesseapp.domain

import com.example.vitesseapp.data.models.Candidate
import com.example.vitesseapp.data.dao.CandidateDao

class CandidateRepository(private val candidateDao: CandidateDao) {
    suspend fun getAllCandidates(): List<Candidate> {
        return candidateDao.getAllCandidates()
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