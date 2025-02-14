package com.example.vitesseapp.api

data class ExchangeRateResponse(
    val result: String,
    val conversion_rate: Double,
    val conversion_result: Double
)
