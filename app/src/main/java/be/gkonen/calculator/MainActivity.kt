package be.gkonen.calculator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.gkonen.calculator.model.UIState
import be.gkonen.calculator.ui.screen.CalculatorViewModel
import be.gkonen.calculator.ui.screen.Keyboard
import be.gkonen.calculator.ui.theme.CalculatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PrincipalScreen()
                }
            }
        }
    }
}

@Composable
fun PrincipalScreen(viewModel: CalculatorViewModel = viewModel()) {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiState.collect { state ->
            when(state) {
                UIState.Idle -> {}
                is UIState.Notification -> {
                    Toast.makeText(context,state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column {
        Text(text = "coucou", textAlign = TextAlign.End)
        Spacer(modifier = Modifier.fillMaxWidth().height(8.dp))
        OperationSurface(viewModel = viewModel)
        Keyboard(viewModel::onEvent)
    }
}

@Composable
fun OperationSurface(viewModel: CalculatorViewModel) {
    Surface(modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 4.dp),
        color = MaterialTheme.colorScheme.onSurfaceVariant) {

        Text(modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            text = "1000 + 323",
            textAlign = TextAlign.End,
            color = MaterialTheme.colorScheme.onSurface)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalculatorTheme {
        PrincipalScreen()
    }
}