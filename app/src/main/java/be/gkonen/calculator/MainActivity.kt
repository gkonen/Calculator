package be.gkonen.calculator


import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    val nbLine = 3
    val nbColumn = 4


    val buttonModifier = Modifier.height(IntrinsicSize.Min)
    val buttonShape : (Int) -> RoundedCornerShape = { input: Int ->
        when(input) {
            1 -> RoundedCornerShape(topStart = 8.dp)
            4 -> RoundedCornerShape(topEnd = 8.dp)
            9 -> RoundedCornerShape(bottomStart = 8.dp)
            12 -> RoundedCornerShape(bottomEnd = 8.dp)
            else -> RoundedCornerShape(0.dp)
        }
    }
    val iconColor = MaterialTheme.colorScheme.onSurface

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        Arrangement.spacedBy(spaceBetween)
    ) {
        for (line in 0 until nbLine) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(spaceBetween)
            )
            {
                for (column in 0 until nbColumn) {
                    val input = line * nbColumn + column + 1
                    if (column != nbColumn-1) {
                        Button(
                            modifier = buttonModifier.weight(1.0f),
                            shape = buttonShape(input),
                            onClick = { onClickButton("$input", context) }) {
                            Text(text = "${input - line}",
                                fontSize = with(LocalDensity.current) { sizeContent.toSp() })
                        }
                    } else {
                        val icon = when(line) {
                            0 -> ImageVector.vectorResource(id = R.drawable.ic_math_add)
                            1 -> ImageVector.vectorResource(id = R.drawable.ic_math_minus)
                            2 -> ImageVector.vectorResource(id = R.drawable.ic_math_multiply)
                            3 -> ImageVector.vectorResource(id = R.drawable.ic_math_divide)
                            else -> null
                        }
                        Button(modifier = buttonModifier.weight(1.0f),
                        shape = buttonShape(input),
                        onClick = { onClickButton("$input", context) }) {
                            icon?.let { Icon(imageVector = icon,
                                contentDescription = null,
                                tint = iconColor,
                                modifier = Modifier
                                    .size(sizeContent)
                                    .aspectRatio(1f) )
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