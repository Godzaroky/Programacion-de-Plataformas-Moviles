package uvg.gonzaroky.composables.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uvg.gonzaroky.composables.DataBase.CharacterDb

class CharacterDetailsViewModel : ViewModel() {
    private val _state = MutableStateFlow(CharacterDetailsState())
    val state = _state.asStateFlow()

    private val db = CharacterDb()

    fun loadCharacterById(id: Int) {
        _state.update { it.copy(isLoading = true, hasError = false) }
        viewModelScope.launch {
            delay(1000L)
            try {
                val character = db.getCharacterById(id)
                _state.update {
                    it.copy(isLoading = false, character = character)
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, hasError = true) }
            }
        }
    }
}