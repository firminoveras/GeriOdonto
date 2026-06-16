package com.firmino.geriodonto.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import com.firmino.geriodonto.companions.MaterialSymbol

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamSearchBar(
    modifier: Modifier = Modifier,
    placeholderText: String = "Adicionar...",
    onSearchStateChange: (Boolean) -> Unit,
    content: @Composable (ColumnScope.(query: String, onDone: () -> Unit) -> Unit),
) {
    val textFieldState = rememberTextFieldState()
    var expanded by rememberSaveable { mutableStateOf(false) }
    val updateExpansion = { isExpanded: Boolean ->
        expanded = isExpanded
        onSearchStateChange(isExpanded)
    }

    fun done() {
        textFieldState.clearText()
        updateExpansion(false)
    }

    Box(modifier.fillMaxSize()) {
        SearchBar(
            modifier = Modifier.align(TopCenter),
            expanded = expanded,
            onExpandedChange = updateExpansion,
            inputField = {
                SearchBarDefaults.InputField(
                    query = textFieldState.text.toString(),
                    onQueryChange = { textFieldState.edit { replace(0, length, it) } },
                    onSearch = { updateExpansion(false) },
                    expanded = expanded,
                    onExpandedChange = updateExpansion,
                    placeholder = { Text(placeholderText) },
                    leadingIcon = {
                        if (expanded) {
                            IconButton(onClick = ::done) { MaterialSymbol("arrow_back") }
                        } else {
                            MaterialSymbol("search")
                        }
                    },
                    trailingIcon = {
                        if (expanded && textFieldState.text.isNotEmpty())
                            IconButton(
                                onClick = { textFieldState.clearText() },
                                content = { MaterialSymbol("clear") },
                            )
                    },
                )
            },
            content = {
                content(textFieldState.text.toString(), ::done)
            },
        )
    }
}