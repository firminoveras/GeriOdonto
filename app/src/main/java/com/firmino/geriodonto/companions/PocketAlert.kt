package com.firmino.geriodonto.companions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


data class PocketAlertConfig(
    val message: String,
    val highlight: String,
    val iconName: String,
    val isLoading: Boolean,
    val duration: Duration,
    val color: Color?,
)

object PocketAlertManager {
    private val _alertState = MutableStateFlow<PocketAlertConfig?>(null)
    val alertState = _alertState.asStateFlow()

    fun show(
        message: String,
        highlight: String = "",
        iconName: String = "warning",
        isLoading: Boolean = false,
        duration: Duration = 3.seconds,
        color: Color? = null,
    ) {
        _alertState.value = PocketAlertConfig(
            message = message,
            highlight = highlight,
            iconName = iconName,
            isLoading = isLoading,
            duration = duration,
            color = color,
        )
    }

    fun dismiss() {
        _alertState.value = null
    }
}

@Composable
fun BoxScope.PocketAlert(
    modifier: Modifier = Modifier,
) {
    val alertConfig by PocketAlertManager.alertState.collectAsStateWithLifecycle()
    var lastValidConfig by remember { mutableStateOf<PocketAlertConfig?>(null) }

    LaunchedEffect(alertConfig) {
        alertConfig?.let { config ->
            lastValidConfig = config
            delay(config.duration)
            PocketAlertManager.dismiss()
        }
    }
    AnimatedVisibility(
        modifier = modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 92.dp),
        visible = alertConfig != null,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        lastValidConfig?.let { config ->

            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                color = MaterialTheme.colorScheme.surfaceContainerHighest,
                tonalElevation = 6.dp,
                shadowElevation = 6.dp,
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    if (config.isLoading) CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    else MaterialSymbol(iconName = config.iconName)
                    if (config.highlight.isNotEmpty()) {
                        Text(text = highlightedText(config.message, config.highlight), style = MaterialTheme.typography.titleSmall)
                    } else {
                        Text(text = config.message, style = MaterialTheme.typography.titleSmall)
                    }
                }
            }
        }
    }
}
