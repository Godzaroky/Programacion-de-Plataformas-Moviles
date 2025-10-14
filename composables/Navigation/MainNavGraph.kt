package com.example.rickmortyapp.composables.Navigation

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.rickmortyapp.composables.DataStore.UserPreferencesRepository
import com.example.rickmortyapp.composables.ui.theme.Screens.LoginScreen
import com.example.rickmortyapp.composables.ui.theme.Screens.ProfileScreen

@Composable
fun RickMortyApp(
    isLoggedIn: Boolean,
    repository: UserPreferencesRepository
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (isLoggedIn) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == CharactersList::class.qualifiedName,
                        onClick = { navController.navigate(CharactersList) },
                        icon = { Icon(Icons.Default.Person, null) },
                        label = { Text("Characters") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == LocationsList::class.qualifiedName,
                        onClick = { navController.navigate(LocationsList) },
                        icon = { Icon(Icons.Default.Place, null) },
                        label = { Text("Locations") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == Profile::class.qualifiedName,
                        onClick = { navController.navigate(Profile) },
                        icon = { Icon(Icons.Default.AccountCircle, null) },
                        label = { Text("Profile") }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if (isLoggedIn) CharactersList else Login,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Login
            composable<Login> {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(CharactersList) {
                            popUpTo(Login) { inclusive = true }
                        }
                    }
                )
            }

            // Characters
            charactersGraph(navController)

            // Locations
            locationsGraph(navController)

            // Profile
            composable<Profile> {
                ProfileScreen(
                    repository = repository,
                    onLogout = {
                        navController.navigate(Login) {
                            popUpTo(CharactersList) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
