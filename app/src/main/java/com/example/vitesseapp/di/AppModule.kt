package com.example.vitesseapp.di

import com.example.vitesseapp.domain.ExchangeRateRepository
import com.example.vitesseapp.ui.viewmodels.ExchangeApiViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { ExchangeRateRepository(get()) }
    viewModel { ExchangeApiViewModel(get()) }
}
