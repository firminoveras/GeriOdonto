package com.firmino.geriodonto.ui.sheets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.firmino.geriodonto.companions.MaterialSymbol
import com.firmino.geriodonto.companions.PocketAlert
import com.firmino.geriodonto.data.MedicalCondition
import com.firmino.geriodonto.data.database.Med
import com.firmino.geriodonto.data.database.MedListType
import com.firmino.geriodonto.data.database.MedWithInteractions
import com.firmino.geriodonto.ui.pages.ExamPageConditions
import com.firmino.geriodonto.ui.pages.ExamPageExams
import com.firmino.geriodonto.ui.pages.ExamPageMeds
import com.firmino.geriodonto.ui.pages.ExamPagePersonal
import com.firmino.geriodonto.ui.pages.ExamPagePrescription
import com.firmino.geriodonto.viewmodel.PatientEvent
import com.firmino.geriodonto.viewmodel.PatientUiState
import kotlinx.coroutines.launch

enum class ExamPages(val text: String, val symbolName: String, val description: String) {
    PAGE_PERSONAL("Pessoal", "account_box", "Informe os dados pessoais do paciente."),
    PAGE_EXAMS("Exames", "labs", "Informe os valores de inportantes exames clínicos do paciente."),
    PAGE_CONDITIONS("Condições", "medical_information", "Adicione quais as condições médicas, comorbidades ou doenças crônicas no paciente."),
    PAGE_MEDS("Medicamentos", "admin_meds", "Adicione os medicamentos de uso contínuo que o paciente já faz uso."),
    PAGE_PRESCRIPTION("Prescrição", "outpatient_med", "Adicione medicamentos a serem prescritos ao paciente."),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamSheet(
    uiState: PatientUiState,
    onEvent: (PatientEvent) -> Unit,
    meds: List<MedWithInteractions>,
    onInteractionButtonClick: () -> Unit,
    onShowTopBarChange: (Boolean) -> Unit,
) {
    val pagerState = rememberPagerState { ExamPages.entries.size }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    var showTopBar by remember { mutableStateOf(true) }
    var showMenuBar by remember { mutableStateOf(true) }
    var showAlerts by remember { mutableStateOf(false) }
    var deleteDiasese by remember { mutableStateOf<MedicalCondition?>(null) }
    var deleteMed by remember { mutableStateOf<Med?>(null) }
    var deletePrescription by remember { mutableStateOf<Med?>(null) }

    LaunchedEffect(showTopBar) {
        onShowTopBarChange(showTopBar)
    }

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
                        onEvent(PatientEvent.RemoveCondition(deleteDiasese!!))
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
                        onEvent(PatientEvent.RemoveMed(deleteMed!!))
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

    if (deletePrescription != null) {
        AlertDialog(
            onDismissRequest = { deletePrescription = null },
            title = { Text("Você deseja deletar essa prescrição?") },
            text = { Text("Essa ação excluirá a entrada de prescrição selecionada.") },
            icon = { MaterialSymbol(iconName = "delete") },
            confirmButton = {
                TextButton(
                    content = { Text("Confirmar") },
                    onClick = {
                        onEvent(PatientEvent.RemoveMed(deletePrescription!!))
                        deletePrescription = null
                    },
                )
            },
            dismissButton = {
                TextButton(
                    onClick = { deletePrescription = null },
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
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
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
                            if (uiState.name.text.isNotEmpty()) {
                                Text(
                                    text = uiState.name.text.toString().split(" ").joinToString(" ", limit = 2, truncated = ""),
                                    style = MaterialTheme.typography.labelSmall,
                                    maxLines = 1,
                                )
                            }

                        }
                        Row(Modifier.align(Alignment.CenterEnd)) {
                            IconButton(
                                onClick = { showAlerts = !showAlerts },
                                content = { MaterialSymbol(iconName = if (showAlerts) "notifications_active" else "notifications_off", filled = showAlerts) },
                            )
                            IconButton(
                                onClick = { showMenuBar = !showMenuBar },
                                content = { MaterialSymbol(iconName = "page_menu_ios", filled = showMenuBar) },
                            )
                            IconButton(
                                onClick = { onEvent(PatientEvent.ClearAll) },
                                content = { MaterialSymbol(iconName = "delete_forever", filled = !uiState.isEmpty) },
                            )
                        }
                    }
                    AnimatedVisibility(visible = showMenuBar) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            HorizontalDivider()
                            ExamMenu(
                                currentPage = pagerState.currentPage,
                                medList = uiState.medList,
                                conditionList = uiState.conditionsList,
                                onChange = { page -> scope.launch { pagerState.animateScrollToPage(page) } },
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            HorizontalPager(state = pagerState, userScrollEnabled = false) { index ->
                when (index) {
                    ExamPages.PAGE_PERSONAL.ordinal -> {
                        ExamPagePersonal(
                            name = uiState.name,
                            genre = uiState.genre,
                            age = uiState.age,
                            height = uiState.height,
                            weight = uiState.weight,
                        )
                    }

                    ExamPages.PAGE_EXAMS.ordinal -> {
                        ExamPageExams(
                            renalFunction = uiState.renalFunction,
                            genre = uiState.genre,
                            hepaticTgo = uiState.hepaticTgo,
                            hepaticTgp = uiState.hepaticTgp,
                            hasFallHistory = uiState.hasFallHistory,
                            hasFragile = uiState.hasFragile,
                            onHasFragileChange = { onEvent(PatientEvent.UpdateFragile(it)) },
                            onHasFallHistoryChange = { onEvent(PatientEvent.UpdateFallHistory(it)) },
                        )
                    }

                    ExamPages.PAGE_CONDITIONS.ordinal -> {
                        ExamPageConditions(
                            conditionsList = uiState.conditionsList,
                            onSearchStateChange = { showTopBar = !it },
                            onAdd = { onEvent(PatientEvent.AddCondition(it)) },
                            onRemove = { deleteDiasese = it },
                        )
                    }

                    ExamPages.PAGE_MEDS.ordinal -> {
                        ExamPageMeds(
                            medList = uiState.medList,
                            conditionsList = uiState.conditionsList,
                            meds = meds,
                            onSearchStateChange = { showTopBar = !it },
                            onAdd = { onEvent(PatientEvent.AddMed(it)) },
                            onRemove = { deleteMed = it },
                        )
                    }

                    ExamPages.PAGE_PRESCRIPTION.ordinal -> {
                        ExamPagePrescription(
                            medList = uiState.medList,
                            meds = meds,
                            onSearchStateChange = { showTopBar = !it },
                            onAdd = { onEvent(PatientEvent.AddMed(it)) },
                            onRemove = { deletePrescription = it },
                        )
                    }
                }
            }
        }
        ExtendedFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(horizontal = 12.dp, vertical = 18.dp),
            onClick = { onInteractionButtonClick() },
            text = { Text("Interações") },
            expanded = uiState.interactions.isNotEmpty(),
            icon = {
                BadgedBox(
                    badge = { if (uiState.interactions.isNotEmpty()) Badge { Text("${uiState.interactions.size}") } },
                ) {
                    MaterialSymbol(
                        iconName = "brightness_alert",
                        filled = uiState.interactions.isNotEmpty(),
                    )
                }

            },
        )
        if (showAlerts) PocketAlert()
    }
}


@Composable
fun ExamMenu(
    currentPage: Int,
    medList: Set<Med>,
    conditionList: Set<MedicalCondition>,
    onChange: (Int) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
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
                    ExamPages.PAGE_CONDITIONS -> conditionList.size
                    ExamPages.PAGE_MEDS -> medList.filter { it.type == MedListType.PRE }.size
                    ExamPages.PAGE_PRESCRIPTION -> medList.filter { it.type == MedListType.POS }.size
                    else -> 0
                }
                Surface(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = color,
                    onClick = {
                        if (currentPage == page.ordinal) expanded = !expanded
                        onChange(page.ordinal)
                    },
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
        AnimatedVisibility(visible = expanded) {
            Text(
                modifier = Modifier.padding(horizontal = 24.dp),
                text = ExamPages.entries[currentPage].description,
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
            )
        }
    }
}
