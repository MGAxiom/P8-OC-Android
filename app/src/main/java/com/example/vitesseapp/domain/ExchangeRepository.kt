package com.example.vitesseapp.domain

import com.example.vitesseapp.api.ExchangeRateApi
import com.example.vitesseapp.api.ExchangeRateResponse


class ExchangeRateRepository(private val api: ExchangeRateApi) {
    suspend fun getExchangeRate(amount: Double): ExchangeRateResponse {
        return api.getExchangeRate(amount)
    }
}
