package com.firmino.geriodonto.ui.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.firmino.geriodonto.companions.MaterialSymbol
import com.firmino.geriodonto.companions.Med
import com.firmino.geriodonto.companions.MedicalCondition
import com.firmino.geriodonto.companions.medsList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamPageMeds(
    medicalConditionList: List<MedicalCondition>,
    medList: List<Med>,
    onAdd: (Med) -> Unit,
    onRemove: (Med) -> Unit,
) {
    val searchState = rememberTextFieldState()

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {

        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                Spacer(Modifier.height(58.dp))
            }
            items(items = medList) {
                ExamMedItem(it)
            }
        }

        ExamSearchMedBar(
            textFieldState = searchState,
            completions = medsList.map { it.name to it.medClass.text },
            completionsWhenEmpty = medicalConditionList.map { it.commonMeds to it.name },
            onClickResult = { name ->
                medsList.find { it.name == name }?.let { condition ->
                    onAdd(condition)
                }
            },
        )
    }
}

@Composable
fun ExamMedItem(med: Med) {


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamSearchMedBar(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState,
    completions: List<Pair<String, String>>,
    completionsWhenEmpty: List<Pair<List<Med>, String>> = listOf(),
    onClickResult: (String) -> Unit,
    content: LazyListScope.() -> Unit = {},
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(modifier.fillMaxSize()) {
        SearchBar(
            modifier = Modifier.align(TopCenter),
            inputField = {
                SearchBarDefaults.InputField(
                    query = textFieldState.text.toString(),
                    onQueryChange = { textFieldState.edit { replace(0, length, it) } },
                    onSearch = { expanded = false },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text("Adicionar...") },
                    leadingIcon = { MaterialSymbol("search") },
                    trailingIcon = {
                        if (textFieldState.text.isNotBlank()) {
                            IconButton(
                                onClick = {
                                    textFieldState.edit { replace(0, length, "") }
                                    expanded = false
                                },
                            ) {
                                MaterialSymbol("clear")
                            }
                        }
                    },
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                Column {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        content = content,
                    )
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                }
                if (textFieldState.text.isNotBlank()) {
                    completions.filter {
                        (it.first + " " + it.second).contains(textFieldState.text.toString(), ignoreCase = true)
                    }.take(20).forEach { result ->
                        ListItem(
                            modifier = Modifier
                                .clickable {
                                    expanded = false
                                    textFieldState.clearText()
                                    onClickResult(result.first)
                                }
                                .fillMaxWidth(),
                            headlineContent = {
                                HighlightedText(result.first, textFieldState.text.toString())
                            },
                            supportingContent = {
                                if (result.second.isNotBlank()) {
                                    HighlightedText(result.second, textFieldState.text.toString())
                                }
                            },
                        )
                    }
                } else {
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = "Sugestões personalizadas",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    completionsWhenEmpty.forEach { result ->
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
                                        expanded = false
                                        onClickResult(med.name)
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
            }
        }
    }
}