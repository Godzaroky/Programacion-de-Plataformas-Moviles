package com.example.rickmortyapp.composables.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.rickmortyapp.composables.DataBase.Location
import com.example.rickmortyapp.composables.DataBase.LocationDb
import com.example.rickmortyapp.composables.DataBase.room.DatabaseProvider
import com.example.rickmortyapp.composables.DataBase.room.LocationEntity
import com.example.rickmortyapp.composables.DataBase.toEntity
import com.example.rickmortyapp.composables.DataBase.toLocation

data class LocationsState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val locations: List<Location> = emptyList()
)

class LocationsViewModel(application: Application) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(LocationsState())
    val state: StateFlow<LocationsState> = _state.asStateFlow()

    private val db = DatabaseProvider.getDatabase(application)
    private val dao = db.locationDao()

    init {
        loadLocations()
    }

    fun loadLocations() {
        _state.update { it.copy(isLoading = true, hasError = false) }

        viewModelScope.launch {
            delay(4000L)

            try {
                val localData: List<LocationEntity> = dao.getAllLocations()

                if (localData.isNotEmpty()) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            hasError = false,
                            locations = localData.map { entity -> entity.toLocation() }
                        )
                    }
                } else {
                    val initialData = LocationDb().getAllLocations()
                    dao.insertLocations(initialData.map { it.toEntity() })

                    _state.update {
                        it.copy(
                            isLoading = false,
                            hasError = false,
                            locations = initialData
                        )
                    }
                }

            } catch (e: Exception) {
                _state.update {
                    it.copy(isLoading = false, hasError = true)
                }
            }
        }
    }

    fun retryLoad() {
        loadLocations()
    }
}