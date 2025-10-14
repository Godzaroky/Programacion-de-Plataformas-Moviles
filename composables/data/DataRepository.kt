package com.example.rickmortyapp.composables.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.stringPreferencesKey

class DataRepository(private val dataStore: AppDataStore) {

    private val NAME_KEY = stringPreferencesKey("user_name")

    val userName: Flow<String?> = dataStore.data.map { prefs ->
        prefs[NAME_KEY]
    }

    suspend fun saveUserName(name: String) {
        dataStore.edit { prefs -> prefs[NAME_KEY] = name }
    }

    suspend fun clearUserName() {
        dataStore.edit { prefs -> prefs.remove(NAME_KEY) }
    }
}