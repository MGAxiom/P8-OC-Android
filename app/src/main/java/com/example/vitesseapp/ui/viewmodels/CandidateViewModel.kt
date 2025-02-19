package com.example.vitesseapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vitesseapp.data.models.Candidate
import com.example.vitesseapp.domain.CandidateRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CandidateViewModel(private val repository: CandidateRepository) : ViewModel() {

    private val searchQuery = MutableStateFlow("")
    internal val candidates = MutableStateFlow<List<Candidate>>(emptyList())
    internal val favoriteCandidates = MutableStateFlow<List<Candidate>>(emptyList())
    internal val currentCandidate = MutableStateFlow<Candidate?>(null)
    internal val isLoading = MutableStateFlow(false)
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
            isLoading.value = true
            try {
                val allCandidates = if (query.isEmpty()) {
                    repository.getAllCandidates().first()
                } else {
                    repository.searchCandidates("%$query%").first()
                }
                candidates.value = allCandidates
            } catch (e: Exception) {
                println("Error loading candidates: ${e.message}")
            } finally {
                isLoading.value = false
            }
        }
    }

    fun loadFavoriteCandidates(query: String) {
        viewModelScope.launch {
            isLoading.value = true
            val favorites = if (query.isEmpty()) {
                repository.getAllFavoriteCandidates().first()
            } else {
                repository.searchFavouriteCandidates("%$query%").first()
            }
            favoriteCandidates.value = favorites
            isLoading.value = false
        }
    }

    fun insertOrUpdateCandidate(candidate: Candidate) = viewModelScope.launch {
        createdId.value = repository.insertOrUpdateCandidate(candidate).toInt()
    }

    fun toggleFavorite() = viewModelScope.launch {
        currentCandidate.value?.let { candidate ->
            val updatedCandidate = candidate.copy(favorite = !candidate.favorite)
            repository.toggleFavoriteStatus(updatedCandidate)
            currentCandidate.value = updatedCandidate
        }
    }

    fun loadCandidateById(id: Int) = viewModelScope.launch {
        currentCandidate.value = repository.getCandidateById(id)
    }

    fun deleteCandidate(candidate: Candidate) = viewModelScope.launch {
        repository.deleteCandidate(candidate)
    }

    suspend fun getCandidateById(id: Int): Candidate? {
        return repository.getCandidateById(id)
    }
}