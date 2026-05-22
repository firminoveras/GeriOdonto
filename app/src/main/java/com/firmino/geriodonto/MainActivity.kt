package com.firmino.geriodonto

import android.R
import android.graphics.Paint
import android.os.Bundle
import android.text.Layout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.maxLength
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
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Exam(onDismiss: () -> Unit) {
    val pagerState = rememberPagerState { ExamPages.entries.size }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    val name = rememberTextFieldState("")
    val genre = rememberTextFieldState("")
    val weight = rememberSliderState(value = 0f)
    val age = rememberSliderState(value = 0f)
    val renalFunction = rememberSliderState(value = 0f)
    val hepaticTgo = rememberSliderState(value = 0f)
    val hepaticTgp = rememberSliderState(value = 0f)

    var hasFallHistory by remember { mutableStateOf(false) }
    var hasFragile by remember { mutableStateOf(false) }

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

                        ExamSlider(
                            label = "Idade",
                            symbolName = "elderly",
                            suffix = "anos",
                            value = age,
                            min = 40f,
                            max = 120f,
                            plusIndicator = true,
                        )

                        ExamSlider(
                            label = "Peso",
                            symbolName = "weight",
                            suffix = "kg",
                            value = weight,
                            min = 30f,
                            max = 200f,
                            plusIndicator = true,
                        )

                        ExamSlider(
                            label = "Função renal: Creatinina Sérica",
                            symbolName = "nephrology",
                            suffix = "mg/dL",
                            value = renalFunction,
                            max = 3f,
                            decimals = 2,
                            plusIndicator = true,
                            scale = when (genre.text.toString()) {
                                "Feminino" -> listOf(Pair(3f, "Acima"), Pair(1.1f, "Normal"), Pair(0.6f, "Abaixo"))
                                else -> listOf(Pair(3f, "Acima"), Pair(1.3f, "Normal"), Pair(0.7f, "Abaixo"))
                            },
                        )

                        ExamSlider(
                            label = "Função hepática: TGO (AST)",
                            symbolName = "water_do",
                            suffix = "U/L",
                            value = hepaticTgo,
                            plusIndicator = true,
                            scale = listOf(
                                Pair(100f, "Acima"),
                                Pair(40f, "Normal"),
                                Pair(10f, "Abaixo"),
                            ),
                        )

                        ExamSlider(
                            label = "Função hepática: TGP (ALT)",
                            symbolName = "water_drop",
                            suffix = "U/L",
                            value = hepaticTgp,
                            plusIndicator = true,
                            scale = listOf(
                                Pair(100f, "Acima"),
                                Pair(56f, "Normal"),
                                Pair(7f, "Abaixo"),
                            ),
                        )

                        ExamChecker(
                            label = "Histórico de quedas",
                            symbolName = "falling",
                            value = hasFallHistory,
                            onValueChange = { hasFallHistory = it },
                        )

                        ExamChecker(
                            label = "Fragilidade",
                            symbolName = "sick",
                            value = hasFragile,
                            onValueChange = { hasFragile = it },
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
fun ExamChecker(
    label: String,
    symbolName: String,
    labelTrue: String = "Sim",
    labelFalse: String = "Não",
    value: Boolean,
    onValueChange: (Boolean) -> Unit,
) {
    Box(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.surfaceVariant, shape = MaterialTheme.shapes.extraLarge)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            MaterialSymbol(iconName = symbolName)
            Column {
                Text(text = label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(text = if (value) labelTrue else labelFalse)
            }
        }
        Switch(
            modifier = Modifier.align(Alignment.CenterEnd).padding(horizontal = 12.dp),
            checked = value,
            onCheckedChange = onValueChange,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamSlider(
    label: String,
    symbolName: String,
    suffix: String,
    value: SliderState,
    scale: List<Pair<Float, String>> = listOf(),
    min: Float = 0f,
    max: Float = 100f,
    decimals: Int = 0,
    plusIndicator: Boolean = false,
) {
    var isEditing by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("") }
    val progress = (min + (max - min) * value.value)
    scale.forEach {
        if (progress <= it.first) {
            description = it.second
        }
    }

    val formatedValue = "%.${decimals}f${if (plusIndicator && progress == max) "+" else ""}".format(progress)

    Column(modifier = Modifier.background(color = if (isEditing) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent, shape = MaterialTheme.shapes.extraLarge)) {
        Box {
            TextField(
                modifier = Modifier.fillMaxWidth().alpha(if (isEditing) 0f else 1f),
                value = if (progress != min) "$formatedValue $suffix" else "",
                onValueChange = {},
                readOnly = true,
                label = { Text(label) },
                leadingIcon = { MaterialSymbol(symbolName, filled = isEditing) },
                shape = MaterialTheme.shapes.extraLarge,
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                ),
            )
            Slider(
                modifier = Modifier.align(Alignment.Center).padding(horizontal = 24.dp).alpha(if (isEditing) 1f else 0f).pointerInput(Unit) {
                    awaitEachGesture {
                        awaitFirstDown(pass = PointerEventPass.Initial)
                        isEditing = true
                        do {
                            val event = awaitPointerEvent(pass = PointerEventPass.Initial)
                            val anyChangesDown = event.changes.any { it.pressed }
                        } while (anyChangesDown)
                        isEditing = false
                    }
                },
                state = value,
                colors = SliderDefaults.colors(inactiveTrackColor = MaterialTheme.colorScheme.primary),
            )
        }
        AnimatedVisibility(visible = isEditing) {
            HorizontalDivider(color = MaterialTheme.colorScheme.surface)
            Row(Modifier.padding(12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    MaterialSymbol(symbolName)
                    if (scale.isEmpty()) {
                        Text(text = label, style = MaterialTheme.typography.bodyLarge)
                    } else {
                        Column {
                            Text(text = label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(text = description, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = formatedValue, style = MaterialTheme.typography.headlineSmall)
                    Text(text = suffix, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
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
    maxLength: Int = 256,
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
            trailingIcon = { if (text.text.isNotEmpty() && isEditing) IconButton(onClick = { text.clearText() }) { MaterialSymbol("backspace") } },
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
