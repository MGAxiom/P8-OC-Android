package com.example.vitesseapp

import android.app.Application
import com.example.vitesseapp.di.appModule
import com.example.vitesseapp.di.databaseModule
import com.example.vitesseapp.di.networkModule
import org.koin.android.ext.koin.androidContext

class VitesseApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        org.koin.core.context.startKoin {
            androidContext(this@VitesseApplication)
            modules(listOf(databaseModule, networkModule, appModule))
        }
    }
}