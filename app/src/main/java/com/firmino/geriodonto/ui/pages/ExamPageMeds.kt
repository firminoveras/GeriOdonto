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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.firmino.geriodonto.companions.MaterialSymbol
import com.firmino.geriodonto.companions.highlightedText
import com.firmino.geriodonto.companions.roundedCornerListShape
import com.firmino.geriodonto.companions.MedicalCondition
import com.firmino.geriodonto.ui.widgets.ExamMedItem
import com.firmino.geriodonto.ui.widgets.ExamSearchPage
import com.firmino.geriodonto.viewmodel.Med
import com.firmino.geriodonto.viewmodel.MedListType

@Composable
fun ExamPageMeds(
    medList: Set<Med>,
    filteredMeds: List<Med>,
    onSearch: (String) -> Unit,
    conditionsList: Set<MedicalCondition>,
    onSearchStateChange: (Boolean) -> Unit,
    onAdd: (Med) -> Unit,
    onRemove: (Med) -> Unit,
    onFindAndAdd: (id: String, type: MedListType, addedBy: String) -> Unit,
) {
    ExamSearchPage(
        onSearchStateChange = onSearchStateChange,
        placeholderText = "Adicionar medicamento...",
        itemsContent = {
            itemsIndexed(items = medList.filter { it.type == MedListType.PRE }.toList(), key = { _, item -> item.name + item.description }) { index, item ->
                ExamMedItem(
                    med = item,
                    onRemove = onRemove,
                    shape = roundedCornerListShape(index = index, total = medList.filter { it.type == MedListType.PRE }.size),
                    usingMedsIds = medList.map { it.id },
                )
            }
        },
        suggestionsContent = { query, onDone ->
            onSearch(query)
            val lazyState = rememberLazyListState()
            LaunchedEffect(filteredMeds, query) { if (filteredMeds.isNotEmpty()) lazyState.scrollToItem(0) }
            if (query.isNotBlank()) {
                LazyColumn(state = lazyState) {
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
                conditionsList.map { it.commonMeds to it.name }.filter { it.first.isNotEmpty() }.forEach { result ->
                    Text(
                        modifier = Modifier.padding(start = 12.dp, top = 8.dp),
                        text = result.second,
                        style = MaterialTheme.typography.titleSmall,
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        item { Spacer(modifier = Modifier.width(0.dp)) }
                        items(result.first) { name ->
                            ElevatedSuggestionChip(
                                onClick = {
                                    onDone()
                                    onFindAndAdd(name, MedListType.PRE, result.second)
                                },
                                icon = { MaterialSymbol(iconName = "medication") },
                                // TODO: Fazer isso ficar melhor com um pair, por exemplo
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