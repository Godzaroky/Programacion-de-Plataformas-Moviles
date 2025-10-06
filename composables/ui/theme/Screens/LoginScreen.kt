package uvg.gonzaroky.composables.ui.theme.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(modifier: Modifier = Modifier, onEnterClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Parte superior
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = onEnterClick) {
                Text("Entrar")
            }
            Spacer(Modifier.height(24.dp))
        }

        // Parte inferior
        Text(
            text = "Diego Gonzalez - #24170",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
