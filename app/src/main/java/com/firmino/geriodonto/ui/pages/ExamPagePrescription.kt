package com.firmino.geriodonto.ui.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.firmino.geriodonto.companions.MaterialSymbol
import com.firmino.geriodonto.companions.highlightedText
import com.firmino.geriodonto.companions.roundedCornerListShape
import com.firmino.geriodonto.data.MedClass
import com.firmino.geriodonto.ui.widgets.ExamMedItem
import com.firmino.geriodonto.ui.widgets.ExamSearchPage
import com.firmino.geriodonto.viewmodel.Med
import com.firmino.geriodonto.viewmodel.MedListType

@Composable
fun ExamPagePrescription(
    medList: Set<Med>,
    filteredMeds: List<Med>,
    suggestionMedList: List<Med>,
    onSuggestionMedClassChange: (MedClass?) -> Unit,
    onSearch: (String) -> Unit,
    onSearchStateChange: (Boolean) -> Unit,
    onAdd: (Med) -> Unit,
    onRemove: (Med) -> Unit,
) {
    ExamSearchPage(
        onSearchStateChange = onSearchStateChange,
        placeholderText = "Prescrever medicamento...",
        itemsContent = {
            itemsIndexed(items = medList.filter { it.type == MedListType.POS }, key = { _, item -> item.name + item.description }) { index, item ->
                ExamMedItem(
                    med = item,
                    onRemove = onRemove,
                    shape = roundedCornerListShape(index = index, total = medList.filter { it.type == MedListType.POS }.size),
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
                var visibleClass by remember { mutableStateOf<MedClass?>(null) }
                LazyColumn {
                    items(items = MedClass.entries, key = { it.name }) {
                        val rotation by animateFloatAsState(
                            targetValue = if (visibleClass == it) 180f else 0f,
                            animationSpec = tween(durationMillis = 300),
                        )

                        Surface(
                            onClick = {
                                visibleClass = it.takeIf { it != visibleClass }
                                onSuggestionMedClassChange(visibleClass)
                            },
                            color = Color.Transparent,
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp, horizontal = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = it.text,
                                    style = MaterialTheme.typography.titleSmall,
                                )
                                MaterialSymbol(iconName = "arrow_drop_down", modifier = Modifier.rotate(rotation))
                            }
                        }
                        AnimatedVisibility(visible = suggestionMedList.isNotEmpty() && visibleClass == it) {
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                item { Spacer(modifier = Modifier.width(0.dp)) }
                                items(suggestionMedList) { med ->
                                    ElevatedSuggestionChip(
                                        onClick = {
                                            onAdd(med.copy(type = MedListType.POS))
                                            onDone()
                                        },
                                        icon = { MaterialSymbol(iconName = "medication") },
                                        label = { Text(med.name) },
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