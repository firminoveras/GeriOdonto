package com.firmino.geriodonto.data.datastorage

import android.content.Context
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
    }

    enum class LightMode(val symbol: String) {
        AUTO("night_sight_auto"),
        DARK("dark_mode"),
        LIGHT("light_mode"),
    }

    enum class AccentColor(val symbol: String) {
        AUTO("colorize"),
        GREEN("eco"),
        PURPLE("air"),
        BLUE("water_drop"),
        RED("local_fire_department"),
        ORANGE("nutrition"),
        BROWN("cookie"),
        YELLOW("wb_sunny"),
        WHITE("cloud")
    }

    data class UserSettings(
        val lightMode: LightMode,
        val accentColor: AccentColor,
    )

    val settings: Flow<UserSettings> = context.dataStore.data.catch { e ->
        if (e is IOException) emit(emptyPreferences()) else throw e
    }.map { preferences ->
        UserSettings(
            lightMode = LightMode.entries.find { it.name == preferences[LIGHT_MODE] } ?: LightMode.AUTO,
            accentColor = AccentColor.entries.find { it.name == preferences[ACCENT_COLOR] } ?: AccentColor.AUTO,
        )
    }

    suspend fun saveLightMode(value: String) = context.dataStore.edit { preferences ->
        preferences[LIGHT_MODE] = LightMode.entries.find { it.name == value }?.name ?: LightMode.AUTO.name
    }

    suspend fun saveAccentColor(value: String) = context.dataStore.edit { preferences ->
        preferences[ACCENT_COLOR] = AccentColor.entries.find { it.name == value }?.name ?: AccentColor.AUTO.name
    }
}