package com.firmino.geriodonto.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.firmino.geriodonto.data.datastorage.SettingsRepository
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.PaletteStyle
import com.materialkolor.rememberDynamicMaterialThemeState

@Composable
fun GeriOdontoTheme(
    settingLightMode: SettingsRepository.LightMode,
    settingAccentColor: SettingsRepository.AccentColor,
    content: @Composable () -> Unit,
    settingOledMode: SettingsRepository.OledMode,
    settingPallete: SettingsRepository.Palette,
) {
    val darkMode = when (settingLightMode) {
        SettingsRepository.LightMode.DARK -> true
        SettingsRepository.LightMode.LIGHT -> false
        else -> isSystemInDarkTheme()
    }

    val context = LocalContext.current
    val seedColorFromSetting = settingAccentColor.color
    val finalSeedColor = remember(seedColorFromSetting) {
        seedColorFromSetting ?: if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) dynamicLightColorScheme(context).primary
        else Color(33, 150, 243, 255)
    }

    val pallete = when (settingPallete) {
        SettingsRepository.Palette.TONALSPOT -> PaletteStyle.TonalSpot
        SettingsRepository.Palette.NEUTRAL -> PaletteStyle.Neutral
        SettingsRepository.Palette.VIBRANT -> PaletteStyle.Vibrant
        SettingsRepository.Palette.EXPRESSIVE -> PaletteStyle.Expressive
        SettingsRepository.Palette.RAINBOW -> PaletteStyle.Rainbow
        SettingsRepository.Palette.FRUITSALAD -> PaletteStyle.FruitSalad
        SettingsRepository.Palette.MONOCHROME -> PaletteStyle.Monochrome
        SettingsRepository.Palette.FIDELITY -> PaletteStyle.Fidelity
        SettingsRepository.Palette.CONTENT -> PaletteStyle.Content
    }

    val colorSchemeState = rememberDynamicMaterialThemeState(
        seedColor = finalSeedColor,
        isDark = darkMode,
        isAmoled = settingOledMode == SettingsRepository.OledMode.ON,
        style = pallete,
    )

    DynamicMaterialTheme(
        state = colorSchemeState,
        animate = true,
        typography = Typography,
        content = content,
    )
}