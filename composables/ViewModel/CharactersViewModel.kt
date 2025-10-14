package com.example.rickmortyapp.composables.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmortyapp.composables.DataBase.room.DatabaseProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.rickmortyapp.composables.DataBase.Character
import com.example.rickmortyapp.composables.DataBase.CharacterDb
import com.example.rickmortyapp.composables.DataBase.toCharacter
import com.example.rickmortyapp.composables.DataBase.toEntity

data class CharactersState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val characters: List<Character> = emptyList()
)

class CharactersViewModel(application: Application) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(CharactersState())
    val state: StateFlow<CharactersState> = _state.asStateFlow()

    private val db = DatabaseProvider.getDatabase(application)
    private val dao = db.characterDao()

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        _state.update { it.copy(isLoading = true, hasError = false) }

        viewModelScope.launch {
            delay(4000L) // simula carga desde internet

            try {
                val localData = dao.getAllCharacters()

                if (localData.isNotEmpty()) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            hasError = false,
                            characters = localData.map { entity -> entity.toCharacter() }
                        )
                    }
                } else {
                    // Si la DB está vacía, la llenamos con datos de prueba
                    val initialData = CharacterDb().getAllCharacters()
                    dao.insertCharacters(initialData.map { it.toEntity() })

                    _state.update {
                        it.copy(
                            isLoading = false,
                            hasError = false,
                            characters = initialData
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
}
