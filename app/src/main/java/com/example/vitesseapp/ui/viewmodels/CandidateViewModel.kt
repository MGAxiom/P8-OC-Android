package com.example.vitesseapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vitesseapp.data.models.Candidate
import com.example.vitesseapp.domain.CandidateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class CandidateViewModel(private val repository: CandidateRepository): ViewModel() {

    private val searchQuery = MutableStateFlow("")
    internal val candidates = MutableStateFlow<List<Candidate>>(emptyList())
    internal val favoriteCandidates = MutableStateFlow<List<Candidate>>(emptyList())
    val createdId = MutableStateFlow(0)

    init {
        loadCandidates("")
        loadFavoriteCandidates("")
    }

    fun setSearchQuery(query: String) {
        viewModelScope.launch {
            searchQuery.value = query
            loadCandidates(query)
            loadFavoriteCandidates(query)
        }
    }

    fun loadCandidates(query: String) {
        viewModelScope.launch {
            val allCandidates = if (query.isEmpty()) {
                repository.getAllCandidates().first()
            } else {
                repository.searchCandidates("%$query%").first()
            }
            candidates.value = allCandidates
        }
    }

    fun loadFavoriteCandidates(query: String) {
        viewModelScope.launch {
            val favorites = if (query.isEmpty()) {
                repository.getAllFavoriteCandidates().first()
            } else {
                repository.searchFavouriteCandidates("%$query%").first()
            }
            favoriteCandidates.value = favorites
        }
    }

    fun insertCandidate(candidate: Candidate) = viewModelScope.launch {
        createdId.value = repository.insertCandidate(candidate).toInt()
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
        repository.searchCandidates(query).collect { candidateList ->
            candidates.value = candidateList
        }

    }
}