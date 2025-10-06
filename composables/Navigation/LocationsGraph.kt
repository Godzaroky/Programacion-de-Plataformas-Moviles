package uvg.gonzaroky.composables.Navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import uvg.gonzaroky.composables.DataBase.LocationDb
import uvg.gonzaroky.composables.ui.theme.Screens.LocationDetailsScreen
import uvg.gonzaroky.composables.ui.theme.Screens.LocationsScreen

fun NavGraphBuilder.locationsGraph(navController: NavHostController) {
    navigation<LocationsList>(startDestination = LocationsList) {
        composable<LocationsList> {
            val db = LocationDb()
            LocationsScreen(
                onLocationClick = { id -> navController.navigate(LocationDetails(id)) }
            )
        }
        composable<LocationDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<LocationDetails>()
            val db = LocationDb()
            val location = db.getLocationById(args.locationId)
            LocationDetailsScreen(location, onBack = { navController.popBackStack() })
        }
    }
}
