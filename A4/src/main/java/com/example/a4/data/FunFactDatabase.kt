package com.example.a4.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FunFact::class], version = 1, exportSchema = false)
abstract class FunFactDatabase : RoomDatabase() {
    abstract fun funFactDao(): FunFactDao

    companion object {
        @Volatile
        private var INSTANCE: FunFactDatabase? = null

        fun getDatabase(context: Context): FunFactDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FunFactDatabase::class.java,
                    "fun_fact_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
