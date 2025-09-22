package com.example.laboratorio7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.laboratorio7.ui.theme.Laboratorio7Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Laboratorio7Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NotificationScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

enum class NotificationType {
    INFORMATIVA,
    CAPACITACION
}

data class Notification(
    val id: Int,
    val title: String,
    val body: String,
    val sendAt: String,
    val type: NotificationType
)

fun generateFakeNotifications(): List<Notification> {
    val notifications = mutableListOf<Notification>()
    val titles = listOf(
        "Nueva versión disponible",
        "Nueva reunión agendada con Koalit",
        "Mensaje de Maria",
        "Capacitación: jetpack compose internals"
    )
    val bodies = listOf(
        "La aplicación ha sido actualizada a v1.0.2. Ve a la PlayStore y actualízala!",
        "Koalit te ha enviado un evento para que agregues a tu calendario",
        "No te olvides de asistir a esta capacitación mañana, a las 6pm, en el Intecap.",
        "Inicio de la capacitación 'Jetpack Compose Internals', no faltes"
    )
    val types = NotificationType.entries.toTypedArray()

    for (i in 1..50) {
        val sendAt = "30 Sept 10:${(10..59).random()}am"
        notifications.add(
            Notification(
                id = i,
                title = titles.random(),
                body = bodies.random(),
                sendAt = sendAt,
                type = types.random()
            )
        )
    }
    return notifications
}

@Composable
fun NotificationScreen(modifier: Modifier = Modifier) {
    var filtro by remember { mutableStateOf<NotificationType?>(null) }

    val todas = remember { generateFakeNotifications() }
    val listaFiltrada = if (filtro == null) todas else todas.filter { it.type == filtro }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Tipos de notificaciones",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Botones de filtro
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterButton(
                text = "Informativas",
                selected = filtro == NotificationType.INFORMATIVA,
                onClick = {
                    filtro = if (filtro == NotificationType.INFORMATIVA) null else NotificationType.INFORMATIVA
                }
            )
            FilterButton(
                text = "Capacitaciones",
                selected = filtro == NotificationType.CAPACITACION,
                onClick = {
                    filtro = if (filtro == NotificationType.CAPACITACION) null else NotificationType.CAPACITACION
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de notificaciones
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(listaFiltrada) { notif ->
                NotificationItem(notif)
            }
        }
    }
}

@Composable
fun FilterButton(text: String, selected: Boolean, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
            contentColor = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
        ),
        border = if (selected) BorderStroke(0.dp, Color.Transparent) else null
    ) {
        Text(text)
    }
}

@Composable
fun NotificationItem(notif: Notification) {
    val (bgColor, icon, iconColor) = when (notif.type) {
        NotificationType.INFORMATIVA -> Triple(
            MaterialTheme.colorScheme.secondaryContainer,
            Icons.Filled.Notifications,
            MaterialTheme.colorScheme.onSecondaryContainer
        )
        NotificationType.CAPACITACION -> Triple(
            MaterialTheme.colorScheme.tertiaryContainer,
            Icons.Filled.Event,
            MaterialTheme.colorScheme.onTertiaryContainer
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(32.dp)
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = notif.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = notif.body,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = notif.sendAt,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Laboratorio7Theme {
        NotificationScreen()
    }
}
