package com.example.vitesseapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.vitesseapp.data.models.Candidate
import com.example.vitesseapp.domain.CandidateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CandidateDao {
    @Query("SELECT * FROM candidates")
    fun getAllCandidates(): Flow<List<CandidateEntity>>

    @Query("SELECT * FROM candidates WHERE favorite = true")
    fun getFavoriteCandidates(): Flow<List<CandidateEntity>>

    @Query("SELECT * FROM candidates WHERE id = :id")
    suspend fun getCandidateById(id: Int): CandidateEntity?

    @Insert
    suspend fun insertCandidate(candidate: CandidateEntity): Long

    @Update
    suspend fun updateCandidate(candidate: CandidateEntity)

    @Delete
    suspend fun deleteCandidate(candidate: CandidateEntity)

    @Query("SELECT * FROM candidates WHERE LOWER(name) LIKE '%' || LOWER(:searchQuery) || '%'")
    fun searchCandidates(searchQuery: String): Flow<List<CandidateEntity>>

    @Query("SELECT * FROM candidates WHERE LOWER(name) LIKE '%' || LOWER(:searchQuery) || '%' AND favorite = 1")
    fun searchFavoriteCandidates(searchQuery: String): Flow<List<CandidateEntity>>

}