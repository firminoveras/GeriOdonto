package com.firmino.geriodonto.ui.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalFloatingToolbar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.firmino.geriodonto.companions.MaterialSymbol
import com.firmino.geriodonto.data.datastorage.SettingsRepository
import com.firmino.geriodonto.viewmodel.PatientUiState

data class ConfigUiWrapper(
    val menuType: ActiveSettingMenu,
    val currentSelection: SettingsRepository.AppSettingItem,
    val symbolName: String,
    val options: List<SettingsRepository.AppSettingItem>,
    val onSelect: (String) -> Unit,
)

enum class ActiveSettingMenu(val text: String) {
    NONE(""),
    ACCENT_COLOR("Tema"),
    OLED_MODE("Modo OLED"),
    LIGHT_MODE("Modo Escuro"),
    PALETTE("Palheta")
}

data class MenuSettingsState(
    val lightMode: SettingsRepository.LightMode,
    val accentColor: SettingsRepository.AccentColor,
    val oledMode: SettingsRepository.OledMode,
    val palette: SettingsRepository.Palette,
)

sealed interface MenuEvent {
    data object SeedDatabase : MenuEvent
    data object Clear : MenuEvent
    data object AddClick : MenuEvent
    data object InfoClick : MenuEvent
    data object PolicyClick : MenuEvent
    data class LightModeChange(val mode: SettingsRepository.LightMode) : MenuEvent
    data class AccentColorChange(val color: SettingsRepository.AccentColor) : MenuEvent
    data class OledModeChange(val mode: SettingsRepository.OledMode) : MenuEvent
    data class PaletteChange(val palette: SettingsRepository.Palette) : MenuEvent
}

@Composable
fun BoxScope.ExamMenuToolbar(
    uiState: PatientUiState,
    onEvent: (MenuEvent) -> Unit,
    settingsState: MenuSettingsState,
) {
    var showSettings by remember { mutableStateOf(false) }
    var activeMenu by remember { mutableStateOf(ActiveSettingMenu.NONE) }

    if (!showSettings && activeMenu != ActiveSettingMenu.NONE) {
        activeMenu = ActiveSettingMenu.NONE
    }
    val settingsItems = remember(settingsState) {
        listOf(
            ConfigUiWrapper(
                menuType = ActiveSettingMenu.ACCENT_COLOR,
                currentSelection = settingsState.accentColor,
                symbolName = "colors",
                options = SettingsRepository.AccentColor.entries,
                onSelect = { name -> onEvent(MenuEvent.AccentColorChange(SettingsRepository.AccentColor.valueOf(name))) },
            ),
            ConfigUiWrapper(
                menuType = ActiveSettingMenu.PALETTE,
                currentSelection = settingsState.palette,
                symbolName = "palette",
                options = SettingsRepository.Palette.entries,
                onSelect = { name -> onEvent(MenuEvent.PaletteChange(SettingsRepository.Palette.valueOf(name))) },
            ),

            ConfigUiWrapper(
                menuType = ActiveSettingMenu.OLED_MODE,
                currentSelection = settingsState.oledMode,
                symbolName = "contrast_circle",
                options = SettingsRepository.OledMode.entries,
                onSelect = { name -> onEvent(MenuEvent.OledModeChange(SettingsRepository.OledMode.valueOf(name))) },
            ),
            ConfigUiWrapper(
                menuType = ActiveSettingMenu.LIGHT_MODE,
                currentSelection = settingsState.lightMode,
                symbolName = "dark_mode",
                options = SettingsRepository.LightMode.entries,
                onSelect = { name -> onEvent(MenuEvent.LightModeChange(SettingsRepository.LightMode.valueOf(name))) },
            ),
        )
    }

    VerticalFloatingToolbar(
        modifier = Modifier.align(Alignment.BottomEnd),
        expanded = true,
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(MenuEvent.AddClick) }) {
                MaterialSymbol(iconName = if (uiState.isEmpty) "add" else "edit")
            }
        },
    ) {
        settingsItems.forEach { item ->
            SettingIcon(
                visible = showSettings && (activeMenu == ActiveSettingMenu.NONE || activeMenu == item.menuType),
                symbol = item.symbolName,
                extend = activeMenu == item.menuType,
                optionsList = item.options,
                onClick = item.onSelect,
                onExtendChange = { isExtended ->
                    activeMenu = if (isExtended) item.menuType else ActiveSettingMenu.NONE
                },
            )
        }

        AnimatedVisibility(visible = activeMenu == ActiveSettingMenu.NONE) {
            IconButton(
                onClick = { showSettings = !showSettings },
                colors = IconButtonDefaults.iconButtonColors(containerColor = if (showSettings) MaterialTheme.colorScheme.surfaceContainerLow else Color.Unspecified),
                content = { MaterialSymbol(iconName = "settings", filled = showSettings) },
            )
        }

        AnimatedVisibility(visible = !showSettings) {
            Column {
                IconButton(onClick = { onEvent(MenuEvent.InfoClick) }) { MaterialSymbol(iconName = "license") }
                IconButton(onClick = { onEvent(MenuEvent.PolicyClick) }) { MaterialSymbol(iconName = "policy") }
                if (!uiState.isEmpty) {
                    IconButton(
                        onClick = {
                            onEvent(MenuEvent.Clear)
                            onEvent(MenuEvent.AddClick)
                        },
                        content = { MaterialSymbol(iconName = "add") },
                    )
                }
            }
        }
    }

    SettingLabel(
        modifier = Modifier
            .padding(end = 76.dp)
            .align(Alignment.BottomEnd),
        visible = showSettings || activeMenu != ActiveSettingMenu.NONE,
        title = if (activeMenu != ActiveSettingMenu.NONE) activeMenu.text else "Configurações",
        subtitle = when (activeMenu) {
            ActiveSettingMenu.NONE -> ""
            ActiveSettingMenu.ACCENT_COLOR -> settingsState.accentColor.label
            ActiveSettingMenu.OLED_MODE -> settingsState.oledMode.label
            ActiveSettingMenu.LIGHT_MODE -> settingsState.lightMode.label
            ActiveSettingMenu.PALETTE -> settingsState.palette.label
        },
    )

    SettingLabel(
        modifier = Modifier
            .padding(end = 76.dp)
            .align(Alignment.BottomEnd),
        visible = !showSettings && !uiState.isEmpty,
        title = "Continuar",
        subtitle = uiState.name.text.toString(),
    )
}

