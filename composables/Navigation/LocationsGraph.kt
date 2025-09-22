package uvg.gonzaroky.composables.Navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import uvg.gonzaroky.composables.LocationDb
import uvg.gonzaroky.composables.LocationDetailsScreen
import uvg.gonzaroky.composables.LocationsScreen

fun NavGraphBuilder.locationsGraph(navController: NavHostController) {
    navigation<LocationsList>(startDestination = LocationsList) {
        composable<LocationsList> {
            val db = LocationDb()
            LocationsScreen(
                locations = db.getAllLocations(),
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
