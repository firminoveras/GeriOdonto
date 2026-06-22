package com.firmino.geriodonto.ui.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.firmino.geriodonto.companions.highlightedText
import com.firmino.geriodonto.companions.roundedCornerListShape
import com.firmino.geriodonto.ui.widgets.ExamMedItem
import com.firmino.geriodonto.ui.widgets.ExamSearchPage
import com.firmino.geriodonto.viewmodel.Med
import com.firmino.geriodonto.viewmodel.MedListType

@Composable
fun ExamPagePrescription(
    medList: Set<Med>,
    filteredMeds: List<Med>,
    onSearch: (String) -> Unit,
    onSearchStateChange: (Boolean) -> Unit,
    onAdd: (Med) -> Unit,
    onRemove: (Med) -> Unit,
) {
    ExamSearchPage(
        onSearchStateChange = onSearchStateChange,
        placeholderText = "Prescrever medicamento...",
        lazyContent = {
            itemsIndexed(items = medList.filter { it.type == MedListType.POS }, key = { _, item -> item.name + item.description }) { index, item ->
                ExamMedItem(
                    med = item,
                    onRemove = onRemove,
                    shape = roundedCornerListShape(index = index, total = medList.filter { it.type == MedListType.POS }.size),
                    usingMedsIds = medList.map { it.id },
                )
            }
        },
        searchContent = { query, onDone ->
            onSearch(query)
            if (query.isNotBlank()) {
                LazyColumn {
                    items(items = filteredMeds, key = { it.id }) {
                        ListItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onDone()
                                    onAdd(it.copy(type = MedListType.POS))
                                },
                            colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
                            headlineContent = { Text(highlightedText(it.name, query)) },
                            supportingContent = { if (it.description.isNotBlank()) Text(highlightedText(it.description, query)) },
                        )
                    }
                }
            } else {
                val scope = rememberCoroutineScope()
//                val items = meds.groupBy { it.med.medClass }.values.toList()
//                LazyColumn {
//                    items(items = items, key = { it.first().med.medClass.name }) {
//                        var visible by remember { mutableStateOf(false) }
//                        val rotation by animateFloatAsState(
//                            targetValue = if (visible) 180f else 0f,
//                            animationSpec = tween(durationMillis = 300),
//                        )
//
//                        Surface(
//                            onClick = { visible = !visible },
//                            color = Color.Transparent,
//                        ) {
//                            Row(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(vertical = 8.dp, horizontal = 12.dp),
//                                horizontalArrangement = Arrangement.SpaceBetween,
//                                verticalAlignment = Alignment.CenterVertically,
//                            ) {
//                                Text(
//                                    text = it.first().med.medClass.text,
//                                    style = MaterialTheme.typography.titleSmall,
//                                )
//                                MaterialSymbol(iconName = "arrow_drop_down", modifier = Modifier.rotate(rotation))
//                            }
//                        }
//                        AnimatedVisibility(visible = visible) {
//                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                                item { Spacer(modifier = Modifier.width(0.dp)) }
//                                items(it) { med ->
//                                    ElevatedSuggestionChip(
//                                        onClick = {
//                                            scope.launch {
//                                                onAdd(med.toMed(type = MedListType.POS))
//                                                onDone()
//                                            }
//                                        },
//                                        icon = { MaterialSymbol(iconName = "medication") },
//                                        label = { Text(med.med.name) },
//                                    )
//                                }
//                                item { Spacer(modifier = Modifier.width(0.dp)) }
//                            }
//                        }
//                        HorizontalDivider()
//                    }
//                }
            }
        },
    )
}