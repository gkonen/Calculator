package be.gkonen.calculator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.gkonen.calculator.domain.KeyboardHelper
import be.gkonen.calculator.model.UIEvent
import be.gkonen.calculator.model.UIState
import be.gkonen.calculator.ui.screen.CalculatorViewModel
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
                    Keyboard()
                }
            }
        }
    }
}

@Composable
fun Keyboard() {
    val spaceBetween = 2.dp
    val sizeContent = 24.dp
    val nbLine = 4
    val nbColumn = 4

    val context = LocalContext.current
    val buttonModifier = Modifier.height(IntrinsicSize.Min)
    val iconColor = MaterialTheme.colorScheme.onSurface

    val viewModel = viewModel<CalculatorViewModel>()
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        Arrangement.spacedBy(spaceBetween)
    ) {
        for(line in 0..nbLine) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(spaceBetween)
            )  {
                for(column in 0 until nbColumn) {
                    val valueId = line*nbColumn + column

                    val config = KeyboardHelper[valueId]
                    if( config != null) {
                        Button(
                            modifier = buttonModifier.weight(KeyboardHelper.getWeight(valueId)),
                            shape = KeyboardHelper.getShape(valueId),
                            onClick = {
                                viewModel.onEvent(UIEvent.ButtonPressed(config))
                            }) {

                                config.value?.let { value ->
                                    Text(text = value,
                                        fontSize = with(LocalDensity.current) { sizeContent.toSp() })
                                }
                                config.iconId?.let { id ->
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = id),
                                        contentDescription = null,
                                        tint = iconColor,
                                        modifier = Modifier
                                            .size(sizeContent)
                                            .aspectRatio(1f)
                                    )
                                }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalculatorTheme {
        Keyboard()
    }
}