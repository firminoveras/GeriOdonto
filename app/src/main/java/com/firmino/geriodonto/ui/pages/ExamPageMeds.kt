package com.firmino.geriodonto.ui.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.firmino.geriodonto.companions.MaterialSymbol
import com.firmino.geriodonto.companions.highlightedText
import com.firmino.geriodonto.companions.roundedCornerListShape
import com.firmino.geriodonto.data.PatientState
import com.firmino.geriodonto.data.database.Med
import com.firmino.geriodonto.data.database.MedListType
import com.firmino.geriodonto.ui.widgets.ExamMedItem
import com.firmino.geriodonto.ui.widgets.ExamSearchBar
import com.firmino.geriodonto.viewmodel.MedViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamPageMeds(
    viewModel: MedViewModel,
    patient: PatientState,
    onSearchStateChange: (Boolean) -> Unit,
    onAdd: (Med) -> Unit,
    onRemove: (Med) -> Unit,
) {
    val meds by viewModel.medsList.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            item { Spacer(Modifier.height(58.dp)) }
            itemsIndexed(items = patient.medList.filter { it.type == MedListType.PRE }.toList(), key = { _, item -> item.name + item.description }) { index, item ->
                ExamMedItem(
                    med = item,
                    onRemove = onRemove,
                    shape = roundedCornerListShape(index = index, total = patient.medList.filter { it.type == MedListType.PRE }.size),
                    viewModel = viewModel,
                    patient = patient,
                )

            }
        }

        ExamSearchBar(
            onSearchStateChange = onSearchStateChange,
            placeholderText = "Adicionar medicamento...",
            content = { query, onDone ->
                viewModel.onSearchQueryChanged(query, patient.medList)
                if (query.isNotBlank()) {
                    LazyColumn {
                        items(items = meds.take(20), key = { it.med.id }) {
                            ListItem(
                                modifier = Modifier
                                    .clickable {
                                        onDone()
                                        onAdd(it.toMed(type = MedListType.PRE))
                                    }
                                    .fillMaxWidth(),

                                colors = ListItemDefaults.colors(
                                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                                ),
                                headlineContent = {
                                    Text(highlightedText(it.med.name, query))
                                },
                                supportingContent = {
                                    if (it.med.description.isNotBlank()) {
                                        Text(highlightedText(it.med.description, query))
                                    }
                                },
                            )
                        }
                    }
                } else if (patient.conditionsList.map { it.commonMeds to it.name }.isNotEmpty()) {
                    val scope = rememberCoroutineScope()
                    patient.conditionsList.map { it.commonMeds to it.name }.filter { it.first.isNotEmpty() }.forEach { result ->
                        Text(
                            modifier = Modifier.padding(start = 12.dp, top = 8.dp),
                            text = result.second,
                            style = MaterialTheme.typography.titleSmall,
                        )
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            item { Spacer(modifier = Modifier.width(0.dp)) }
                            items(result.first) { name ->
                                ElevatedSuggestionChip(
                                    onClick = {
                                        scope.launch {
                                            val med = viewModel.getMed(name)
                                            if (med != null) {
                                                onAdd(med.toMed(result.second, MedListType.PRE))
                                            }
                                            onDone()
                                        }
                                    },
                                    icon = { MaterialSymbol(iconName = "medication") },
                                    label = { Text(name.replaceFirstChar { it.uppercase() }.replace("_", " ")) },
                                )
                            }
                            item { Spacer(modifier = Modifier.width(0.dp)) }
                        }
                        HorizontalDivider()
                    }
                }
            },
        )
    }
}