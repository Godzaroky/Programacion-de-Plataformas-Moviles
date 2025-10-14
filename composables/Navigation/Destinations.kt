package com.example.rickmortyapp.composables.Navigation

import kotlinx.serialization.Serializable

@Serializable object Login
@Serializable object CharactersList
@Serializable data class CharacterDetails(val characterId: Int)

@Serializable object LocationsList
@Serializable data class LocationDetails(val locationId: Int)

@Serializable object Profile