@Composable
fun SettingLabel(
    modifier: Modifier = Modifier,
    visible: Boolean,
    title: String,
    subtitle: String = "",
) {
    AnimatedVisibility(modifier = modifier, visible = visible, enter = fadeIn(), exit = fadeOut()) {
        Column(
            modifier = Modifier
                .height(56.dp)
                .background(color = MaterialTheme.colorScheme.surfaceContainer, shape = MaterialTheme.shapes.extraLarge)
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End,
        ) {
            Text(
                modifier = Modifier.animateContentSize(),
                text = title,
                style = MaterialTheme.typography.bodyMedium,
            )
            AnimatedVisibility(visible = subtitle.isNotEmpty()) {
                Text(
                    modifier = Modifier.animateContentSize(),
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

@Composable
fun SettingIcon(
    visible: Boolean,
    symbol: String,
    extend: Boolean,
    optionsList: List<SettingsRepository.AppSettingItem>,
    onClick: (String) -> Unit,
    onExtendChange: (Boolean) -> Unit,
) {
    Column {
        AnimatedVisibility(visible = visible) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AnimatedVisibility(visible = extend) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        optionsList.forEach { entry ->
                            IconButton(
                                onClick = {
                                    onExtendChange(false)
                                    onClick(entry.label)
                                },
                                content = { MaterialSymbol(iconName = entry.symbol) },
                            )
                        }
                    }
                }
                IconButton(
                    onClick = { onExtendChange(!extend) },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = if (extend) MaterialTheme.colorScheme.surfaceContainerLow else Color.Unspecified,
                    ),
                    content = { MaterialSymbol(iconName = symbol, filled = extend) },
                )
            }
        }
    }
}
