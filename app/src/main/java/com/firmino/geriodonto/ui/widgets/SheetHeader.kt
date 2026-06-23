package com.firmino.geriodonto.ui.widgets

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.firmino.geriodonto.companions.MaterialSymbol

@Composable
fun SheetHeader(
    iconName: String,
    title: String,
    subtitle: String,
    compactMode: Boolean = false,
    content: @Composable RowScope.() -> Unit = {},
) {
    var compactMode by remember { mutableStateOf(compactMode) }
    val verticalBias by animateFloatAsState(
        targetValue = if (compactMode) 0f else -1f,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "HeaderContentVerticalAlignment",
    )
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { compactMode = !compactMode },
        ) {
            AnimatedContent(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(horizontal = 12.dp),
                targetState = compactMode,
            ) { mode ->
                when (mode) {
                    true -> {
                        Row(
                            modifier = Modifier.padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            MaterialSymbol(iconName = iconName, size = 32.sp)
                            Column {
                                Text(text = title, style = MaterialTheme.typography.titleLarge)
                                AnimatedVisibility(visible = subtitle.isNotEmpty()) {
                                    Text(text = subtitle, style = MaterialTheme.typography.labelMedium)
                                }
                            }
                        }
                    }

                    false -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            MaterialSymbol(iconName = iconName, size = 64.sp)
                            Text(text = title, style = MaterialTheme.typography.titleLarge)
                            AnimatedVisibility(visible = subtitle.isNotEmpty()) {
                                Text(text = subtitle, style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .align(BiasAlignment(horizontalBias = 1f, verticalBias = verticalBias))
                    .padding(horizontal = 12.dp),
                content = content,
            )
        }
        HorizontalDivider()
    }
}
