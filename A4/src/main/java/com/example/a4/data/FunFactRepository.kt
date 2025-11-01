package com.example.a4.data

import kotlinx.coroutines.flow.Flow

class FunFactRepository private constructor(
    private val funFactDao: FunFactDao,
    private val apiService: FunFactApiService
) {
    val allFacts: Flow<List<FunFact>> = funFactDao.getAllFacts()

    suspend fun fetchAndStoreFact() {
        try {
            val fact = apiService.getRandomFact()
            funFactDao.insertFact(fact)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun deleteAllFacts() {
        funFactDao.deleteAllFacts()
    }

    companion object {
        @Volatile
        private var INSTANCE: FunFactRepository? = null

        fun getInstance(dao: FunFactDao, apiService: FunFactApiService): FunFactRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = FunFactRepository(dao, apiService)
                INSTANCE = instance
                instance
            }
        }
    }
}
