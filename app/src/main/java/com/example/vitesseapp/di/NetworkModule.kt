package com.example.vitesseapp.di

import com.example.vitesseapp.api.ExchangeRateApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideRetrofit() }
    single { provideExchangeApi(get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://v6.exchangerate-api.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideExchangeApi(retrofit: Retrofit): ExchangeRateApi {
    return retrofit.create(ExchangeRateApi::class.java)
}

