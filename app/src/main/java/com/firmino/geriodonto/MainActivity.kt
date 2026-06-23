package com.firmino.geriodonto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalFloatingToolbar
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.firmino.geriodonto.companions.MaterialSymbol
import com.firmino.geriodonto.companions.PocketAlert
import com.firmino.geriodonto.companions.PocketAlertManager
import com.firmino.geriodonto.companions.rememberAppVersion
import com.firmino.geriodonto.ui.sheets.ExamSheet
import com.firmino.geriodonto.ui.sheets.InteractionSheet
import com.firmino.geriodonto.ui.theme.GeriOdontoTheme
import com.firmino.geriodonto.ui.theme.fontFamilyBaumans
import com.firmino.geriodonto.ui.theme.fontFamilyPoiret
import com.firmino.geriodonto.viewmodel.MedViewModel
import com.firmino.geriodonto.viewmodel.PatientStateChangeType
import com.firmino.geriodonto.viewmodel.PatientUiState
import com.firmino.geriodonto.viewmodel.PatientViewModel
import com.firmino.geriodonto.viewmodel.SeedingState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeriOdontoTheme {
                Content()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Content(
    patientViewModel: PatientViewModel = hiltViewModel(),
    medViewModel: MedViewModel = hiltViewModel(),
) {
    val seedingState by medViewModel.seedingState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    var showExamSheet by remember { mutableStateOf(false) }
    var showInteractionSheet by remember { mutableStateOf(false) }
    var focusMode by remember { mutableStateOf(false) }

    val filteredMeds by medViewModel.filteredMedsList.collectAsStateWithLifecycle()
    val suggestionMedsByClass by medViewModel.medsByClass.collectAsStateWithLifecycle()

    val sheetExamState = rememberBottomSheetState(
        initialValue = SheetValue.Hidden,
        enabledValues = setOf(SheetValue.Hidden, SheetValue.Expanded),
    )

    val sheetInteractionSheet = rememberBottomSheetState(
        initialValue = SheetValue.Hidden,
        enabledValues = setOf(SheetValue.Hidden, SheetValue.Expanded),
    )

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
                onAddClick = { showExamSheet = true },
                uiState = patientViewModel.uiState.value,
                onClear = patientViewModel::clear,
                onSeedDatabase = { medViewModel.seedDatabase() },
                seedingState = seedingState,
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
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Menu(
    uiState: PatientUiState,
    seedingState: SeedingState,
    onSeedDatabase: () -> Unit,
    onClear: () -> Unit,
    onAddClick: () -> Unit,
) {
    val appVersion = rememberAppVersion()
    var dataVersion by remember { mutableStateOf("") }
    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onSecondary,
        ),
    )
    val rotationAngle by rememberInfiniteTransition(label = "InfiniteRotationTransition").animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "RotationAngleAnimation",
    )

    Box(Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-400).dp, x = 400.dp)
                .requiredSize(800.dp)
                .alpha(.1f)
                .rotate(rotationAngle)
                .clip(MaterialShapes.Cookie6Sided.toShape()),
            painter = ColorPainter(MaterialTheme.colorScheme.primary),
            contentDescription = null,
        )
        Image(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-300).dp, x = 300.dp)
                .requiredSize(600.dp)
                .alpha(.1f)
                .rotate(rotationAngle)
                .clip(MaterialShapes.Cookie7Sided.toShape()),
            painter = ColorPainter(MaterialTheme.colorScheme.primary),
            contentDescription = null,
        )
        Image(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-150).dp, x = 150.dp)
                .requiredSize(300.dp)
                .alpha(.1f)
                .rotate(rotationAngle)
                .clip(MaterialShapes.Cookie9Sided.toShape()),
            painter = ColorPainter(MaterialTheme.colorScheme.primary),
            contentDescription = null,
        )
        Image(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-200).dp, x = 200.dp)
                .requiredSize(400.dp)
                .alpha(.1f)
                .rotate(rotationAngle)
                .clip(MaterialShapes.Cookie12Sided.toShape()),
            painter = ColorPainter(MaterialTheme.colorScheme.primary),
            contentDescription = null,
        )
        Image(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(y = 400.dp, x = (-400).dp)
                .requiredSize(800.dp)
                .alpha(.1f)
                .rotate(rotationAngle)
                .clip(MaterialShapes.Cookie6Sided.toShape()),
            painter = ColorPainter(MaterialTheme.colorScheme.primary),
            contentDescription = null,
        )
        Image(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(y = 300.dp, x = (-300).dp)
                .requiredSize(600.dp)
                .alpha(.1f)
                .rotate(rotationAngle)
                .clip(MaterialShapes.Cookie7Sided.toShape()),
            painter = ColorPainter(MaterialTheme.colorScheme.primary),
            contentDescription = null,
        )
        Image(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(y = 150.dp, x = (-150).dp)
                .requiredSize(300.dp)
                .alpha(.1f)
                .rotate(rotationAngle)
                .clip(MaterialShapes.Cookie9Sided.toShape()),
            painter = ColorPainter(MaterialTheme.colorScheme.primary),
            contentDescription = null,
        )
        Image(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(y = 200.dp, x = (-200).dp)
                .requiredSize(400.dp)
                .alpha(.1f)
                .rotate(rotationAngle)
                .clip(MaterialShapes.Cookie12Sided.toShape()),
            painter = ColorPainter(MaterialTheme.colorScheme.primary),
            contentDescription = null,
        )

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .size(128.dp)
                    .graphicsLayer(alpha = 0.99f)
                    .drawWithContent {
                        drawContent()
                        drawRect(brush = gradientBrush, blendMode = BlendMode.SrcIn)
                    },
                imageVector = ImageVector.vectorResource(R.drawable.ic_icon),
                contentDescription = null,
            )
            Row {
                Text(
                    text = "Geri",
                    fontFamily = fontFamilyBaumans,
                    style = MaterialTheme.typography.displayLarge,
                )
                Text(
                    text = "Odonto",
                    fontFamily = fontFamilyPoiret,
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Text(
                text = "Prescrição Segura em Odontogeriatria",
                style = MaterialTheme.typography.labelMedium,
            )
            Text(
                modifier = Modifier.padding(top = 6.dp),
                text = appVersion,
                color = MaterialTheme.colorScheme.outline,
                style = MaterialTheme.typography.labelSmall,
            )
            Text(
                text = dataVersion,
                color = MaterialTheme.colorScheme.outline,
                style = MaterialTheme.typography.labelSmall,
            )
            Spacer(Modifier.height(128.dp))
        }


        when (seedingState) {
            is SeedingState.Idle -> {
                onSeedDatabase()
            }

            SeedingState.Verifying -> {
                dataVersion = "Verificando MedData..."
            }

            is SeedingState.Updating -> {
                dataVersion = "Atualizando MedData de v${seedingState.seederData.localVersion} para v${seedingState.seederData.jsonVersion}..."
                PocketAlertManager.show(
                    message = "Atualizando medicamentos...",
                    iconName = "warning",
                    isLoading = true,
                )
            }

            is SeedingState.Error -> {
                dataVersion = "Erro ao Atualizar MedData"
                PocketAlertManager.show(
                    message = "Erro ao atualizar MedData: ${seedingState.message}",
                    iconName = "warning",
                )
            }

            is SeedingState.Updated -> {
                dataVersion = "MedData v${seedingState.seederData.jsonVersion}"
                PocketAlertManager.show(
                    message = "MedData atualizado para v${seedingState.seederData.localVersion}",
                    iconName = "check",
                )
            }

            is SeedingState.Done -> {
                dataVersion = "MedData v${seedingState.seederData.localVersion}"
                VerticalFloatingToolbar(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    expanded = true,
                    floatingActionButton = {
                        FloatingActionButton(onClick = onAddClick) {
                            MaterialSymbol(iconName = if (uiState.isEmpty) "add" else "edit")
                        }
                    },
                ) {
                    IconButton(onClick = {}) { MaterialSymbol(iconName = "info") }
                    IconButton(onClick = {}) { MaterialSymbol(iconName = "policy") }
                    if (!uiState.isEmpty) {
                        IconButton(
                            onClick = {
                                onClear()
                                onAddClick()
                            },
                            content = { MaterialSymbol(iconName = "add") },
                        )
                    }
                }
            }
        }
        PocketAlert()
    }
}