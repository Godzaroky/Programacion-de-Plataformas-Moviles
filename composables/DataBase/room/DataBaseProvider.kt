package com.example.rickmortyapp.composables.DataBase.room

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "rick_and_morty_db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}