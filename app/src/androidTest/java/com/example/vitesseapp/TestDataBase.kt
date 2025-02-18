package com.example.vitesseapp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vitesseapp.data.dao.CandidateDao
import com.example.vitesseapp.domain.CandidateEntity

@Database(entities = [CandidateEntity::class], version = 1, exportSchema = false)
abstract class TestDatabase : RoomDatabase() {
    abstract fun candidateDao(): CandidateDao
}