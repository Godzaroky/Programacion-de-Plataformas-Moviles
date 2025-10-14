package com.example.rickmortyapp.composables.ui.theme.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rickmortyapp.composables.DataBase.Character
import com.example.rickmortyapp.composables.DataBase.CharacterDb
import com.example.rickmortyapp.composables.ViewModel.CharactersViewModel

@Composable
fun CharactersScreen(
    viewModel: CharactersViewModel = viewModel(),
    onCharacterClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()

    when {
        state.isLoading -> LoadingScreen()

        state.hasError -> ErrorScreen(
            message = "Error al obtener listado de personajes. Intenta de nuevo",
            onRetry = { viewModel.loadCharacters() }
        )

        else -> CharactersList(
            characters = state.characters,
            onCharacterClick = onCharacterClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharactersList(
    characters: List<Character>,
    onCharacterClick: (Int) -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Characters") }) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            items(characters) { character ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCharacterClick(character.id) }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .padding(end = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Surface(
                            shape = MaterialTheme.shapes.small,
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = character.name.take(1),
                                modifier = Modifier.padding(12.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                    Column {
                        Text(character.name, style = MaterialTheme.typography.titleMedium)
                        Text("${character.species} - ${character.status}")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailsScreen(
    character: Character,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Character details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("ID: ${character.id}", style = MaterialTheme.typography.bodyMedium)
            Text("Name: ${character.name}", style = MaterialTheme.typography.titleMedium)
            Text("Status: ${character.status}")
            Text("Species: ${character.species}")
            Text("Gender: ${character.gender}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharactersScreenPreview() {
    val db = CharacterDb()
    CharactersScreen(
        onCharacterClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun CharacterDetailsPreview() {
    val db = CharacterDb()
    CharacterDetailsScreen(
        character = db.getCharacterById(1),
        onBack = {}
    )
}
