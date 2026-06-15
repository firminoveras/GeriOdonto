package com.firmino.geriodonto.data

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.firmino.geriodonto.data.database.Med

enum class PatientStateChangeType {
    ADD,
    REMOVE
}

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
    val conditionsList = mutableStateSetOf<MedicalCondition>()
    val medList = mutableStateSetOf<Med>()

    var onConditionChanged: (MedicalCondition, PatientStateChangeType) -> Unit = { _, _ -> }
    var onMedChanged: (Med, PatientStateChangeType) -> Unit = { _, _ -> }

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

    fun add(med: Med) {
        if (this.medList.add(med))
            onMedChanged(med, PatientStateChangeType.ADD)
    }

    fun remove(med: Med) {
        if (this.medList.remove(med))
            onMedChanged(med, PatientStateChangeType.REMOVE)
    }

    fun contains(med: Med) = this.medList.contains(med)

    fun add(condition: MedicalCondition) {
        if (this.conditionsList.add(condition))
            onConditionChanged(condition, PatientStateChangeType.ADD)
    }

    fun remove(condition: MedicalCondition) {
        if (this.conditionsList.remove(condition))
            onConditionChanged(condition, PatientStateChangeType.REMOVE)
    }

    fun contains(condition: MedicalCondition) = this.conditionsList.contains(condition)
}

@Composable
fun rememberPatientState(): PatientState {
    val state = remember { PatientState() }

    LaunchedEffect(state.hasFragile) {
        if (state.hasFragile) state.add(Fragilidade) else state.remove(Fragilidade)
    }

    LaunchedEffect(state.hasFallHistory) {
        if (state.hasFallHistory) state.add(HistoricoQuedas) else state.remove(HistoricoQuedas)
    }

    LaunchedEffect(state.age.value) {
        if (state.age.value >= 80) state.add(IdadeAvancada) else state.remove(IdadeAvancada)
    }

    LaunchedEffect(state.weight.value, state.height.value) {
        if (state.height.value > state.height.valueRange.start && state.weight.value > state.weight.valueRange.start) {
            val imc = state.weight.value / (state.height.value * state.height.value)
            when {
                imc < 22.0 -> {
                    state.remove(Sobrepeso)
                    state.add(BaixoPeso)
                }

                imc >= 27.0 -> {
                    state.remove(BaixoPeso)
                    state.add(Sobrepeso)
                }

                else -> {
                    state.remove(Sobrepeso)
                    state.remove(BaixoPeso)
                }
            }
        }
    }

    LaunchedEffect(state.renalFunction.value, state.genre.text) {
        if (state.renalFunction.value > 0) {
            val isFeminino = state.genre.text.toString() == "Feminino"
            val limiteBaixo = if (isFeminino) 0.6f else 0.7f
            val limiteAlto = if (isFeminino) 1.1f else 1.3f
            when {
                state.renalFunction.value < limiteBaixo -> {
                    state.remove(CreatininaAlta)
                    state.add(CreatininaBaixa)
                }

                state.renalFunction.value >= limiteAlto -> {
                    state.remove(CreatininaBaixa)
                    state.add(CreatininaAlta)
                }

                else -> {
                    state.remove(CreatininaAlta)
                    state.remove(CreatininaBaixa)
                }
            }
        }
    }

    LaunchedEffect(state.hepaticTgo.value) {
        if (state.hepaticTgo.value > 0) {
            when {
                state.hepaticTgo.value < 10 -> {
                    state.remove(TGOAlta)
                    state.add(TGOBaixa)
                }

                state.hepaticTgo.value >= 40 -> {
                    state.remove(TGOBaixa)
                    state.add(TGOAlta)
                }

                else -> {
                    state.remove(TGOAlta)
                    state.remove(TGOBaixa)
                }
            }
        }
    }

    LaunchedEffect(state.hepaticTgp.value) {
        if (state.hepaticTgp.value > 0) {
            when {
                state.hepaticTgp.value < 8 -> {
                    state.remove(TGPAlta)
                    state.add(TGPBaixa)
                }

                state.hepaticTgp.value >= 56 -> {
                    state.remove(TGPBaixa)
                    state.add(TGPAlta)
                }

                else -> {
                    state.remove(TGPAlta)
                    state.remove(TGPBaixa)
                }
            }
        }
    }

    LaunchedEffect(state.medList.size) {
        if (state.medList.size >= 5) state.add(Polifarmacia) else state.remove(Polifarmacia)
    }

    return state
}