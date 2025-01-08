package com.example.vitesseapp.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.vitesseapp.data.models.Candidate
import kotlinx.coroutines.flow.Flow

interface CandidateDao {
    @Query("SELECT * FROM candidates")
    fun getAllCandidates(): Flow<List<Candidate>>

    @Query("SELECT * FROM candidates WHERE id = :id")
    fun getCandidateById(id: Int): Candidate?

    @Insert
    suspend fun insertCandidate(candidate: Candidate)

    @Update
    suspend fun updateCandidate(candidate: Candidate)

    @Delete
    suspend fun deleteCandidate(candidate: Candidate)

    @Query("SELECT * FROM candidates WHERE name LIKE :searchQuery OR description LIKE :searchQuery")
    suspend fun searchCandidates(searchQuery: String): List<Candidate>
}