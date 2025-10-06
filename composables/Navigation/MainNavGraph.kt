package uvg.gonzaroky.composables.Navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uvg.gonzaroky.composables.ui.theme.Screens.ProfileScreen
import uvg.gonzaroky.composables.ui.theme.Screens.LoginScreen
import uvg.gonzaroky.composables.ui.theme.NotificationScreen
import uvg.gonzaroky.composables.ui.theme.Screens.LoginScreen

@Composable
fun RickMortyApp() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(CharactersList) },
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("Characters") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(LocationsList) },
                    icon = { Icon(Icons.Default.Place, null) },
                    label = { Text("Locations") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Profile) },
                    icon = { Icon(Icons.Default.AccountCircle, null) },
                    label = { Text("Profile") }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = CharactersList,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Nested graphs
            charactersGraph(navController)
            locationsGraph(navController)

            // Profile
            composable<Profile> {
                ProfileScreen(
                    onLogout = {
                        navController.navigate(Login) {
                            popUpTo(0) { inclusive = true } // limpia el backstack
                        }
                    }
                )
            }

            // Login (para logout redirigir)
            composable<Login> {
                LoginScreen(
                    onEnterClick = {
                        navController.navigate(CharactersList) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
