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
import com.firmino.geriodonto.ui.widgets.ExamMedItem
import com.firmino.geriodonto.ui.widgets.ExamSearchPage
import com.firmino.geriodonto.viewmodel.Med
import com.firmino.geriodonto.viewmodel.MedListType
import kotlinx.coroutines.launch

@Composable
fun ExamPageMeds(
    medList: Set<Med>,
    filteredMeds: List<Med>,
    onSearch: (String) -> Unit,
    conditionsList: Set<MedicalCondition>,
    onSearchStateChange: (Boolean) -> Unit,
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
                                .clickable {
                                    onDone()
                                    onAdd(it.copy(type = MedListType.PRE))
                                }
                                .fillMaxWidth(),

                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                            ),
                            headlineContent = {
                                Text(highlightedText(it.name, query))
                            },
                            supportingContent = {
                                if (it.description.isNotBlank()) {
                                    Text(highlightedText(it.description, query))
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
//                                        val med = meds.firstOrNull { it.med.id == name }
//                                        if (med != null) {
//                                            onAdd(med.toMed(result.second, MedListType.PRE))
//                                        }
//                                        onDone()
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