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

    enum class OledMode(val symbol: String) {
        ON("contrast"),
        OFF("contrast_rtl_off"),
    }

    enum class LightMode(val symbol: String) {
        AUTO("night_sight_auto"),
        DARK("dark_mode"),
        LIGHT("light_mode"),
    }

    enum class AccentColor(val symbol: String, val color: Color?) {
        AUTO("colorize", null),
        GREEN("eco", Color(76, 175, 80, 255)),
        PURPLE("air", Color(103, 58, 183, 255)),
        BLUE("water_drop", Color(33, 150, 243, 255)),
        RED("local_fire_department", Color(244, 67, 54, 255)),
        ORANGE("nutrition", Color(255, 152, 0, 255)),
        BROWN("cookie", Color(140, 75, 30, 255)),
        YELLOW("wb_sunny", Color(255, 235, 59, 255)),
        WHITE("cloud", Color(255, 255, 255, 255))
    }

    enum class Palette(val symbol: String) {
        TONALSPOT("palette"),
        NEUTRAL("tonality"),
        VIBRANT("flare"),
        EXPRESSIVE("brush"),
        RAINBOW("gradient"),
        FRUITSALAD("bubble_chart"),
        MONOCHROME("filter_b_and_w"),
        FIDELITY("colorize"),
        CONTENT("image")
    }

    data class UserSettings(
        val lightMode: LightMode,
        val accentColor: AccentColor,
        val oledMode: OledMode,
        val pallete: Palette
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

    suspend fun saveLightMode(value: String) = context.dataStore.edit { preferences ->
        preferences[LIGHT_MODE] = LightMode.entries.find { it.name == value }?.name ?: LightMode.AUTO.name
    }

    suspend fun saveAccentColor(value: String) = context.dataStore.edit { preferences ->
        preferences[ACCENT_COLOR] = AccentColor.entries.find { it.name == value }?.name ?: AccentColor.AUTO.name
    }

    suspend fun saveOledMode(value: String) = context.dataStore.edit { preferences ->
        preferences[OLED_MODE] = OledMode.entries.find { it.name == value }?.name ?: OledMode.OFF.name
    }

    suspend fun savePallete(value: String) = context.dataStore.edit { preferences ->
        preferences[PALLETE] = Palette.entries.find { it.name == value }?.name ?: Palette.TONALSPOT.name
    }
}