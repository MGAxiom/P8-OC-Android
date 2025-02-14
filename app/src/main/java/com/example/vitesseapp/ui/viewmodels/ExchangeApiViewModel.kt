package com.example.vitesseapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vitesseapp.domain.ExchangeRateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ExchangeApiViewModel (
    private val repository: ExchangeRateRepository
): ViewModel() {
    val exchangeRate = MutableStateFlow<Double?>(null)

    fun convertCurrency(amount: Double) {
        viewModelScope.launch {
            try {
                val response = repository.getExchangeRate(amount)
                exchangeRate.value = response.conversion_result
            } catch (e: Exception) {

            }
        }
    }
}