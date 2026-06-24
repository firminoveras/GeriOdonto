package com.firmino.geriodonto.data.datastorage

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsRepository @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        val LIGHT_MODE = stringPreferencesKey("light_mode")
        val ACCENT_COLOR = stringPreferencesKey("accent_color")
        val OLED_MODE = stringPreferencesKey("oled_mode")
        val PALLETE = stringPreferencesKey("opallete")
    }

    interface AppSettingItem {
        val symbol: String
        val label: String
    }

    enum class AccentColor(
        override val symbol: String,
        val color: Color?,
        override val label: String,
    ) : AppSettingItem {
        AUTO("colorize", null, "Automático"),
        GREEN("eco", Color(76, 175, 80, 255), "Natureza"),
        PURPLE("air", Color(103, 58, 183, 255), "Lavanda"),
        BLUE("water_drop", Color(33, 150, 243, 255), "Água"),
        RED("local_fire_department", Color(244, 67, 54, 255), "Fogo"),
        ORANGE("nutrition", Color(255, 152, 0, 255), "Cítrico"),
        BROWN("cookie", Color(140, 75, 30, 255), "Chocolate"),
        YELLOW("wb_sunny", Color(255, 235, 59, 255), "Ensolarado"),
        WHITE("cloud", Color(255, 255, 255, 255), "Algodão")
    }

    enum class OledMode(
        override val symbol: String,
        override val label: String,
    ) : AppSettingItem {
        ON("contrast", "Ligado"),
        OFF("contrast_rtl_off", "Desligado"),
    }

    enum class LightMode(
        override val symbol: String,
        override val label: String,
    ) : AppSettingItem {
        AUTO("night_sight_auto", "Automático"),
        DARK("dark_mode", "Ligado"),
        LIGHT("light_mode", "Desligado"),
    }

    enum class Palette(
        override val symbol: String,
        override val label: String,
    ) : AppSettingItem {
        TONALSPOT("filter_tilt_shift", "Ponto Tonal"),
        NEUTRAL("blur_circular", "Neutro"),
        VIBRANT("stars", "Vibrante"),
        EXPRESSIVE("sentiment_very_satisfied", "Expressivo"),
        RAINBOW("stroke_partial", "Arco-íris"),
        FRUITSALAD("yoshoku", "Salada de Frutas"),
        MONOCHROME("contrast", "Monocromo"),
        FIDELITY("check_circle", "Fidelidade"),
        CONTENT("target", "Conteúdo")
    }

    data class UserSettings(
        val lightMode: LightMode,
        val accentColor: AccentColor,
        val oledMode: OledMode,
        val pallete: Palette,
    )

    val settings: Flow<UserSettings> = context.dataStore.data.catch { e ->
        if (e is IOException) emit(emptyPreferences()) else throw e
    }.map { preferences ->
        UserSettings(
            lightMode = LightMode.entries.find { it.name == preferences[LIGHT_MODE] } ?: LightMode.AUTO,
            accentColor = AccentColor.entries.find { it.name == preferences[ACCENT_COLOR] } ?: AccentColor.AUTO,
            oledMode = OledMode.entries.find { it.name == preferences[OLED_MODE] } ?: OledMode.OFF,
            pallete = Palette.entries.find { it.name == preferences[PALLETE] } ?: Palette.TONALSPOT,
        )
    }

    suspend fun saveLightMode(value: LightMode) = context.dataStore.edit { preferences ->
        preferences[LIGHT_MODE] = value.name
    }

    suspend fun saveAccentColor(value: AccentColor) = context.dataStore.edit { preferences ->
        preferences[ACCENT_COLOR] = value.name
    }

    suspend fun saveOledMode(value: OledMode) = context.dataStore.edit { preferences ->
        preferences[OLED_MODE] = value.name
    }

    suspend fun savePallete(value: Palette) = context.dataStore.edit { preferences ->
        preferences[PALLETE] = value.name
    }
}