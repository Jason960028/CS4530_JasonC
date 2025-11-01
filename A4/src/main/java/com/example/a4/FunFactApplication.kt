package com.example.a4

import android.app.Application
import com.example.a4.data.FunFactApiService
import com.example.a4.data.FunFactDatabase
import com.example.a4.data.FunFactRepository

class FunFactApplication : Application() {

    private val database by lazy { FunFactDatabase.getDatabase(this) }
    private val apiService by lazy { FunFactApiService() }
    val repository by lazy {
        FunFactRepository.getInstance(database.funFactDao(), apiService)
    }

    override fun onTerminate() {
        super.onTerminate()
        apiService.close()
    }
}
