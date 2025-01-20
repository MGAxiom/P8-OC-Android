package com.example.vitesseapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vitesseapp.data.models.Candidate
import com.example.vitesseapp.domain.CandidateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class CandidateViewModel(private val repository: CandidateRepository): ViewModel() {

    internal val candidates = MutableStateFlow<List<Candidate>>(emptyList())
    internal val favoriteCandidates = MutableStateFlow<List<Candidate>>(emptyList())

    init {
        loadCandidates()
        loadFavouriteCandidates()
    }

    fun loadCandidates() {
        viewModelScope.launch {
            repository.getAllCandidates().collect { candidateList ->
                candidates.value = candidateList
            }
        }
    }

    fun loadFavouriteCandidates() {
        viewModelScope.launch {
            repository.getAllFavoriteCandidates().collect { favoriteCandidateList ->
                favoriteCandidates.value = favoriteCandidateList
            }
        }
    }

    fun insertCandidate(candidate: Candidate) = viewModelScope.launch {
        repository.insertCandidate(candidate)
    }

    fun updateCandidate(candidate: Candidate) = viewModelScope.launch {
        repository.updateCandidate(candidate)
    }

    fun deleteCandidate(candidate: Candidate) = viewModelScope.launch {
        repository.deleteCandidate(candidate)
    }

    suspend fun getCandidateById(id: Int): Flow<Candidate?> = flow {
        emit(repository.getCandidateById(id))
    }

    fun searchCandidates(query: String) = viewModelScope.launch {
        repository.searchCandidates(query)
    }
}