package com.example.rickmortyapp.composables.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.rickmortyapp.composables.DataBase.Location
import com.example.rickmortyapp.composables.DataBase.LocationDb

data class LocationsState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val locations: List<Location> = emptyList()
)

class LocationsDetailsViewModel : ViewModel() {

    private val _state = MutableStateFlow(LocationsState())
    val state = _state.asStateFlow()

    private val db = LocationDb()

    init {
        loadLocations()
    }

    fun loadLocations() {
        _state.update { it.copy(isLoading = true, hasError = false) }

        viewModelScope.launch {
            delay(4000L) // ⏳ simula 4 segundos de carga
            try {
                val locations = db.getAllLocations()
                _state.update {
                    it.copy(
                        isLoading = false,
                        hasError = false,
                        locations = locations
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        hasError = true
                    )
                }
            }
        }
    }
}