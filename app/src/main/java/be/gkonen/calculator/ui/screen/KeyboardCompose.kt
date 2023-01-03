package be.gkonen.calculator.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.gkonen.calculator.domain.KeyboardHelper
import be.gkonen.calculator.model.UIEvent
import be.gkonen.calculator.ui.theme.light_buttonVariant

@Preview(showBackground = true)
@Composable
fun Keyboard(onEvent: (UIEvent) -> Unit = { _ -> }) {
    val spaceBetween = 2.dp
    val sizeContent = 24.dp
    val nbLine = 4
    val nbColumn = 4

    val buttonModifier = Modifier.fillMaxHeight() //height(IntrinsicSize.Max)
    val iconColor = MaterialTheme.colorScheme.onSurfaceVariant

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 16.dp),
        Arrangement.spacedBy(spaceBetween)
    ) {
        for(line in 0..nbLine) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(spaceBetween)
            )  {
                for(column in 0 until nbColumn) {
                    val valueId = line*nbColumn + column

                    val config = KeyboardHelper[valueId]
                    if( config != null) {

                        val buttonColors = ButtonDefaults.buttonColors(
                            containerColor = when(config.type) {
                                KeyboardHelper.ButtonType.OPERATOR -> light_buttonVariant
                                KeyboardHelper.ButtonType.VALUE -> MaterialTheme.colorScheme.surface
                                KeyboardHelper.ButtonType.SPECIAL -> MaterialTheme.colorScheme.surfaceVariant
                            },
                            contentColor = iconColor
                        )
                        Button(
                            modifier = buttonModifier
                                .weight(KeyboardHelper.getWeight(valueId)),
                            colors = buttonColors,
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