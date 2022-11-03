package be.gkonen.calculator

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.gkonen.calculator.ui.screen.KeyboardHelper
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
    val context = LocalContext.current
    val nbLine = 4
    val nbColumn = 4

    val buttonModifier = Modifier.height(IntrinsicSize.Min)
    val iconColor = MaterialTheme.colorScheme.onSurface

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
                            onClick = { onClickButton("value", context) }) {

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

fun onClickButton(input: String, context: Context) = run {
    Toast.makeText(context,
    "vous avez appuyé sur $input",
        Toast.LENGTH_SHORT).show()
}