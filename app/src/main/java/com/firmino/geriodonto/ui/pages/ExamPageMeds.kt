package com.firmino.geriodonto.ui.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.firmino.geriodonto.companions.MaterialSymbol
import com.firmino.geriodonto.companions.Med
import com.firmino.geriodonto.companions.MedicalCondition
import com.firmino.geriodonto.companions.medsList
import com.firmino.geriodonto.ui.widgets.ExamSearchBar
import com.firmino.geriodonto.ui.widgets.HighlightedText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamPageMeds(
    medicalConditionList: List<MedicalCondition>,
    medList: List<Med>,
    onSearchStateChange: (Boolean) -> Unit,
    onAdd: (Med) -> Unit,
    onRemove: (Med) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item { Spacer(Modifier.height(58.dp)) }
            items(items = medList) { ExamMedItem(it) }
        }

        ExamSearchBar(
            onSearchStateChange = onSearchStateChange,
            content = { query, onDone ->
                if (query.isNotBlank()) {
                    medsList.map { it.name to it.medClass.text }.filter {
                        (it.first + " " + it.second).contains(query, ignoreCase = true)
                    }.take(20).forEach { result ->
                        ListItem(
                            modifier = Modifier
                                .clickable {
                                    onDone()
                                    medsList.find { it.name == result.first }?.let { condition ->
                                        onAdd(condition)
                                    }
                                }
                                .fillMaxWidth(),
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
                } else if (medicalConditionList.map { it.commonMeds to it.name }.isNotEmpty()) {
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = "Sugestões personalizadas",
                        style = MaterialTheme.typography.titleMedium,
                    )
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
                            items(result.first) { med ->
                                SuggestionChip(
                                    onClick = {
                                        onDone()
                                        medsList.find { it.name == med.name }?.let { condition ->
                                            onAdd(condition)
                                        }
                                    },
                                    icon = { MaterialSymbol(iconName = "medication") },
                                    label = { Text(med.name) },
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
fun ExamMedItem(med: Med) {


}