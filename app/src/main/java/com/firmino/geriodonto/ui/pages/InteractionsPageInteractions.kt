package com.firmino.geriodonto.ui.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import com.firmino.geriodonto.data.InteractionAlert
import com.firmino.geriodonto.data.PatientState

@Composable
fun InteractionnsPageInteractions(
    patient: PatientState,
) {
    LazyColumn(
        Modifier.padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(items = patient.interactions.value) {
            InteractionItem(interaction = it)
        }
    }
}

@Composable
fun InteractionItem(
    interaction: InteractionAlert,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        onClick = { isExpanded = !isExpanded },
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = interaction.alertLevel.color.copy(alpha = .2f),
        ),
    ) {
        Column(Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                MaterialSymbol(
                    iconName = interaction.alertLevel.symbolName,
                    size = MaterialTheme.typography.displaySmall.fontSize,
                    color = interaction.alertLevel.color,
                    colorFilled = interaction.alertLevel.color,
                    filled = isExpanded,
                )
                Column {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = interaction.medBase.name,
                            style = MaterialTheme.typography.titleSmall,
                            maxLines = 1,
                        )
                        MaterialSymbol(
                            iconName = "arrow_range",
                            size = MaterialTheme.typography.titleSmall.fontSize,
                        )
                        Text(
                            text = interaction.medInteracted.name,
                            style = MaterialTheme.typography.titleSmall,
                            maxLines = 1,
                        )
                    }
                    Text(
                        text = interaction.alertLevel.text,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }

            AnimatedVisibility(isExpanded) {
                Column {
                    HorizontalDivider(color = MaterialTheme.colorScheme.surface)
                    Text(
                        modifier = Modifier.padding(start = 12.dp, top = 8.dp),
                        text = "Descrição da interação",
                        style = MaterialTheme.typography.labelSmall,
                        color = interaction.alertLevel.color,
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 12.dp, end = 12.dp, bottom = 8.dp)
                            .fillMaxWidth(1f),
                        text = interaction.description,
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
                    Row(
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 12.dp)
                            .heightIn(min = 32.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        MaterialSymbol(interaction.risk.category.symbolName)
                        Column {
                            Text(
                                text = "Risco ${interaction.risk.category.description}",
                                style = MaterialTheme.typography.labelSmall,
                                color = interaction.alertLevel.color,
                            )
                            Text(text = interaction.risk.text, style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }
            }
        }
    }

}