package com.example.vitesseapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.vitesseapp.data.local.AppDatabase
import com.example.vitesseapp.di.databaseModule
import com.example.vitesseapp.domain.CandidateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidContext(this@MainActivity)
            modules(databaseModule)
        }

        val database by lazy { AppDatabase.create(this) }
        val repository by lazy { CandidateRepository(database.candidateDao()) }

        GlobalScope.launch(Dispatchers.IO) {
            repository.insertInitialCandidates()
        }

        supportActionBar?.hide()
        enableEdgeToEdge()
        setContentView(R.layout.main_activity)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController
    }
}
