package com.firmino.geriodonto.ui.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.firmino.geriodonto.companions.MaterialSymbol

@Composable
fun ExamText(
    label: String? = null,
    symbolName: String? = null,
    text: TextFieldState,
    keyboardType: KeyboardType = KeyboardType.Text,
    suffix: String = "",
    maxLength: Int = 100,
    editable: Boolean = true,
    suggestions: List<Pair<String, String>> = listOf(),
) {
    val focusManager = LocalFocusManager.current
    var isEditing by remember { mutableStateOf(false) }
    val items = suggestions.filter { list ->
        list.first.contains(
            text.text.toString(),
            ignoreCase = true,
        ) && list.first != text.text.toString()
    }
    val isExtended = isEditing && items.isNotEmpty()

    Column(
        modifier = Modifier.background(
            color = if (isExtended) MaterialTheme.colorScheme.surfaceContainerHighest else Color.Transparent,
            shape = MaterialTheme.shapes.large,
        ),
    ) {
        AnimatedVisibility(visible = isExtended) {
            Column {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    item { Spacer(modifier = Modifier.width(6.dp)) }
                    item {
                        BadgedBox(badge = { Badge(Modifier.alpha(.8f)) { Text("${items.size}") } }) {
                            MaterialSymbol(iconName = "prompt_suggestion")
                        }
                    }
                    items(items = items) { item ->
                        SuggestionChip(
                            label = { Text(text = item.first) },
                            icon = { MaterialSymbol(iconName = item.second) },
                            onClick = {
                                text.edit { replace(0, length, item.first) }
                                focusManager.clearFocus()
                            },
                        )
                    }
                    item { Spacer(modifier = Modifier.width(6.dp)) }
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.surface)
            }
        }

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (!editable && !isEditing && focusState.isFocused) text.clearText()
                    isEditing = focusState.isFocused
                },
            state = text,
            readOnly = !editable,
            label = { label?.let { Text(label) } },
            lineLimits = TextFieldLineLimits.SingleLine,
            leadingIcon = { symbolName?.let { MaterialSymbol(symbolName, filled = isEditing) } },
            trailingIcon = {
                if (text.text.isNotEmpty() && isEditing) IconButton(onClick = { text.clearText() }) {
                    MaterialSymbol(
                        "backspace",
                    )
                }
            },
            onKeyboardAction = { focusManager.clearFocus() },
            shape = MaterialTheme.shapes.extraLarge,
            suffix = { Text(suffix) },
            inputTransformation = InputTransformation.maxLength(maxLength),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Done,
                keyboardType = keyboardType,
            ),
        )
    }
}
