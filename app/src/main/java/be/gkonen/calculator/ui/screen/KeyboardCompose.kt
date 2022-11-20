package be.gkonen.calculator.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.gkonen.calculator.domain.KeyboardHelper
import be.gkonen.calculator.model.UIEvent
import be.gkonen.calculator.model.UIState


@Composable
fun Keyboard(onEvent: (UIEvent) -> Unit) {
    val spaceBetween = 2.dp
    val sizeContent = 24.dp
    val nbLine = 4
    val nbColumn = 4

    val buttonModifier = Modifier.height(IntrinsicSize.Min)
    val iconColor = MaterialTheme.colorScheme.onSurface

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
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
                                onEvent(UIEvent.ButtonPressed(config))
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