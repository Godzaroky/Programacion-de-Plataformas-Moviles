package uvg.gonzaroky.composables.Navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import uvg.gonzaroky.composables.CharacterDb
import uvg.gonzaroky.composables.CharacterDetailsScreen
import uvg.gonzaroky.composables.CharactersScreen

fun NavGraphBuilder.charactersGraph(navController: NavHostController) {
    val db = CharacterDb()

    navigation<CharactersList>(startDestination = CharactersList) {

        composable<CharactersList> {
            CharactersScreen(
                characters = db.getAllCharacters(),
                onCharacterClick = { id ->
                    navController.navigate(CharacterDetails(id))
                }
            )
        }

        composable<CharacterDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<CharacterDetails>()
            val character = db.getCharacterById(args.characterId)

            CharacterDetailsScreen(
                character = character,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
