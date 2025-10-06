package uvg.gonzaroky.composables.ViewModel

data class CharacterDetailsState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val character: uvg.gonzaroky.composables.DataBase.Character? = null
)