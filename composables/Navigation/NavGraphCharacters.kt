package com.example.rickmortyapp.composables.Navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.rickmortyapp.composables.DataBase.CharacterDb
import com.example.rickmortyapp.composables.ui.theme.Screens.CharacterDetailsScreen
import com.example.rickmortyapp.composables.ui.theme.Screens.CharactersScreen

fun NavGraphBuilder.charactersGraph(navController: NavHostController) {
    val db = CharacterDb()

    navigation<CharactersList>(startDestination = CharactersList) {

        composable<CharactersList> {
            CharactersScreen(
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
