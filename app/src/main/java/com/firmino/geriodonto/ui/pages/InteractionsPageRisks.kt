package com.firmino.geriodonto.ui.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.firmino.geriodonto.companions.MaterialSymbol
import com.firmino.geriodonto.data.PatientState
import com.firmino.geriodonto.data.RiskAlert
import com.firmino.geriodonto.data.RiskAlertType


@Composable
fun InteractionnsPageRisks(
    patient: PatientState,
) {
    LazyColumn(
        Modifier.padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        patient.risks.value.groupBy { it.risk.name }.values.sortedBy { it.first().risk.category }.forEach { item ->
            item {
                RisksItem(
                    riskAlert = item.first(),
                    size = item.size,
                    byItems = item.map { it.type to it.description }.distinct(),
                )
            }
        }
    }
}

@Composable
fun RisksItem(
    riskAlert: RiskAlert,
    size: Int,
    byItems: List<Pair<RiskAlertType, String>>,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        onClick = { isExpanded = !isExpanded },
        shape = MaterialTheme.shapes.extraLarge,
    ) {
        Column {
            Box(Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    MaterialSymbol(
                        iconName = riskAlert.risk.category.symbolName,
                        size = MaterialTheme.typography.displaySmall.fontSize,
                        filled = isExpanded,
                    )
                    Column {
                        Text(
                            text = riskAlert.risk.category.description,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                        )
                        Text(
                            text = riskAlert.risk.text,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(8.dp),
                ) {
                    MaterialSymbol(
                        modifier = Modifier.align(Alignment.Center),
                        iconName = "circle",
                        size = 32.sp,
                        filled = true,
                    )

                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "$size",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 1,
                    )
                }
            }

            AnimatedVisibility(isExpanded) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.surface)
                    Text(
                        text = "Fontes de risco",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    byItems.forEach {
                        Row(
                            modifier = Modifier
                                .background(color = MaterialTheme.colorScheme.surfaceContainer, shape = MaterialTheme.shapes.extraLarge)
                                .padding(horizontal = 12.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            MaterialSymbol(iconName = it.first.iconName)
                            Column {
                                Text(it.first.text, style = MaterialTheme.typography.labelSmall)
                                Text(it.second, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}
