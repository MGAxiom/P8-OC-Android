package com.example.vitesseapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vitesseapp.data.models.Candidate
import com.example.vitesseapp.data.dao.CandidateDao
import com.example.vitesseapp.domain.CandidateEntity

@Database(entities = [CandidateEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun candidateDao(): CandidateDao

    companion object {
        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "your_database_name"
            ).build()
        }
    }
}
