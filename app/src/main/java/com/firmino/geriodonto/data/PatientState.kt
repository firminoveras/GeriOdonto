package com.firmino.geriodonto.data

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.firmino.geriodonto.data.database.Med

class PatientState {
    val name = TextFieldState("")
    val genre = TextFieldState("")
    val weight = SliderState(value = 30f, valueRange = 30f..200f)
    val height = SliderState(value = 0f, valueRange = 0f..2f)
    val age = SliderState(value = 40f, valueRange = 40f..120f)
    val renalFunction = SliderState(value = 0f, valueRange = 0f..3f)
    val hepaticTgo = SliderState(value = 0f, valueRange = 0f..100f)
    val hepaticTgp = SliderState(value = 0f, valueRange = 0f..100f)
    var hasFallHistory by mutableStateOf(false)
    var hasFragile by mutableStateOf(false)
    val conditionsList = mutableStateListOf<MedicalCondition>()
    val medList = mutableStateListOf<Med>()
}

@Composable
fun rememberPatientState(): PatientState {
    val state = remember { PatientState() }

    LaunchedEffect(state.hasFragile) {
        state.conditionsList.remove(Fragilidade)
        if (state.hasFragile) state.conditionsList.add(Fragilidade)
    }

    LaunchedEffect(state.hasFallHistory) {
        state.conditionsList.remove(HistoricoQuedas)
        if (state.hasFallHistory) state.conditionsList.add(HistoricoQuedas)
    }

    LaunchedEffect(state.age.value) {
        state.conditionsList.remove(IdadeAvancada)
        if (state.age.value >= 80) state.conditionsList.add(IdadeAvancada)
    }

    LaunchedEffect(state.weight.value, state.height.value) {
        state.conditionsList.removeAll { it == Sobrepeso || it == BaixoPeso }
        if (state.height.value > 0) {
            val imc = state.weight.value / (state.height.value * state.height.value)
            when {
                imc < 22.0 -> state.conditionsList.add(BaixoPeso)
                imc >= 27.0 -> state.conditionsList.add(Sobrepeso)
            }
        }
    }

    LaunchedEffect(state.renalFunction.value, state.genre.text) {
        val isFeminino = state.genre.text.toString() == "Feminino"
        val limiteBaixo = if (isFeminino) 0.6f else 0.7f
        val limiteAlto = if (isFeminino) 1.1f else 1.3f

        state.conditionsList.removeAll { it == CreatininaBaixa || it == CreatininaAlta }

        when {
            state.renalFunction.value < limiteBaixo -> state.conditionsList.add(CreatininaBaixa)
            state.renalFunction.value >= limiteAlto -> state.conditionsList.add(CreatininaAlta)
        }
    }

    LaunchedEffect(state.hepaticTgo.value) {
        state.conditionsList.removeAll { it == TGOAlta || it == TGOBaixa }
        when {
            state.hepaticTgo.value < 10 -> state.conditionsList.add(TGOBaixa)
            state.hepaticTgo.value >= 40 -> state.conditionsList.add(TGOAlta)
        }
    }

    LaunchedEffect(state.hepaticTgp.value) {
        state.conditionsList.removeAll { it == TGPAlta || it == TGPBaixa }
        when {
            state.hepaticTgp.value < 8 -> state.conditionsList.add(TGPBaixa)
            state.hepaticTgp.value >= 56 -> state.conditionsList.add(TGPAlta)
        }
    }
    return state
}

