package com.firmino.geriodonto.ui.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.firmino.geriodonto.companions.MaterialSymbol
import com.firmino.geriodonto.companions.highlightedText
import com.firmino.geriodonto.companions.roundedCornerListShape
import com.firmino.geriodonto.data.MedicalCondition
import com.firmino.geriodonto.data.database.Med
import com.firmino.geriodonto.data.database.MedListType
import com.firmino.geriodonto.data.database.MedWithInteractions
import com.firmino.geriodonto.ui.widgets.ExamMedItem
import com.firmino.geriodonto.ui.widgets.ExamSearchPage
import kotlinx.coroutines.launch

@Composable
fun ExamPageMeds(
    medList: Set<Med>,
    meds: List<MedWithInteractions>,
    conditionsList: Set<MedicalCondition>,
    onSearchStateChange: (Boolean) -> Unit,
    onSearchQueryChanged: (String, Set<Med>) -> Unit,
    onAdd: (Med) -> Unit,
    onRemove: (Med) -> Unit,
) {
    ExamSearchPage(
        onSearchStateChange = onSearchStateChange,
        placeholderText = "Adicionar medicamento...",
        lazyContent = {
            itemsIndexed(items = medList.filter { it.type == MedListType.PRE }.toList(), key = { _, item -> item.name + item.description }) { index, item ->
                ExamMedItem(
                    med = item,
                    onRemove = onRemove,
                    shape = roundedCornerListShape(index = index, total = medList.filter { it.type == MedListType.PRE }.size),
                    meds = meds,
                    medList = medList,
                )
            }
        },
        searchContent = { query, onDone ->
            onSearchQueryChanged(query, medList)
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
            } else if (conditionsList.map { it.commonMeds to it.name }.isNotEmpty()) {
                val scope = rememberCoroutineScope()
                conditionsList.map { it.commonMeds to it.name }.filter { it.first.isNotEmpty() }.forEach { result ->
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
                                        val med = meds.firstOrNull { it.med.id == name }
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