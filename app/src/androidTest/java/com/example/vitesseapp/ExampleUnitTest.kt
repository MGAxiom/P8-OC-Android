package com.example.vitesseapp

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.vitesseapp.data.dao.CandidateDao
import com.example.vitesseapp.data.models.Candidate
import com.example.vitesseapp.domain.CandidateRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleUnitTest {
    private lateinit var candidateDao: CandidateDao
    private lateinit var database: TestDatabase
    private lateinit var candidateRepository: CandidateRepository

    private val classicCandidate = Candidate(
        id = 1,
        name = "John Doe",
        email = "john.doe@example.com",
        phone = 1234567890,
        favorite = false,
        birthday = 0,
        imageResUri = "",
        expectedSalary = 100000,
        notes = "Good candidate"
    )

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TestDatabase::class.java
        ).build()
        candidateDao = database.candidateDao()
        candidateRepository = CandidateRepository(candidateDao)
    }

    @After
    fun closeSetup() {
        database.close()
    }

    @Test
    fun checkInsertedCandidateIsInDatabase() = runBlocking {

        candidateRepository.insertOrUpdateCandidate(classicCandidate)

        val insertedCandidate = classicCandidate.id?.let { candidateRepository.getCandidateById(it) }
        assertEquals(classicCandidate, insertedCandidate)
    }

    @Test
    fun checkUpdatedCandidateIsUpdated() = runBlocking {

        candidateRepository.insertOrUpdateCandidate(classicCandidate)

        val updatedCandidate = classicCandidate.copy(name = "John Smith", favorite = true)
        candidateRepository.insertOrUpdateCandidate(updatedCandidate)

        val retrievedCandidate = updatedCandidate.id?.let { candidateDao.getCandidateById(it) }
        assertEquals(retrievedCandidate?.name, updatedCandidate.name)
        assertTrue(retrievedCandidate?.favorite == true)
    }

    @Test
    fun checkDeletedCandidateIsCorrectlyRemoved() = runBlocking {


        candidateRepository.insertOrUpdateCandidate(classicCandidate)

        candidateRepository.deleteCandidate(classicCandidate)

        val retrievedCandidate = classicCandidate.id?.let { candidateDao.getCandidateById(it) }
        assertNull(retrievedCandidate)
    }

}