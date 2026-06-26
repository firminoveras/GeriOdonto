package com.firmino.geriodonto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.firmino.geriodonto.companions.PocketAlert
import com.firmino.geriodonto.companions.PocketAlertManager
import com.firmino.geriodonto.ui.sheets.ExamSheet
import com.firmino.geriodonto.ui.sheets.InfoSheet
import com.firmino.geriodonto.ui.sheets.InteractionSheet
import com.firmino.geriodonto.ui.sheets.PolicySheet
import com.firmino.geriodonto.ui.theme.GeriOdontoTheme
import com.firmino.geriodonto.ui.widgets.ExamMenuLogo
import com.firmino.geriodonto.ui.widgets.ExamMenuToolbar
import com.firmino.geriodonto.ui.widgets.MenuEvent
import com.firmino.geriodonto.ui.widgets.MenuSettingsState
import com.firmino.geriodonto.viewmodel.MedViewModel
import com.firmino.geriodonto.viewmodel.PatientStateChangeType
import com.firmino.geriodonto.viewmodel.PatientUiState
import com.firmino.geriodonto.viewmodel.PatientViewModel
import com.firmino.geriodonto.viewmodel.SeedingState
import com.firmino.geriodonto.viewmodel.SettingsUiState
import com.firmino.geriodonto.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val settingsUiState by settingsViewModel.uiState.collectAsState()

            if (settingsUiState is SettingsUiState.Success) {
                GeriOdontoTheme(
                    settingLightMode = (settingsUiState as SettingsUiState.Success).settings.lightMode,
                    settingAccentColor = (settingsUiState as SettingsUiState.Success).settings.accentColor,
                    settingOledMode = (settingsUiState as SettingsUiState.Success).settings.oledMode,
                    settingPallete = (settingsUiState as SettingsUiState.Success).settings.pallete,
                    content = { Content() },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Content(
    patientViewModel: PatientViewModel = hiltViewModel(),
    medViewModel: MedViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()

    val seedingState by medViewModel.seedingState.collectAsStateWithLifecycle()
    val filteredMeds by medViewModel.filteredMedsList.collectAsStateWithLifecycle()
    val suggestionMedsByClass by medViewModel.medsByClass.collectAsStateWithLifecycle()
    val settingsUiState by settingsViewModel.uiState.collectAsState()

    val sheetExamState = rememberBottomSheetState(initialValue = SheetValue.Hidden, enabledValues = setOf(SheetValue.Hidden, SheetValue.Expanded))
    val sheetInteractionSheet = rememberBottomSheetState(initialValue = SheetValue.Hidden, enabledValues = setOf(SheetValue.Hidden, SheetValue.Expanded))
    val sheetPolicySheet = rememberBottomSheetState(initialValue = SheetValue.Hidden)
    val sheetInfoSheet = rememberBottomSheetState(initialValue = SheetValue.Hidden)

    var showExamSheet by remember { mutableStateOf(false) }
    var showInteractionSheet by remember { mutableStateOf(false) }
    var showPolicySheet by remember { mutableStateOf(false) }
    var showInfoSheet by remember { mutableStateOf(false) }
    var focusMode by remember { mutableStateOf(false) }

    patientViewModel.onConditionChanged = { condition, type ->
        PocketAlertManager.show(
            message = "Condição ${condition.name} ${if (type == PatientStateChangeType.ADD) "adicionada" else "removida"}.",
            highlight = condition.name,
            iconName = if (type == PatientStateChangeType.ADD) "add_circle" else "remove_circle",
        )
    }

    patientViewModel.onMedChanged = { med, type ->
        PocketAlertManager.show(
            message = "Medicamento ${med.name} ${if (type == PatientStateChangeType.ADD) "adicionado" else "removido"}.",
            highlight = med.name,
            iconName = if (type == PatientStateChangeType.ADD) "add_circle" else "remove_circle",
        )
    }

    Scaffold { contentPadding ->
        Column(Modifier.padding(contentPadding)) {
            Menu(
                uiState = patientViewModel.uiState.value,
                seedingState = seedingState,
                settingsState = MenuSettingsState(
                    lightMode = (settingsUiState as SettingsUiState.Success).settings.lightMode,
                    accentColor = (settingsUiState as SettingsUiState.Success).settings.accentColor,
                    oledMode = (settingsUiState as SettingsUiState.Success).settings.oledMode,
                    palette = (settingsUiState as SettingsUiState.Success).settings.pallete,
                ),
                onEvent = { event ->
                    when (event) {
                        is MenuEvent.AccentColorChange -> settingsViewModel.saveAccentColor(event.color)
                        is MenuEvent.LightModeChange -> settingsViewModel.saveLightMode(event.mode)
                        is MenuEvent.OledModeChange -> settingsViewModel.saveOledMode(event.mode)
                        is MenuEvent.PaletteChange -> settingsViewModel.savePallete(event.palette)
                        MenuEvent.SeedDatabase -> medViewModel.seedDatabase()
                        MenuEvent.Clear -> patientViewModel.clear()
                        MenuEvent.AddClick -> showExamSheet = true
                        MenuEvent.InfoClick -> showInfoSheet = true
                        MenuEvent.PolicyClick -> showPolicySheet = true
                    }
                },
            )
        }

        if (showExamSheet) {
            ModalBottomSheet(
                onDismissRequest = { showExamSheet = false },
                contentWindowInsets = { BottomSheetDefaults.modalWindowInsets.exclude(WindowInsets.navigationBars) },
                sheetState = sheetExamState,
                containerColor = if (!focusMode) MaterialTheme.colorScheme.surfaceContainerHigh else BottomSheetDefaults.ContainerColor,
            ) {
                ExamSheet(
                    onInteractionButtonClick = { showInteractionSheet = true },
                    onShowTopBarChange = { focusMode = it },
                    uiState = patientViewModel.uiState.value,
                    onEvent = patientViewModel::onEvent,
                    filteredMeds = filteredMeds,
                    onSearch = { medViewModel.onSearchQueryChanged(it) },
                    onFindAndAdd = { id, type, addedBy ->
                        scope.launch {
                            medViewModel.getMedById(id, type, addedBy)?.let {
                                patientViewModel.add(it)
                            }
                        }
                    },
                    suggestionMedList = suggestionMedsByClass,
                    onSuggestionMedClassChange = { medViewModel.onSuggestionMedsQueryChanged(it) },
                )
            }
        }

        if (showInteractionSheet) {
            ModalBottomSheet(
                contentWindowInsets = { BottomSheetDefaults.modalWindowInsets.exclude(WindowInsets.navigationBars) },
                onDismissRequest = { showInteractionSheet = false },
                sheetState = sheetInteractionSheet,
            ) {
                InteractionSheet(
                    onClose = {
                        scope.launch { sheetInteractionSheet.hide() }.invokeOnCompletion {
                            if (!sheetInteractionSheet.isVisible) {
                                showInteractionSheet = false
                            }
                        }
                    },
                    uiState = patientViewModel.uiState.value,
                )
            }
        }

        if (showPolicySheet) {
            ModalBottomSheet(
                contentWindowInsets = { BottomSheetDefaults.modalWindowInsets.exclude(WindowInsets.navigationBars) },
                onDismissRequest = { showPolicySheet = false },
                sheetState = sheetPolicySheet,
            ) {
                PolicySheet()
            }
        }

        if (showInfoSheet) {
            ModalBottomSheet(
                contentWindowInsets = { BottomSheetDefaults.modalWindowInsets.exclude(WindowInsets.navigationBars) },
                onDismissRequest = { showInfoSheet = false },
                sheetState = sheetInfoSheet,
            ) {
                InfoSheet()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Menu(
    uiState: PatientUiState,
    seedingState: SeedingState,
    settingsState: MenuSettingsState,
    onEvent: (MenuEvent) -> Unit,
) {
    var medDataVersion by remember { mutableStateOf("") }

    Box(Modifier.fillMaxSize().padding(bottom = 12.dp)) {
        ExamMenuLogo(medDataVersion)
        when (seedingState) {
            is SeedingState.Idle -> {
                onEvent(MenuEvent.SeedDatabase)
            }

            SeedingState.Verifying -> {
                medDataVersion = "[Verificando]"
            }

            is SeedingState.Updating -> {
                medDataVersion = "[Atualizando v${seedingState.seederData.localVersion} > v${seedingState.seederData.jsonVersion}]"
                PocketAlertManager.show(
                    message = "Atualizando medicamentos...",
                    iconName = "warning",
                    isLoading = true,
                )
            }

            is SeedingState.Error -> {
                medDataVersion = "[Erro]"
                PocketAlertManager.show(
                    message = "Erro ao atualizar MedData: ${seedingState.message}",
                    iconName = "warning",
                )
            }

            is SeedingState.Updated -> {
                medDataVersion = "v${seedingState.seederData.jsonVersion}"
                PocketAlertManager.show(
                    message = "MedData atualizado para v${seedingState.seederData.localVersion}",
                    iconName = "check",
                )
            }

            is SeedingState.Done -> {
                medDataVersion = "v${seedingState.seederData.localVersion}"
                ExamMenuToolbar(
                    uiState = uiState,
                    onEvent = onEvent,
                    settingsState = settingsState,
                )
            }
        }
        PocketAlert()
    }
}
