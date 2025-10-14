package com.example.rickmortyapp.composables.data

import android.content.Context
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

val Context.dataStore by preferencesDataStore(name = "app_preferences")

class AppDataStore(private val context: Context) {

    val data: Flow<Preferences> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }

    suspend fun edit(transform: suspend (MutablePreferences) -> Unit) {
        context.dataStore.edit { prefs ->
            transform(prefs)
        }
    }
}