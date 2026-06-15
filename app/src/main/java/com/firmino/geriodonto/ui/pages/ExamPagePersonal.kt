package com.firmino.geriodonto.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.firmino.geriodonto.data.PatientState
import com.firmino.geriodonto.ui.widgets.ExamSlider
import com.firmino.geriodonto.ui.widgets.ExamText

@Composable
fun ExamPagePersonal(
    patient: PatientState
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
                text = patient.name,
            )
        }

        item {
            ExamText(
                label = "Gênero",
                symbolName = "person_pin",
                text = patient.genre,
                editable = false,
                suggestions = listOf(
                    Pair("Masculino", "male"),
                    Pair("Feminino", "female"),
                ),
            )
        }

        item {
            ExamSlider(
                label = "Idade",
                symbolName = "elderly",
                suffix = "anos",
                value = patient.age,
                plusIndicator = true,
                description = {
                    when {
                        it >= 80 -> "Idade Avançada"
                        else -> "Idade Regular"
                    }
                },
            )
        }

        item {
            ExamSlider(
                label = "Altura",
                symbolName = "height",
                suffix = "m",
                value = patient.height,
                decimals = 2,
                plusIndicator = true,
                description = {
                    if (patient.height.value > 0) {
                        val imc = patient.weight.value / (patient.height.value * patient.height.value)
                        when {
                            imc < 22.0 -> "Abaixo do peso"
                            imc >= 27.0 -> "Acima do peso"
                            else -> "Peso ideal"
                        }
                    } else ""
                },
            )
        }

        item {
            ExamSlider(
                label = "Peso",
                symbolName = "weight",
                suffix = "kg",
                value = patient.weight,
                plusIndicator = true,
                description = {
                    if (patient.height.value > 0) {
                        val imc = patient.weight.value / (patient.height.value * patient.height.value)
                        when {
                            imc < 22.0 -> "Abaixo do peso"
                            imc >= 27.0 -> "Acima do peso"
                            else -> "Peso ideal"
                        }
                    } else ""
                },
            )
        }
    }
}
