package com.firmino.geriodonto.ui.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.firmino.geriodonto.companions.MaterialSymbol
import com.firmino.geriodonto.companions.highlightedText
import com.firmino.geriodonto.companions.roundedCornerListShape
import com.firmino.geriodonto.data.PatientState
import com.firmino.geriodonto.data.database.Med
import com.firmino.geriodonto.ui.widgets.ExamSearchBar
import com.firmino.geriodonto.viewmodel.MedViewModel
import kotlinx.coroutines.launch

@Composable
fun ExamPagePrescription(
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
            itemsIndexed(items = patient.prescriptionList.toList(), key = { _, item -> item.name + item.description }) { index, item ->
                ExamMedItem(
                    med = item,
                    onRemove = onRemove,
                    shape = roundedCornerListShape(index = index, total = patient.prescriptionList.size),
                )
            }
        }

        ExamSearchBar(
            onSearchStateChange = onSearchStateChange,
            placeholderText = "Prescrever medicamento...",
            content = { query, onDone ->
                viewModel.onSearchQueryChanged(query)
                if (query.isNotBlank()) {
                    LazyColumn {
                        items(items = meds.take(20), key = { it.med.id }) {
                            ListItem(
                                modifier = Modifier
                                    .clickable {
                                        onDone()
                                        onAdd(it.toMed())
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
                } else {
                    val scope = rememberCoroutineScope()
                    val items = meds.groupBy { it.med.medClass }.values.toList()
                    LazyColumn {
                        items(items = items, key = { it.first().med.medClass.name }) {
                            var visible by remember { mutableStateOf(false) }
                            Surface(
                                onClick = { visible = !visible },
                                color = Color.Transparent,
                                shape = MaterialTheme.shapes.extraLarge,
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp, horizontal = 12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = it.first().med.medClass.text,
                                        style = MaterialTheme.typography.titleSmall,
                                    )
                                    MaterialSymbol(iconName = "expand_circle_down", filled = visible)
                                }
                            }
                            AnimatedVisibility(visible = visible) {
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                ) {
                                    item { Spacer(modifier = Modifier.width(0.dp)) }
                                    items(it) { med ->
                                        ElevatedSuggestionChip(
                                            onClick = {
                                                scope.launch {
                                                    onAdd(med.toMed())
                                                    onDone()
                                                }
                                            },
                                            icon = { MaterialSymbol(iconName = "medication") },
                                            label = { Text(med.med.name) },
                                        )
                                    }
                                    item { Spacer(modifier = Modifier.width(0.dp)) }
                                }
                            }
                            HorizontalDivider()
                        }
                    }
                }
            },
        )
    }
}