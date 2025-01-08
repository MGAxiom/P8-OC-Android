package com.example.vitesseapp.di

import com.example.vitesseapp.data.local.AppDatabase
import com.example.vitesseapp.domain.CandidateRepository
import com.example.vitesseapp.ui.viewmodels.CandidateViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single {
        AppDatabase.create(androidContext())
    }

    single { get<AppDatabase>().candidateDao() }
    single { CandidateRepository(get()) }

    viewModel { CandidateViewModel(get()) }
}