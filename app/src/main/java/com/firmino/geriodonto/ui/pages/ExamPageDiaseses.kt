package com.firmino.geriodonto.ui.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.firmino.geriodonto.companions.MaterialSymbol
import com.firmino.geriodonto.companions.roundedCornerListShape
import com.firmino.geriodonto.data.MedicalCondition
import com.firmino.geriodonto.data.medicalConditionsList
import com.firmino.geriodonto.ui.widgets.ExamSearchBar
import com.firmino.geriodonto.ui.widgets.HighlightedText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamPageDiaseses(
    medicalConditionList: List<MedicalCondition>,
    onSearchStateChange: (Boolean) -> Unit,
    onAdd: (MedicalCondition) -> Unit,
    onRemove: (MedicalCondition) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            item {
                Spacer(Modifier.height(58.dp))
            }
            itemsIndexed(items = medicalConditionList, key = { _, item -> item.name}) { index, item ->
                ExamDiaseseItem(
                    shape = roundedCornerListShape(index = index, total = medicalConditionList.size),
                    medicalCondition = item,
                    onRemove = { removeItem -> onRemove(removeItem) },
                )
            }
        }

        ExamSearchBar(
            onSearchStateChange = onSearchStateChange,
            content = { query, onDone ->
                if (query.isNotEmpty()) {
                    medicalConditionsList.map { it.name to it.description }.filter {
                        (it.first + " " + it.second).contains(query, ignoreCase = true)
                    }.take(20).forEach { result ->
                        ListItem(
                            modifier = Modifier
                                .clickable {
                                    onDone()
                                    medicalConditionsList.find { it.name == result.first }?.let { condition ->
                                        onAdd(condition)
                                    }
                                }
                                .fillMaxWidth(),
                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                            ),
                            headlineContent = {
                                HighlightedText(result.first, query)
                            },
                            supportingContent = {
                                if (result.second.isNotBlank()) {
                                    HighlightedText(result.second, query)
                                }
                            },
                        )
                    }
                }
            },
        )
    }
}

@Composable
fun ExamDiaseseItem(
    medicalCondition: MedicalCondition,
    onRemove: (MedicalCondition) -> Unit,
    shape: CornerBasedShape,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        onClick = { isExpanded = !isExpanded },
        shape = shape,
    ) {
        Column(Modifier.fillMaxWidth()) {
            Box(Modifier.padding(vertical = 12.dp).fillMaxWidth()) {
                Column(Modifier.padding(start = 16.dp, end = 64.dp)) {
                    Text(
                        text = medicalCondition.name,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Text(
                        text = medicalCondition.description,
                        style = MaterialTheme.typography.bodySmall.copy(
                            textAlign = TextAlign.Justify,
                            hyphens = Hyphens.Auto,
                            letterSpacing = TextUnit.Unspecified,
                            lineBreak = LineBreak.Paragraph.copy(
                                strategy = LineBreak.Strategy.HighQuality,
                                strictness = LineBreak.Strictness.Strict,
                                wordBreak = LineBreak.WordBreak.Phrase,
                            ),
                        ),
                    )
                }

                IconButton(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 12.dp),
                    onClick = { onRemove(medicalCondition) },
                    content = { MaterialSymbol("delete") },
                )
            }
            AnimatedVisibility(isExpanded) {
                Column {
                    medicalCondition.commonRisks.forEach {
                        HorizontalDivider(color = MaterialTheme.colorScheme.surface)
                        Row(
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 12.dp)
                                .heightIn(min = 32.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            MaterialSymbol(it.category.symbolName)
                            Column {
                                Text(
                                    text = "Risco ${it.category.description}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                )
                                Text(text = it.text, style = MaterialTheme.typography.labelMedium)
                            }
                        }

                    }
                }
            }
        }
    }
}