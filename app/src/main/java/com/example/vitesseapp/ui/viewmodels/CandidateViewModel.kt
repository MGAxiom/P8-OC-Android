package com.example.vitesseapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vitesseapp.data.models.Candidate
import com.example.vitesseapp.domain.CandidateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CandidateViewModel(private val repository: CandidateRepository): ViewModel() {

    private val _candidates = MutableStateFlow<List<Candidate>>(emptyList())
    val candidates: StateFlow<List<Candidate>> = _candidates.asStateFlow()

    init {
        loadCandidates()
    }

    private fun loadCandidates() {
        viewModelScope.launch {
            repository.getAllCandidates().collect { candidateList ->
                _candidates.value = candidateList
            }
        }
    }

    fun getAllCandidates() = viewModelScope.launch {
        repository.getAllCandidates()
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

    fun getCandidateById(id: Int) = viewModelScope.launch {
        repository.getCandidateById(id)
    }

    fun getFavoriteCandidates() = viewModelScope.launch {
        repository.getAllFavoriteCandidates()
    }

    fun searchCandidates(query: String) = viewModelScope.launch {
        repository.searchCandidates(query)
    }
}