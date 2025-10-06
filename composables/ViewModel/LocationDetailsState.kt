package uvg.gonzaroky.composables.ViewModel

import uvg.gonzaroky.composables.DataBase.Location

data class LocationDetailsState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val location: Location? = null
)