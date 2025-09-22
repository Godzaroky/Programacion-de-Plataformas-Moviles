package uvg.gonzaroky.composables.Navigation

import kotlinx.serialization.Serializable

// --- Characters ---
@Serializable
object CharactersList

@Serializable
data class CharacterDetails(val characterId: Int)

// --- Locations ---
@Serializable
object LocationsList

@Serializable
data class LocationDetails(val locationId: Int)

// --- Profile ---
@Serializable
object Profile

// --- Login ---
@Serializable
object Login

