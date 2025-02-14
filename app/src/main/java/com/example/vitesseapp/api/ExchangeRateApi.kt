package com.example.vitesseapp.api

import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangeRateApi {
    @GET("v6/fdec5932887ede659716d9e2/pair/EUR/GBP/{amount}")
    suspend fun getExchangeRate(
        @Path("amount") amount: Double
    ): ExchangeRateResponse
}