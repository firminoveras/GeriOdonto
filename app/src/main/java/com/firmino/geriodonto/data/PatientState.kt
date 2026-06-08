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
    val height = SliderState(value = 0.6f, valueRange = 0.6f..2f)
    val age = SliderState(value = 40f, valueRange = 40f..120f)
    val renalFunction = SliderState(value = 0f, valueRange = 0f..3f)
    val hepaticTgo = SliderState(value = 0f, valueRange = 0f..100f)
    val hepaticTgp = SliderState(value = 0f, valueRange = 0f..100f)
    var hasFallHistory by mutableStateOf(false)
    var hasFragile by mutableStateOf(false)
    val conditionsList = mutableStateListOf<MedicalCondition>()
    val medList = mutableStateListOf<Med>()

    fun clear() {
        name.edit { replace(0, name.text.length, "") }
        genre.edit { replace(0, genre.text.length, "") }
        weight.value = 30f
        height.value = 0.6f
        age.value = 40f
        renalFunction.value = 0f
        hepaticTgo.value = 0f
        hepaticTgp.value = 0f
        hasFallHistory = false
        hasFragile = false
        conditionsList.clear()
        medList.clear()
    }

    fun isEmpty() = name.text.isEmpty() &&
            genre.text.isEmpty() &&
            weight.value == weight.valueRange.start &&
            height.value == height.valueRange.start &&
            age.value == age.valueRange.start &&
            renalFunction.value == renalFunction.valueRange.start &&
            hepaticTgo.value == hepaticTgo.valueRange.start &&
            hepaticTgp.value == hepaticTgp.valueRange.start &&
            !hasFallHistory &&
            !hasFragile &&
            conditionsList.isEmpty() &&
            medList.isEmpty()

    fun isNotEmpty() = !isEmpty()

    fun add(med: Med) = this.medList.add(med)

    fun remove(med: Med) = this.medList.remove(med)

    fun contains(med: Med) = this.medList.contains(med)

    fun add(condition: MedicalCondition) = this.conditionsList.add(condition)

    fun remove(condition: MedicalCondition) = this.conditionsList.remove(condition)

    fun contains(condition: MedicalCondition) = this.conditionsList.contains(condition)

}

@Composable
fun rememberPatientState(): PatientState {
    val state = remember { PatientState() }

    LaunchedEffect(state.hasFragile) {
        state.remove(Fragilidade)
        if (state.hasFragile) state.add(Fragilidade)
    }

    LaunchedEffect(state.hasFallHistory) {
        state.remove(HistoricoQuedas)
        if (state.hasFallHistory) state.add(HistoricoQuedas)
    }
    LaunchedEffect(state.age.value) {
        state.remove(IdadeAvancada)
        if (state.age.value >= 80) state.add(IdadeAvancada)
    }
    LaunchedEffect(state.weight.value, state.height.value) {
        state.remove(Sobrepeso)
        state.remove(BaixoPeso)
        if (state.height.value > state.height.valueRange.start && state.weight.value > state.weight.valueRange.start) {
            val imc = state.weight.value / (state.height.value * state.height.value)
            when {
                imc < 22.0 -> state.conditionsList.add(BaixoPeso)
                imc >= 27.0 -> state.conditionsList.add(Sobrepeso)
            }
        }
    }

    LaunchedEffect(state.renalFunction.value, state.genre.text) {
        state.remove(CreatininaAlta)
        state.remove(CreatininaBaixa)
        if (state.renalFunction.value > 0) {
            val isFeminino = state.genre.text.toString() == "Feminino"
            val limiteBaixo = if (isFeminino) 0.6f else 0.7f
            val limiteAlto = if (isFeminino) 1.1f else 1.3f
            when {
                state.renalFunction.value < limiteBaixo -> state.conditionsList.add(CreatininaBaixa)
                state.renalFunction.value >= limiteAlto -> state.conditionsList.add(CreatininaAlta)
            }
        }
    }

    LaunchedEffect(state.hepaticTgo.value) {
        state.remove(TGOAlta)
        state.remove(TGOBaixa)
        if (state.hepaticTgo.value > 0) {
            when {
                state.hepaticTgo.value < 10 -> state.conditionsList.add(TGOBaixa)
                state.hepaticTgo.value >= 40 -> state.conditionsList.add(TGOAlta)
            }
        }
    }

    LaunchedEffect(state.hepaticTgp.value) {
        state.remove(TGPAlta)
        state.remove(TGPBaixa)
        if (state.hepaticTgp.value > 0) {
            when {
                state.hepaticTgp.value < 8 -> state.conditionsList.add(TGPBaixa)
                state.hepaticTgp.value >= 56 -> state.conditionsList.add(TGPAlta)
            }
        }
    }
    return state
}