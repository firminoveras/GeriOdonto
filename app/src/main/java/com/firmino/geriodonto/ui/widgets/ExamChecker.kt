package com.firmino.geriodonto.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.firmino.geriodonto.companions.MaterialSymbol

@Composable
fun ExamChecker(
    label: String,
    symbolName: String,
    labelTrue: String = "Sim",
    labelFalse: String = "Não",
    value: Boolean,
    onValueChange: (Boolean) -> Unit,
) {
    Box(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.surfaceContainerHighest,
            shape = MaterialTheme.shapes.extraLarge,
        ),
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            MaterialSymbol(iconName = symbolName)
            Column {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(text = if (value) labelTrue else labelFalse)
            }
        }
        Switch(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(horizontal = 12.dp),
            checked = value,
            onCheckedChange = onValueChange,
        )
    }
}
