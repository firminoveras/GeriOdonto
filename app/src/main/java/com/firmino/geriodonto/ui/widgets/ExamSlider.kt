package com.firmino.geriodonto.ui.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.firmino.geriodonto.companions.MaterialSymbol

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamSlider(
    label: String,
    symbolName: String,
    suffix: String,
    value: SliderState,
    decimals: Int = 0,
    plusIndicator: Boolean = false,
    description: (value: Float) -> String = { "" },
) {
    var isEditing by remember { mutableStateOf(false) }

    val formatedValue = "%.${decimals}f${if (plusIndicator && value.value == value.valueRange.endInclusive) "+" else ""}".format(
        value.value,
    )

    Column(
        modifier = Modifier.background(
            color = if (isEditing) MaterialTheme.colorScheme.surfaceContainerHighest else Color.Transparent,
            shape = MaterialTheme.shapes.extraLarge,
        ),
    ) {
        Box {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(if (isEditing) 0f else 1f),
                value = if (value.value != value.valueRange.start) "$formatedValue $suffix" else "",
                onValueChange = {},
                readOnly = true,
                label = { Text(label) },
                leadingIcon = { MaterialSymbol(symbolName, filled = isEditing) },
                shape = MaterialTheme.shapes.extraLarge,
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                ),
            )
            Slider(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 24.dp)
                    .alpha(if (isEditing) 1f else 0f)
                    .pointerInput(Unit) {
                        awaitEachGesture {
                            awaitFirstDown(pass = PointerEventPass.Initial)
                            isEditing = true
                            do {
                                val event = awaitPointerEvent(pass = PointerEventPass.Initial)
                                val anyChangesDown = event.changes.any { it.pressed }
                            } while (anyChangesDown)
                            isEditing = false
                        }
                    },
                state = value,
                colors = SliderDefaults.colors(inactiveTrackColor = MaterialTheme.colorScheme.primary),
            )
        }
        AnimatedVisibility(visible = isEditing) {
            HorizontalDivider(color = MaterialTheme.colorScheme.surface)
            Row(
                Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    MaterialSymbol(symbolName)
                    if (description(value.value).isEmpty()) {
                        Text(text = label, style = MaterialTheme.typography.bodyLarge)
                    } else {
                        Column {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                            Text(text = description(value.value), style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = formatedValue, style = MaterialTheme.typography.headlineSmall)
                    Text(
                        text = suffix,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}
