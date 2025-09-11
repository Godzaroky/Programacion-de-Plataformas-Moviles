import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.examencorto.ui.theme.ExamenCortoTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExamenCortoTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "menu"
                ){
                    composable("menu"){
                        MenuScreen (
                            onAddClick = {navController.navigate("operations/add")},
                            onMinusClick = {navController.navigate("operations/minus")}
                        )
                    }
                    composable("operations/{type}"){backStackEntry ->
                        val type = backStackEntry.arguments?.getString("type") ?: "suma"
                        Operations(type)
                    }
                }
                }
            }
        }
    }

@Composable
fun MenuScreen(onAddClick: () -> Unit, onMinusClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Diego Gonzalez 24170",
            style = MaterialTheme.typography.headlineLarge
        )
        Button(onClick = onAddClick){
            Text("Suma")
        }
        Spacer(Modifier.height(16.dp))
        Button(onClick = onMinusClick){
            Text("Resta")
        }
    }
}

@Composable
fun Operations(type: String){
    var num1 by remember {mutableStateOf("")}
    var num2 by remember{mutableStateOf("")}
    var result by remember {mutableStateOf<Int?>(null)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = if(type == "suma") "Suma" else "Resta",
            style = MaterialTheme.typography.displayLarge
        )
        TextField(
            value = num1,
            onValueChange = {num1 = it},
            label = {Text("Primer numero")},
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = num2,
            onValueChange = {num2 = it},
            label = {Text("Segundo numero")},
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val n1 = num1.toIntOrNull() ?: 0
            val n2 = num2.toIntOrNull() ?: 0
            result = if (type == "suma") n1 + n2 else n1 - n2
        }){
            Text("Calcular")
        }
        Spacer(Modifier.height(24.dp))
        result?.let{
            Text(
                "Resultado; $it",
                style = MaterialTheme.typography.headlineMedium
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    ExamenCortoTheme {
        MenuScreen(onAddClick = {}, onMinusClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun OperationsPreview() {
    ExamenCortoTheme {
        Operations(type = "suma")
    }
}
