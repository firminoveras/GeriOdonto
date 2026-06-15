package com.firmino.geriodonto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.firmino.geriodonto.companions.MaterialSymbol
import com.firmino.geriodonto.companions.PocketAlert
import com.firmino.geriodonto.companions.PocketAlertManager
import com.firmino.geriodonto.companions.rememberAppVersion
import com.firmino.geriodonto.data.MedicalCondition
import com.firmino.geriodonto.data.PatientState
import com.firmino.geriodonto.data.PatientStateChangeType
import com.firmino.geriodonto.data.database.Med
import com.firmino.geriodonto.data.rememberPatientState
import com.firmino.geriodonto.ui.pages.ExamPageDiaseses
import com.firmino.geriodonto.ui.pages.ExamPageExams
import com.firmino.geriodonto.ui.pages.ExamPageMeds
import com.firmino.geriodonto.ui.pages.ExamPagePersonal
import com.firmino.geriodonto.ui.theme.GeriOdontoTheme
import com.firmino.geriodonto.ui.theme.fontFamilyBaumans
import com.firmino.geriodonto.ui.theme.fontFamilyPoiret
import com.firmino.geriodonto.viewmodel.MedViewModel
import com.firmino.geriodonto.viewmodel.SeedingState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


enum class ExamPages(val text: String, val symbolName: String) {
    PAGE_PERSONAL("Pessoal", "account_box"),
    PAGE_EXAMS("Exames", "labs"),
    PAGE_CONDITIONS("Condições", "medical_information"),
    PAGE_MEDS("Medicamentos", "admin_meds"),
}


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
    viewModel: MedViewModel = hiltViewModel(),
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val patient = rememberPatientState()
    val sheetState = rememberBottomSheetState(
        initialValue = SheetValue.Hidden,
        enabledValues = setOf(SheetValue.Hidden, SheetValue.Expanded),
    )

    patient.onConditionChanged = { condition, type ->
        PocketAlertManager.show(
            message = "Condição ${condition.name} ${if (type == PatientStateChangeType.ADD) "adicionada" else "removida"}.",
            highlight = condition.name,
            iconName = if (type == PatientStateChangeType.ADD) "add_circle" else "remove_circle",
        )
    }

    patient.onMedChanged = { med, type ->
        PocketAlertManager.show(
            message = "Medicamento ${med.name} ${if (type == PatientStateChangeType.ADD) "adicionado" else "removido"}.",
            highlight = med.name,
            iconName = if (type == PatientStateChangeType.ADD) "add_circle" else "remove_circle",
        )
    }

    Scaffold { contentPadding ->
        Column(Modifier.padding(contentPadding)) {
            Menu(
                viewModel = viewModel,
                patient = patient,
                onAddClick = { showBottomSheet = true },
            )
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState,
            ) {
                Exam(
                    viewModel = viewModel,
                    patient = patient,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Menu(
    viewModel: MedViewModel,
    patient: PatientState,
    onAddClick: () -> Unit,
) {
    val seedingState by viewModel.seedingState.collectAsStateWithLifecycle()
    val appVersion = rememberAppVersion()
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
        Text(
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .align(Alignment.BottomStart),
            text = appVersion,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.labelMediumEmphasized,
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
            Spacer(Modifier.height(128.dp))
        }


        when (seedingState) {
            SeedingState.Idle -> {
                viewModel.seedDatabase()
            }

            SeedingState.Loading -> {
                PocketAlertManager.show(
                    message = "Atualizando medicamentos...",
                    iconName = "warning",
                    isLoading = true,
                )
            }

            SeedingState.Success -> {
                VerticalFloatingToolbar(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    expanded = true,
                    floatingActionButton = {
                        FloatingActionButton(onClick = onAddClick) {
                            MaterialSymbol(iconName = if (patient.isEmpty()) "add" else "edit")
                        }
                    },
                ) {
                    IconButton(onClick = {}) { MaterialSymbol(iconName = "info") }
                    IconButton(onClick = {}) { MaterialSymbol(iconName = "policy") }
                    if (patient.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                patient.clear()
                                onAddClick()
                            },
                            content = { MaterialSymbol(iconName = "add") },
                        )
                    }
                }
            }

            is SeedingState.Error -> {
                PocketAlertManager.show(
                    message = "Erro ao atualizar database.",
                    iconName = "erro",
                )
            }
        }
        PocketAlert()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Exam(
    viewModel: MedViewModel,
    patient: PatientState,
) {
    val pagerState = rememberPagerState { ExamPages.entries.size }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    var showTopBar by remember { mutableStateOf(true) }
    var showMenuBar by remember { mutableStateOf(true) }
    var deleteDiasese by remember { mutableStateOf<MedicalCondition?>(null) }
    var deleteMed by remember { mutableStateOf<Med?>(null) }

    if (deleteDiasese != null) {
        AlertDialog(
            onDismissRequest = { deleteDiasese = null },
            title = { Text("Você deseja deletar essa entrada?") },
            text = { Text("Essa ação excluirá a entrada de comorbidade selecionada.") },
            icon = { MaterialSymbol(iconName = "delete") },
            confirmButton = {
                TextButton(
                    content = { Text("Confirmar") },
                    onClick = {
                        patient.remove(deleteDiasese!!)
                        deleteDiasese = null
                    },
                )
            },
            dismissButton = {
                TextButton(
                    onClick = { deleteDiasese = null },
                    content = { Text("Cancelar") },
                )
            },
        )
    }

    if (deleteMed != null) {
        AlertDialog(
            onDismissRequest = { deleteMed = null },
            title = { Text("Você deseja deletar esse medicamento?") },
            text = { Text("Essa ação excluirá a entrada de medicamento selecionada.") },
            icon = { MaterialSymbol(iconName = "delete") },
            confirmButton = {
                TextButton(
                    content = { Text("Confirmar") },
                    onClick = {
                        patient.remove(deleteMed!!)
                        deleteMed = null
                    },
                )
            },
            dismissButton = {
                TextButton(
                    onClick = { deleteMed = null },
                    content = { Text("Cancelar") },
                )
            },
        )
    }

    Box {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) { detectTapGestures { focusManager.clearFocus() } },
        ) {
            AnimatedVisibility(showTopBar) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                    ) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterStart),
                        ) {
                            Text(
                                text = "Nova prescrição",
                                style = MaterialTheme.typography.titleLarge,
                            )
                            if (patient.name.text.isNotEmpty()) {
                                Text(
                                    text = patient.name.text.toString().split(" ").joinToString(" ", limit = 2, truncated = ""),
                                    style = MaterialTheme.typography.labelSmall,
                                    maxLines = 1,
                                )
                            }

                        }
                        Row(Modifier.align(Alignment.CenterEnd)) {
                            IconButton(
                                onClick = { showMenuBar = !showMenuBar },
                                content = { MaterialSymbol(iconName = "page_menu_ios", filled = showMenuBar) },
                            )
                            IconButton(
                                onClick = { patient.clear() },
                                content = { MaterialSymbol(iconName = "delete_forever", filled = patient.isNotEmpty()) },
                            )
                        }
                    }
                    HorizontalDivider()
                    AnimatedVisibility(visible = showMenuBar) {
                        ExamMenu(
                            currentPage = pagerState.currentPage,
                            patient = patient,
                            onChange = { page -> scope.launch { pagerState.animateScrollToPage(page) } },
                        )
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            HorizontalPager(state = pagerState, userScrollEnabled = false) { index ->
                when (index) {
                    ExamPages.PAGE_PERSONAL.ordinal -> {
                        ExamPagePersonal(
                            name = patient.name,
                            genre = patient.genre,
                            age = patient.age,
                            weight = patient.weight,
                            height = patient.height,
                        )
                    }

                    ExamPages.PAGE_EXAMS.ordinal -> {
                        ExamPageExams(
                            genre = patient.genre,
                            renalFunction = patient.renalFunction,
                            hepaticTgo = patient.hepaticTgo,
                            hepaticTgp = patient.hepaticTgp,
                            hasFragile = patient.hasFragile,
                            hasFallHistory = patient.hasFallHistory,
                            onHasFragileChange = { patient.hasFragile = it },
                            onHasFallHistoryChange = { patient.hasFallHistory = it },
                        )
                    }

                    ExamPages.PAGE_CONDITIONS.ordinal -> {
                        ExamPageDiaseses(
                            medicalConditionList = patient.conditionsList.toList(),
                            onSearchStateChange = { showTopBar = it },
                            onAdd = { if (!patient.contains(it)) patient.add(it) },
                            onRemove = { deleteDiasese = it },
                        )
                    }

                    ExamPages.PAGE_MEDS.ordinal -> {
                        ExamPageMeds(
                            medicalConditionList = patient.conditionsList.toList(),
                            viewModel = viewModel,
                            medList = patient.medList.toList(),
                            onSearchStateChange = { showTopBar = it },
                            onAdd = { if (!patient.contains(it)) patient.add(it) },
                            onRemove = { deleteMed = it },
                        )
                    }
                }
            }
        }
        PocketAlert()
    }
}


@Composable
fun ExamMenu(
    currentPage: Int,
    patient: PatientState,
    onChange: (Int) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ExamPages.entries.forEach { page ->
            val current = page.ordinal == currentPage
            val color = if (current) MaterialTheme.colorScheme.primary else Color.Transparent
            val size = when (page) {
                ExamPages.PAGE_CONDITIONS -> patient.conditionsList.size
                ExamPages.PAGE_MEDS -> patient.medList.size
                else -> 0
            }
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                color = color,
                onClick = { onChange(page.ordinal) },
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    BadgedBox(badge = { if (size > 0) Badge { Text("$size") } }) {
                        MaterialSymbol(
                            iconName = page.symbolName,
                            filled = page.ordinal == currentPage,
                            colorFilled = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                    AnimatedVisibility(page.ordinal == currentPage) {
                        Text(
                            text = page.text,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                }
            }
        }
    }
}
