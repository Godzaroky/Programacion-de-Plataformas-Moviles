package com.example.rickmortyapp.composables.ViewModel

import com.example.rickmortyapp.composables.DataBase.Character

data class CharacterDetailsState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val character: Character? = null
)