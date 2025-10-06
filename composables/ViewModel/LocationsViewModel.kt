package uvg.gonzaroky.composables.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uvg.gonzaroky.composables.DataBase.LocationDb

class LocationsViewModel : ViewModel() {
    private val _state = MutableStateFlow(LocationsState())
    val state = _state.asStateFlow()

    private val db = LocationDb()

    init {
        loadLocations()
    }

    fun loadLocations() {
        _state.update { it.copy(isLoading = true, hasError = false) }
        viewModelScope.launch {
            delay(2000L)
            try {
                val locations = db.getAllLocations()
                _state.update {
                    it.copy(isLoading = false, hasError = false, locations = locations)
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, hasError = true) }
            }
        }
    }
}