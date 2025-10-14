package com.example.rickmortyapp.composables.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmortyapp.composables.DataBase.CharacterDb
import com.example.rickmortyapp.composables.DataBase.LocationDb
import com.example.rickmortyapp.composables.DataBase.room.DatabaseProvider
import com.example.rickmortyapp.composables.DataBase.toEntity
import com.example.rickmortyapp.composables.DataStore.UserPreferencesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false
)

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val db = DatabaseProvider.getDatabase(application)
    private val userPrefs = UserPreferencesRepository(application)

    private val characterDao = db.characterDao()
    private val locationDao = db.locationDao()

    // --- LOGIN PRINCIPAL ---
    fun loginUser(name: String, onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true, hasError = false) }

                // Simular sincronización de datos
                delay(4000L)

                // Guardar usuario en DataStore
                userPrefs.saveUserName(name)

                // Poblar DB con datos iniciales (solo la primera vez)
                val chars = CharacterDb().getAllCharacters()
                characterDao.insertCharacters(chars.map { it.toEntity() })

                val locs = LocationDb().getAllLocations()
                locationDao.insertLocations(locs.map { it.toEntity() })

                _state.update { it.copy(isLoading = false) }
                onSuccess()

            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, hasError = true) }
                onError()
            }
        }
    }

    fun clearError() {
        _state.update { it.copy(hasError = false) }
    }
}