package be.gkonen.calculator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    val currentOperation by viewModel.information.collectAsState()

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

    Column(modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Bottom) {

        Box(modifier = Modifier
            .weight(1f)
            .background(MaterialTheme.colorScheme.background)) {
        Column(modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceAround) {

                PreviousOperationSurface()
            /*
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                )
            */

                ResultSurface(viewModel = viewModel)
            /*
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp))
            */

                OperationSurface(currentOperation)
            }
        }

        Box(modifier = Modifier
            .weight(1f, fill = true)
            .background(color = MaterialTheme.colorScheme.background)) {
            Keyboard(viewModel::onEvent)
        }
    }
}
@Composable
fun PreviousOperationSurface() {
    Box(modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd) {

        Surface(modifier = Modifier
            .height(54.dp)
            .padding(top = 16.dp, bottom = 16.dp, end = 8.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surfaceVariant) {

            Text(modifier = Modifier
                .padding(start = 12.dp, end = 12.dp)
                .wrapContentSize(align = Alignment.Center),
                text = "Old Result",
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun ResultSurface(viewModel: CalculatorViewModel) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(64.dp)
        .padding(top = 4.dp, bottom = 4.dp),
        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0f)) {

        Box(contentAlignment = Alignment.CenterEnd) {
            Text(modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                text = "Result",
                textAlign = TextAlign.End,
                fontSize = 48.sp,
                color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
fun OperationSurface(operation : String) {

    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(52.dp)
        .padding(top = 4.dp, bottom = 4.dp),
        color = MaterialTheme.colorScheme.surface) {

        Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.CenterEnd) {
            Text(modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                text = operation,
                fontSize = 32.sp,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalculatorTheme {
        PrincipalScreen()
    }
}