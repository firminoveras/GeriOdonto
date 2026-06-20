package com.firmino.geriodonto.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.firmino.geriodonto.R

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF8BCEE7),
    onPrimary = Color(0xFF003547),
    primaryContainer = Color(0xFF004D63),
    onPrimaryContainer = Color(0xFFC7E7F1),
    inversePrimary = Color(0xFF1A5F7A),

    secondary = Color(0xFFA1D0CB),
    onSecondary = Color(0xFF033734),
    secondaryContainer = Color(0xFF20615C),
    onSecondaryContainer = Color(0xFFBCECE7),

    tertiary = Color(0xFFFFB68E),
    onTertiary = Color(0xFF552200),
    tertiaryContainer = Color(0xFF783500),
    onTertiaryContainer = Color(0xFFFFDBCA),

    background = Color(0xFF111416),
    onBackground = Color(0xFFE2E2E5),
    surface = Color(0xFF111416),
    onSurface = Color(0xFFE2E2E5),
    surfaceVariant = Color(0xFF42474A),
    onSurfaceVariant = Color(0xFFC2C7CC),
    surfaceTint = Color(0xFF8BCEE7),
    inverseSurface = Color(0xFFE2E2E5),
    inverseOnSurface = Color(0xFF2E3132),

    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),

    outline = Color(0xFF8C9196),
    outlineVariant = Color(0xFF42474A),
    scrim = Color(0xFF000000),

    surfaceBright = Color(0xFF37393B),
    surfaceDim = Color(0xFF111416),
    surfaceContainerLowest = Color(0xFF0C0F10),
    surfaceContainerLow = Color(0xFF191C1E),
    surfaceContainer = Color(0xFF1D2022),
    surfaceContainerHigh = Color(0xFF282A2C),
    surfaceContainerHighest = Color(0xFF333537),

    primaryFixed = Color(0xFFC7E7F1),
    primaryFixedDim = Color(0xFF9BD0E4),
    onPrimaryFixed = Color(0xFF001F2A),
    onPrimaryFixedVariant = Color(0xFF004D63),

    secondaryFixed = Color(0xFFBCECE7),
    secondaryFixedDim = Color(0xFFA1D0CB),
    onSecondaryFixed = Color(0xFF00201E),
    onSecondaryFixedVariant = Color(0xFF20615C),

    tertiaryFixed = Color(0xFFFFDBCA),
    tertiaryFixedDim = Color(0xFFFFB68E),
    onTertiaryFixed = Color(0xFF331200),
    onTertiaryFixedVariant = Color(0xFF783500)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1A5F7A),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFC7E7F1),
    onPrimaryContainer = Color(0xFF001F2A),
    inversePrimary = Color(0xFF8BCEE7),

    secondary = Color(0xFF3B7A75),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFBCECE7),
    onSecondaryContainer = Color(0xFF00201E),

    tertiary = Color(0xFF9E4700),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFDBCA),
    onTertiaryContainer = Color(0xFF331200),

    background = Color(0xFFF8F9FA),
    onBackground = Color(0xFF191C1E),
    surface = Color(0xFFF8F9FA),
    onSurface = Color(0xFF191C1E),
    surfaceVariant = Color(0xFFDEE3E6),
    onSurfaceVariant = Color(0xFF42474A),
    surfaceTint = Color(0xFF1A5F7A),
    inverseSurface = Color(0xFF2E3132),
    inverseOnSurface = Color(0xFFF0F1F3),

    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),

    outline = Color(0xFF72787C),
    outlineVariant = Color(0xFFC2C7CC),
    scrim = Color(0xFF000000),

    surfaceBright = Color(0xFFFCFDFE),
    surfaceDim = Color(0xFFD8DCC0),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF1F4F6),
    surfaceContainer = Color(0xFFEBEFF2),
    surfaceContainerHigh = Color(0xFFE6EAEB),
    surfaceContainerHighest = Color(0xFFDFE3E5),

    primaryFixed = Color(0xFFC7E7F1),
    primaryFixedDim = Color(0xFF9BD0E4),
    onPrimaryFixed = Color(0xFF001F2A),
    onPrimaryFixedVariant = Color(0xFF004D63),

    secondaryFixed = Color(0xFFBCECE7),
    secondaryFixedDim = Color(0xFFA1D0CB),
    onSecondaryFixed = Color(0xFF00201E),
    onSecondaryFixedVariant = Color(0xFF20615C),

    tertiaryFixed = Color(0xFFFFDBCA),
    tertiaryFixedDim = Color(0xFFFFB68E),
    onTertiaryFixed = Color(0xFF331200),
    onTertiaryFixedVariant = Color(0xFF783500)
)

val fontFamilyBaumans = FontFamily(Font(R.font.font_baumans))
val fontFamilyPoiret = FontFamily(Font(R.font.font_poiret))

@Composable
fun GeriOdontoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme, typography = Typography, content = content
    )
}