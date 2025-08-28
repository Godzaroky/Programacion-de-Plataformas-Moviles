import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uvg.gonzaroky.laboratorio6.ui.theme.Laboratorio6Theme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Laboratorio6Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    ContadorAvanzado()
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ContadorAvanzado() {
    var contador by remember { mutableStateOf(0) }

    var totalIncrementos by remember { mutableStateOf(0) }
    var totalDecrementos by remember { mutableStateOf(0) }
    var maximo by remember { mutableStateOf(Int.MIN_VALUE) }
    var minimo by remember { mutableStateOf(Int.MAX_VALUE) }
    var totalCambios by remember { mutableStateOf(0) }

    val historial = remember { mutableStateListOf<Pair<Int, Boolean>>() }
    // Pair(valor, true=incremento / false=decremento)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Título
        Text(
            text = "Diego Gonzalez",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )

        //Contador
        Row(
            modifier = Modifier.padding(vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                contador--
                totalDecrementos++
                totalCambios++
                if (contador < minimo) minimo = contador
                historial.add(contador to false)
            }) {
                Icon(Icons.Default.Delete, contentDescription = "Decrementar")
            }

            Text(
                text = contador.toString(),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            IconButton(onClick = {
                contador++
                totalIncrementos++
                totalCambios++
                if (contador > maximo) maximo = contador
                historial.add(contador to true)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Incrementar")
            }
        }

        //Estadísticas
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text("Total incrementos: $totalIncrementos")
            Text("Total decrementos: $totalDecrementos")
            Text("Valor máximo: ${if (maximo == Int.MIN_VALUE) 0 else maximo}")
            Text("Valor mínimo: ${if (minimo == Int.MAX_VALUE) 0 else minimo}")
            Text("Total cambios: $totalCambios")
        }

        // --- Historial ---
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Historial:", fontWeight = FontWeight.Bold)

            FlowRow(
                maxItemsInEachRow = 5, // máximo 5 columnas
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                historial.forEach { (valor, esIncremento) ->
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                if (esIncremento) Color(0xFF2E7D32) else Color(0xFFC62828),
                                shape = RoundedCornerShape(6.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = valor.toString(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        //Botón Reiniciar
        Button(
            onClick = {
                contador = 0
                totalIncrementos = 0
                totalDecrementos = 0
                maximo = Int.MIN_VALUE
                minimo = Int.MAX_VALUE
                totalCambios = 0
                historial.clear()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3F51B5),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            Text("Reiniciar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContadorPreview() {
    Laboratorio6Theme {
        ContadorAvanzado()
    }
}
