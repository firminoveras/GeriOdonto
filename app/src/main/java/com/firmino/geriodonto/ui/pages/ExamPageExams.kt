package com.firmino.geriodonto.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.firmino.geriodonto.ui.widgets.ExamChecker
import com.firmino.geriodonto.ui.widgets.ExamSlider

@Composable
fun ExamPageExams(
    genre: TextFieldState,
    renalFunction: SliderState,
    hepaticTgo: SliderState,
    hepaticTgp: SliderState,
    hasFallHistory: Boolean,
    hasFragile: Boolean,
    onHasFallHistoryChange: (Boolean) -> Unit,
    onHasFragileChange: (Boolean) -> Unit,
) {
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {

        item {
            ExamSlider(
                label = "Função renal: Creatinina Sérica",
                symbolName = "nephrology",
                suffix = "mg/dL",
                value = renalFunction,
                decimals = 2,
                plusIndicator = true,
                description = {
                    val isFeminino = genre.text.toString() == "Feminino"
                    val limiteBaixo = if (isFeminino) 0.6f else 0.7f
                    val limiteAlto = if (isFeminino) 1.1f else 1.3f
                    when {
                        it < limiteBaixo -> "Abaixo"
                        it >= limiteAlto -> "Acima"
                        else -> "Normal"
                    }
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
                description = {
                    when {
                        it < 10 -> "Abaixo"
                        it in 10.0..<40.0 -> "Normal"
                        else -> "Acima"
                    }
                },
            )
        }

        item {
            ExamSlider(
                label = "Função hepática: TGP (ALT)",
                symbolName = "water_drop",
                suffix = "U/L",
                value = hepaticTgp,
                description = {
                    when {
                        it < 7 -> "Abaixo"
                        it in 7f..<56f -> "Normal"
                        else -> "Acima"
                    }
                },
                plusIndicator = true,
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
    }
}
