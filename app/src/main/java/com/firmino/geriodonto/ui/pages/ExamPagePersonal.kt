package com.firmino.geriodonto.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.firmino.geriodonto.companions.MaterialSymbol
import com.firmino.geriodonto.ui.widgets.ExamChecker
import com.firmino.geriodonto.ui.widgets.ExamSlider
import com.firmino.geriodonto.ui.widgets.ExamText

@Composable
fun ExamPagePersonal(
    name: TextFieldState,
    genre: TextFieldState,
    age: SliderState,
    weight: SliderState,
    renalFunction: SliderState,
    hepaticTgo: SliderState,
    hepaticTgp: SliderState,
    hasFallHistory: Boolean,
    hasFragile: Boolean,
    onHasFallHistoryChange: (Boolean) -> Unit,
    onHasFragileChange: (Boolean) -> Unit,
    onNext: () -> Unit,
) {
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            ExamText(
                label = "Nome",
                symbolName = "person_edit",
                text = name,
            )
        }

        item {
            ExamText(
                label = "Gênero",
                symbolName = "person_pin",
                text = genre,
                suggestions = listOf(
                    Pair("Masculino", "male"),
                    Pair("Feminino", "female"),
                    Pair("Transgênero", "transgender"),
                    Pair("Agênero", "agender"),
                ),
            )
        }

        item {
            ExamSlider(
                label = "Idade",
                symbolName = "elderly",
                suffix = "anos",
                value = age,
                min = 40f,
                max = 120f,
                plusIndicator = true,
            )
        }

        item {
            ExamSlider(
                label = "Peso",
                symbolName = "weight",
                suffix = "kg",
                value = weight,
                min = 30f,
                max = 200f,
                plusIndicator = true,
            )
        }

        item {
            ExamSlider(
                label = "Função renal: Creatinina Sérica",
                symbolName = "nephrology",
                suffix = "mg/dL",
                value = renalFunction,
                max = 3f,
                decimals = 2,
                plusIndicator = true,
                scale = when (genre.text.toString()) {
                    "Feminino" -> listOf(
                        Pair(3f, "Acima"),
                        Pair(1.1f, "Normal"),
                        Pair(0.6f, "Abaixo"),
                    )

                    else -> listOf(
                        Pair(3f, "Acima"),
                        Pair(1.3f, "Normal"),
                        Pair(0.7f, "Abaixo"),
                    )
                },
            )
        }

        item {
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
        }

        item {
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
        }

        item {
            ExamChecker(
                label = "Histórico de quedas",
                symbolName = "falling",
                value = hasFallHistory,
                onValueChange = { onHasFallHistoryChange(it) },
            )
        }

        item {
            ExamChecker(
                label = "Fragilidade",
                symbolName = "sick",
                value = hasFragile,
                onValueChange = { onHasFragileChange(it) },
            )
        }

        item {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onNext,
                content = {
                    Text("Próximo")
                    MaterialSymbol(
                        iconName = "arrow_right_alt",
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                },
            )
        }

    }
}
