package uvg.gonzaroky.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uvg.gonzaroky.composables.Location

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsScreen(locations: List<Location>, onLocationClick: (Int) -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Locations") }) }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(locations) { loc ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onLocationClick(loc.id) }
                        .padding(16.dp)
                ) {
                    Column {
                        Text(text = loc.name, style = MaterialTheme.typography.titleMedium)
                        Text(text = "Type: ${loc.type}")
                    }
                }
                Divider()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDetailsScreen(location: Location, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Location details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text("ID: ${location.id}")
            Text("Name: ${location.name}")
            Text("Type: ${location.type}")
            Text("Dimension: ${location.dimension}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationScreenPreview() {
    val sampleLocations = listOf(
        Location(1, "Earth (C-137)", "Planet", "Dimension C-137"),
        Location(2, "Abadango", "Cluster", "Unknown Dimension")
    )
    LocationsScreen(sampleLocations, onLocationClick = {})
}
