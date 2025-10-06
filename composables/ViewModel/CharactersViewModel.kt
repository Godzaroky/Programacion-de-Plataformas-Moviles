package uvg.gonzaroky.composables.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uvg.gonzaroky.composables.DataBase.CharacterDb

class CharactersViewModel : ViewModel() {
    private val db = CharacterDb()
    private val _state = MutableStateFlow(CharacterState())
    val state = _state.asStateFlow()

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        _state.update { it.copy(isLoading = true, hasError = false) }

        viewModelScope.launch {
            delay(4000L)
            try {
                val characters = db.getAllCharacters()
                _state.update {
                    it.copy(
                        isLoading = false,
                        hasError = false,
                        characters = characters
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, hasError = true) }
            }
        }
    }
}

data class CharacterState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val characters: List<uvg.gonzaroky.composables.DataBase.Character> = emptyList()
)