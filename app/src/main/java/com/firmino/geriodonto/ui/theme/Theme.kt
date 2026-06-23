package com.firmino.geriodonto.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.firmino.geriodonto.data.datastorage.SettingsRepository

@Composable
fun GeriOdontoTheme(
    settingLightMode: SettingsRepository.LightMode,
    settingAccentColor: SettingsRepository.AccentColor,
    content: @Composable () -> Unit,
) {
    val darkMode = when (settingLightMode) {
        SettingsRepository.LightMode.DARK -> true
        SettingsRepository.LightMode.LIGHT -> false
        else -> isSystemInDarkTheme()
    }

    val scheme = when (settingAccentColor) {
        SettingsRepository.AccentColor.WHITE -> schemeAlgodao.get(darkMode)
        SettingsRepository.AccentColor.GREEN -> schemeHortela.get(darkMode)
        SettingsRepository.AccentColor.PURPLE -> schemeLavanda.get(darkMode)
        SettingsRepository.AccentColor.BLUE -> schemeHortensia.get(darkMode)
        SettingsRepository.AccentColor.RED -> schemeGuarana.get(darkMode)
        SettingsRepository.AccentColor.ORANGE -> schemeCitrus.get(darkMode)
        SettingsRepository.AccentColor.BROWN -> schemeCacau.get(darkMode)
        SettingsRepository.AccentColor.YELLOW -> schemeMaracuja.get(darkMode)
        else -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val context = LocalContext.current
                if (darkMode) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            } else {
                schemeHortensia.get(darkMode)
            }
        }

    }

    MaterialTheme(
        colorScheme = scheme,
        typography = Typography,
        content = content,
    )
}