package com.firmino.geriodonto.ui.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.firmino.geriodonto.companions.MaterialSymbol
import com.firmino.geriodonto.companions.highlightedText
import com.firmino.geriodonto.companions.roundedCornerListShape
import com.firmino.geriodonto.data.MedicalCondition
import com.firmino.geriodonto.data.database.Med
import com.firmino.geriodonto.ui.widgets.ExamSearchBar
import com.firmino.geriodonto.viewmodel.MedViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamPageMeds(
    medList: List<Med>,
    medicalConditionList: List<MedicalCondition>,
    viewModel: MedViewModel,
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
            itemsIndexed(items = medList, key = { _, item -> item.name + item.description }) { index, item ->
                ExamMedItem(
                    med = item,
                    onRemove = onRemove,
                    shape = roundedCornerListShape(index = index, total = medList.size),
                )
            }
        }

        ExamSearchBar(
            onSearchStateChange = onSearchStateChange,
            content = { query, onDone ->
                viewModel.onSearchQueryChanged(query)
                if (query.isNotBlank()) {
                    meds.take(20).forEach { result ->
                        ListItem(
                            modifier = Modifier
                                .clickable {
                                    onDone()
                                    onAdd(result.toMed())
                                }
                                .fillMaxWidth(),

                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                            ),
                            headlineContent = {
                                Text(highlightedText(result.med.name, query))
                            },
                            supportingContent = {
                                if (result.med.description.isNotBlank()) {
                                    Text(highlightedText(result.med.description, query))
                                }
                            },
                        )
                    }
                } else if (medicalConditionList.map { it.commonMeds to it.name }.isNotEmpty()) {
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = "Sugestões personalizadas",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    val scope = rememberCoroutineScope()
                    medicalConditionList.map { it.commonMeds to it.name }.forEach { result ->
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
                                                onAdd(med.toMed(result.second))
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

@Composable
fun ExamMedItem(
    med: Med,
    onRemove: (Med) -> Unit,
    shape: CornerBasedShape,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        onClick = { isExpanded = !isExpanded },
        shape = shape,
    ) {
        Column(Modifier.fillMaxWidth()) {
            Box(
                Modifier
                    .padding(vertical = 12.dp)
                    .fillMaxWidth(),
            ) {
                Column(Modifier.padding(start = 16.dp, end = 48.dp)) {
                    Text(
                        text = med.name,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                    )
                    Text(
                        text = med.principleActive,
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        text = med.medClass.text,
                        style = MaterialTheme.typography.bodySmallEmphasized,
                    )
                    if (med.addedBy.isNotBlank()) {
                        Text(
                            text = "Para ${med.addedBy}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }

                    AnimatedVisibility(visible = !isExpanded && med.risks.isNotEmpty()) {
                        Text(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .background(color = MaterialTheme.colorScheme.surfaceContainer, shape = RoundedCornerShape(32.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            text = "${med.risks.size} possíveis riscos",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }

                IconButton(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 12.dp),
                    onClick = { onRemove(med) },
                    content = { MaterialSymbol("delete") },
                )
            }
            AnimatedVisibility(isExpanded) {
                Column {
                    HorizontalDivider(color = MaterialTheme.colorScheme.surface)
                    Text(
                        modifier = Modifier.padding(start = 12.dp, top = 8.dp),
                        text = "Descrição",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 12.dp, end = 12.dp, bottom = 8.dp)
                            .fillMaxWidth(1f),
                        text = med.description,
                        textAlign = TextAlign.Justify,
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

                    HorizontalDivider(color = MaterialTheme.colorScheme.surface)
                    Text(
                        modifier = Modifier.padding(start = 12.dp, top = 8.dp),
                        text = "Indicações",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Text(
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp),
                        text = med.byDisease,
                        style = MaterialTheme.typography.labelSmall.copy(
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
                    med.risks.forEach {
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