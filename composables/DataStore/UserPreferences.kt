package com.example.rickmortyapp.composables.DataStore

import android.content.Context
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.rickmortyapp.composables.data.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferencesRepository(private val context: Context) {

    // ✅ Las claves deben estar dentro del companion object
    companion object {
        private val NAME_KEY = stringPreferencesKey("user_name")
        private val LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
    }

    suspend fun saveUserName(name: String) {
        context.dataStore.edit { prefs ->
            prefs[NAME_KEY] = name
            prefs[LOGGED_IN_KEY] = true
        }
    }

    suspend fun clearUserName() {
        context.dataStore.edit { prefs ->
            prefs.remove(NAME_KEY)
            prefs[LOGGED_IN_KEY] = false
        }
    }

    val userName: Flow<String> = context.dataStore.data
        .catch { e ->
            if (e is IOException) emit(emptyPreferences())
            else throw e
        }
        .map { prefs ->
            prefs[NAME_KEY] ?: ""
        }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data
        .catch { e ->
            if (e is IOException) emit(emptyPreferences())
            else throw e
        }
        .map { prefs ->
            prefs[LOGGED_IN_KEY] ?: false
        }
}