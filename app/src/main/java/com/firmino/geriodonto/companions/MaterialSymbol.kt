package com.firmino.geriodonto.companions

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.firmino.geriodonto.R
import kotlin.math.round

@OptIn(ExperimentalTextApi::class)
@Composable
fun MaterialSymbol(
    iconName: String,
    modifier: Modifier = Modifier,
    filled: Boolean = false,
    weight: Float = 400f,
    grad: Float = 0f,
    size: TextUnit = 24.sp,
    color: Color = MaterialTheme.colorScheme.onSurface,
    colorFilled: Color = MaterialTheme.colorScheme.primary,
) {
    val animatedFill by animateFloatAsState(
        targetValue = if (filled) 1f else 0f,
        animationSpec = tween(durationMillis = 300),
    )

    val roundedFill: Float = remember(animatedFill) {
        (round(animatedFill * 100f) / 100f).coerceIn(0f, 1f)
    }

    Text(
        modifier = modifier,
        text = iconName,
        fontFamily = FontFamily(
            Font(
                resId = R.font.material_symbols,
                variationSettings = FontVariation.Settings(
                    FontVariation.Setting("FILL", roundedFill),
                    FontVariation.Setting("wght", weight),
                    FontVariation.Setting("GRAD", grad),
                ),
            ),
        ),
        fontSize = size,
        color = if (filled) colorFilled else color,
    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun MaterialSymbol(
    iconName: String,
    modifier: Modifier = Modifier,
    size: Dp,
    color: Color = MaterialTheme.colorScheme.onSurface,
) {
    val fontFamily = FontFamily(
        Font(
            resId = R.font.material_symbols,
            variationSettings = FontVariation.Settings(
                FontVariation.Setting("FILL", 1f),
                FontVariation.Setting("wght", 400f),
                FontVariation.Setting("GRAD", 0f),
            ),
        ),
    )

    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current
    val fontSizeSp = with(density) { size.toSp() }
    val textLayoutResult = remember(iconName, fontFamily, fontSizeSp) {
        textMeasurer.measure(
            text = iconName,
            style = TextStyle(
                fontFamily = fontFamily,
                fontSize = fontSizeSp,
                color = color,
            ),
        )
    }

    Canvas(modifier = modifier.size(size)) {
        val x = (size.toPx() - textLayoutResult.size.width) / 2f
        val y = (size.toPx() - textLayoutResult.size.height) / 2f

        drawText(
            textLayoutResult = textLayoutResult,
            topLeft = Offset(x, y),
        )
    }
}