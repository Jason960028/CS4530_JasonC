package com.example.a4.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FunFactDao {
    @Query("SELECT * FROM fun_facts ORDER BY id DESC")
    fun getAllFacts(): Flow<List<FunFact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFact(fact: FunFact)

    @Query("DELETE FROM fun_facts")
    suspend fun deleteAllFacts()
}
