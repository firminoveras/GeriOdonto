package com.firmino.geriodonto.ui.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.firmino.geriodonto.companions.MaterialSymbol
import com.firmino.geriodonto.data.database.InteractionEntity
import com.firmino.geriodonto.data.database.Med
import com.firmino.geriodonto.data.database.MedListType
import com.firmino.geriodonto.data.database.MedWithInteractions

@Composable
fun ExamMedItem(
    med: Med,
    onRemove: (Med) -> Unit,
    shape: CornerBasedShape,
    meds: List<MedWithInteractions>,
    medList: Set<Med>,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isInteractionsExpanded by remember { mutableStateOf(false) }
    var isRisksExpanded by remember { mutableStateOf(false) }
    val interactions = remember { mutableStateListOf<Pair<Med, InteractionEntity>>() }
    val interactionsCount = medList.map { it.id }.toSet().let { patientMedIds ->
        med.interactions.count { it.interactingMedId in patientMedIds }
    }

    LaunchedEffect(med) {
        interactions.clear()
        med.interactions.forEach { inter ->
            meds.firstOrNull { it.med.id == inter.interactingMedId }?.let { add ->
                interactions.add(add.toMed(type = MedListType.PRE) to inter)
            }
        }
    }

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

                    AnimatedVisibility(visible = !isExpanded && interactionsCount > 0) {
                        Text(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .background(color = MaterialTheme.colorScheme.surfaceContainer, shape = RoundedCornerShape(32.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            text = "$interactionsCount interações",
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
                    ExamMedItemSection(
                        title = "Descrição",
                        text = med.description,
                    )

                    ExamMedItemSection(
                        title = "Indicações",
                        text = med.byDisease,
                    )

                    ExamMedItemSection(
                        title = "Interações",
                        text = "${interactions.size} possiveis interações.",
                        isExpanded = isInteractionsExpanded,
                        onIsExpandedChange = { isInteractionsExpanded = it },
                        content = {
                            interactions.sortedBy { it.second.alertLevel }.forEach {
                                val hasInMedList = medList.contains(it.first)
                                ExamMedItemContent(
                                    symbolName = it.second.alertLevel.symbolName,
                                    title = if (hasInMedList) "Em uso" else "",
                                    text = it.first.name,
                                    color = if (hasInMedList) it.second.alertLevel.color else MaterialTheme.colorScheme.onSurface,
                                )
                            }
                        },
                    )

                    ExamMedItemSection(
                        title = "Riscos",
                        text = "${med.risks.size} possiveis riscos.",
                        isExpanded = isRisksExpanded,
                        onIsExpandedChange = { isRisksExpanded = it },
                        content = {
                            med.risks.forEach {
                                ExamMedItemContent(
                                    symbolName = it.category.symbolName,
                                    title = "Risco ${it.category.description}",
                                    text = it.text,
                                )
                            }
                        },
                    )

                }
            }
        }
    }
}

@Composable
fun ExamMedItemContent(
    symbolName: String,
    title: String = "",
    text: String,
    color: Color = MaterialTheme.colorScheme.onSurface,
) {
    HorizontalDivider(color = MaterialTheme.colorScheme.surface)
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .heightIn(min = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        MaterialSymbol(
            iconName = symbolName,
            colorFilled = color,
            filled = color != MaterialTheme.colorScheme.onSurface,
        )
        Column {
            if (title.isNotEmpty()) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            Text(text = text)
        }
    }
}

@Composable
private fun ExamMedItemSection(
    title: String,
    text: String,
    isExpanded: Boolean = false,
    onIsExpandedChange: (Boolean) -> Unit = {},
    content: (@Composable ColumnScope.() -> Unit)? = null,
) {
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(durationMillis = 300)
    )

    Surface(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onIsExpandedChange(!isExpanded) },
        color = Color.Transparent,
    ) {
        Column {
            HorizontalDivider(color = MaterialTheme.colorScheme.surface)
            Row(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Text(
                        text = text,
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
                }
                if (content != null) {
                    MaterialSymbol(iconName = "arrow_drop_down", modifier = Modifier.rotate(rotation))
                }
            }
            content?.let { nonNullContent ->
                AnimatedVisibility(visible = isExpanded) {
                    Column(
                        modifier = Modifier.background(color = if (isExpanded) MaterialTheme.colorScheme.surfaceContainerHigh else Color.Transparent),
                    ) {
                        nonNullContent()
                        Spacer(Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}
