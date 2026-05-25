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
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.firmino.geriodonto.companions.MaterialSymbol
import com.firmino.geriodonto.companions.MedicalCondition
import com.firmino.geriodonto.companions.medicalConditionsList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamPageDiaseses(
    medicalConditionList: List<MedicalCondition>,
    onAdd: (MedicalCondition) -> Unit,
    onRemove: (MedicalCondition) -> Unit,
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
            items(items = medicalConditionList) {
                ExamDiaseseItem(
                    modifier = Modifier.fillMaxWidth(),
                    medicalCondition = it,
                    onRemove = { onRemove(it) },
                )
            }
        }

        ExamSearchBar(
            textFieldState = searchState,
            completions = medicalConditionsList.map { it.name to it.description },
            onClickResult = { name ->
                medicalConditionsList.find { it.name == name }?.let { condition ->
                    onAdd(condition)
                }
            },
        )
    }
}

@Composable
fun ExamDiaseseItem(modifier: Modifier = Modifier, medicalCondition: MedicalCondition, onRemove: (MedicalCondition) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(onClick = { isExpanded = !isExpanded }) {
        Column(Modifier.fillMaxWidth()) {
            Box (
                Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
            ){
                Column(Modifier.padding(end = 32.dp)) {
                    Text(medicalCondition.name, style = MaterialTheme.typography.titleLarge)
                    Text(medicalCondition.description, style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic))
                }

                IconButton(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = { onRemove(medicalCondition) },
                    content = { MaterialSymbol("delete") },
                )
            }
            AnimatedVisibility(isExpanded) {
                Column() {
                    medicalCondition.commonRisks.forEach {
                        HorizontalDivider()
                        Row(
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 12.dp)
                                .heightIn(min = 32.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            MaterialSymbol("warning")
                            Text(text = it.text, style = MaterialTheme.typography.labelMedium)
                        }

                    }
                }
            }

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamSearchBar(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState,
    completions: List<Pair<String, String>>,
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
                    placeholder = { Text("Procurar") },
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
                }
            }
        }
    }
}

@Composable
fun HighlightedText(
    fullText: String,
    target: String,
) {
    val annotatedString = buildAnnotatedString {
        var startIndex = 0
        while (startIndex < fullText.length) {
            val index = fullText.indexOf(target, startIndex, ignoreCase = true)
            if (index == -1) {
                append(fullText.substring(startIndex))
                break
            }
            append(fullText.substring(startIndex, index))
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)) {
                append(fullText.substring(index, index + target.length))
            }
            startIndex = index + target.length
        }
    }
    Text(text = annotatedString)
}