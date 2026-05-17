package com.firmino.geriodonto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.firmino.geriodonto.companions.MaterialSymbol
import com.firmino.geriodonto.ui.theme.GeriOdontoTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeriOdontoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Content(innerPadding)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(padding: PaddingValues) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.padding(padding),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Novo") },
                icon = { MaterialSymbol("medication") },
                onClick = { showBottomSheet = true },
            )
        },
    ) { contentPadding ->
        Column(Modifier.padding(contentPadding)) {
            Menu()
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState,
            ) {

                Exam {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun Menu() {

}

enum class ExamPages(val text: String, val symbolName: String) {
    PAGE_PERSONAL("Pessoal", "account_box"), PAGE_DIASESES("Comorbidades", "medical_information"), PAGE_PRESCRIPTION("Prescrição", "admin_meds"),
}

@Composable
fun Exam(onDismiss: () -> Unit) {
    val pagerState = rememberPagerState { ExamPages.entries.size }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    val name = rememberTextFieldState("")
    val genre = rememberTextFieldState("")
    val weight = rememberTextFieldState("")

    Column(
        modifier = Modifier.fillMaxSize().pointerInput(Unit) { detectTapGestures { focusManager.clearFocus() } },
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "Nova prescrição", style = MaterialTheme.typography.titleLarge)
            Button(onClick = onDismiss) {
                Text("Fechar")
            }
        }
        HorizontalDivider()
        ExamMenu(
            currentPage = pagerState.currentPage,
            onChange = { page -> scope.launch { pagerState.animateScrollToPage(page) } },
        )
        HorizontalPager(state = pagerState, userScrollEnabled = false) { index ->
            when (index) {
                ExamPages.PAGE_PERSONAL.ordinal -> {
                    Column(Modifier.fillMaxWidth().padding(horizontal = 12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        ExamText(
                            label = "Nome",
                            symbolName = "person_edit",
                            text = name,
                        )

                        ExamText(
                            label = "Gênero", symbolName = "person_pin", text = genre, suggestions = listOf(
                                Pair("Masculino", "male"),
                                Pair("Feminino", "female"),
                                Pair("Transgênero", "transgender"),
                                Pair("Agênero", "agender"),
                            )
                        )

                        ExamText(
                            label = "Peso",
                            symbolName = "weight",
                            text = weight,
                            keyboardType = KeyboardType.Decimal,
                            suffix = "Kg",
                        )
                    }
                }

                ExamPages.PAGE_DIASESES.ordinal -> {
                    Text(text = "$index")
                }

                ExamPages.PAGE_PRESCRIPTION.ordinal -> {

                    Text(text = "$index")
                }
            }
        }
    }

}

@Composable
fun ExamText(
    label: String? = null,
    symbolName: String? = null,
    text: TextFieldState,
    keyboardType: KeyboardType = KeyboardType.Text,
    suffix: String = "",
    suggestions: List<Pair<String, String>> = listOf(),
) {
    val focusManager = LocalFocusManager.current
    var isEditing by remember { mutableStateOf(false) }
    val items = suggestions.filter { list -> list.first.contains(text.text.toString(), ignoreCase = true) && list.first != text.text.toString() }

    Column(
        modifier = Modifier.background(color = if (isEditing && items.isNotEmpty()) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent, shape = MaterialTheme.shapes.large)
    ) {
        AnimatedVisibility(visible = isEditing && items.isNotEmpty()) {
            Column {
                LazyRow(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
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
                            },
                        )
                    }
                    item { Spacer(modifier = Modifier.width(6.dp)) }
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.surface)
            }
        }

        TextField(
            modifier = Modifier.fillMaxWidth().onFocusChanged { focusState -> isEditing = focusState.isFocused },
            state = text,
            label = { label?.let { Text(label) } },
            lineLimits = TextFieldLineLimits.SingleLine,
            leadingIcon = { symbolName?.let { MaterialSymbol(symbolName, filled = isEditing) } },
            trailingIcon = { if (text.text.isNotEmpty()) IconButton(onClick = { text.clearText() }) { MaterialSymbol("clear") } },
            onKeyboardAction = { focusManager.clearFocus() },
            shape = MaterialTheme.shapes.extraLarge,
            suffix = { Text(suffix) },
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

@Composable
fun ExamMenu(currentPage: Int, onChange: (Int) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ExamPages.entries.forEach { page ->
            val color = if (page.ordinal == currentPage) MaterialTheme.colorScheme.primary else Color.Transparent
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                color = color,
                onClick = { onChange(page.ordinal) },
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    MaterialSymbol(
                        iconName = page.symbolName,
                        filled = page.ordinal == currentPage,
                        colorFilled = MaterialTheme.colorScheme.onPrimary,
                    )
                    AnimatedVisibility(page.ordinal == currentPage) {
                        Text(
                            text = page.text,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                }
            }
        }
    }
}
