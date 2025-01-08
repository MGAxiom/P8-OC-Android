package com.example.vitesseapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vitesseapp.data.models.Candidate
import com.example.vitesseapp.domain.CandidateRepository
import kotlinx.coroutines.launch

class CandidateViewModel(private val repository: CandidateRepository): ViewModel() {

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

    fun searchCandidates(query: String) = viewModelScope.launch {
        repository.searchCandidates(query)
    }
}