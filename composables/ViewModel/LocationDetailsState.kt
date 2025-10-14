package com.example.rickmortyapp.composables.ViewModel

import com.example.rickmortyapp.composables.DataBase.Location

data class LocationDetailsState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val location: Location? = null
)