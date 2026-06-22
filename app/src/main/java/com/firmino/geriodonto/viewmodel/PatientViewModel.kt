package com.firmino.geriodonto.viewmodel

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.SliderState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firmino.geriodonto.data.BaixoPeso
import com.firmino.geriodonto.data.CreatininaAlta
import com.firmino.geriodonto.data.CreatininaBaixa
import com.firmino.geriodonto.data.Fragilidade
import com.firmino.geriodonto.data.HistoricoQuedas
import com.firmino.geriodonto.data.IdadeAvancada
import com.firmino.geriodonto.data.InteractionAlertLevel
import com.firmino.geriodonto.data.MedicalCondition
import com.firmino.geriodonto.data.Polifarmacia
import com.firmino.geriodonto.data.Risk
import com.firmino.geriodonto.data.Sobrepeso
import com.firmino.geriodonto.data.TGOAlta
import com.firmino.geriodonto.data.TGOBaixa
import com.firmino.geriodonto.data.TGPAlta
import com.firmino.geriodonto.data.TGPBaixa
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

enum class PatientStateChangeType {
    ADD,
    REMOVE
}

@HiltViewModel
class PatientViewModel @Inject constructor() : ViewModel() {
    private val name = TextFieldState("")
    private val genre = TextFieldState("")
    private val weight = SliderState(value = 30f, valueRange = 30f..200f)
    private val height = SliderState(value = 0.6f, valueRange = 0.6f..2f)
    private val age = SliderState(value = 40f, valueRange = 40f..120f)
    private val renalFunction = SliderState(value = 0f, valueRange = 0f..3f)
    private val hepaticTgo = SliderState(value = 0f, valueRange = 0f..100f)
    private val hepaticTgp = SliderState(value = 0f, valueRange = 0f..100f)
    private var hasFallHistory by mutableStateOf(false)
    private var hasFragile by mutableStateOf(false)
    private val conditionsList = mutableStateSetOf<MedicalCondition>()
    private val medList = mutableStateSetOf<Med>()
    private val interactions: State<List<InteractionAlert>> = derivedStateOf { verifyInteractions(medList) }
    private val risks: State<List<RiskAlert>> = derivedStateOf { verifyRisks(medList, conditionsList, interactions) }

    var onConditionChanged: (MedicalCondition, PatientStateChangeType) -> Unit = { _, _ -> }
    var onMedChanged: (Med, PatientStateChangeType) -> Unit = { _, _ -> }

    init {
        viewModelScope.launch {
            snapshotFlow {
                listOf(
                    hasFragile,
                    hasFallHistory,
                    age.value,
                    weight.value,
                    height.value,
                    renalFunction.value,
                    hepaticTgo.value,
                    hepaticTgp.value,
                    genre.text.toString(),
                    medList.size
                )
            }.collectLatest {
                processDynamicConditions()
            }
        }
    }

    private fun processDynamicConditions() {
        if (hasFragile) add(Fragilidade) else remove(Fragilidade)
        if (hasFallHistory) add(HistoricoQuedas) else remove(HistoricoQuedas)

        if (age.value >= 80) add(IdadeAvancada) else remove(IdadeAvancada)

        if (height.value > height.valueRange.start && weight.value > weight.valueRange.start) {
            val imc = weight.value / (height.value * height.value)
            when {
                imc < 22.0 -> { remove(Sobrepeso); add(BaixoPeso) }
                imc >= 27.0 -> { remove(BaixoPeso); add(Sobrepeso) }
                else -> { remove(Sobrepeso); remove(BaixoPeso) }
            }
        }

        if (renalFunction.value > 0) {
            val isFeminino = genre.text.toString() == "Feminino"
            val limiteBaixo = if (isFeminino) 0.6f else 0.7f
            val limiteAlto = if (isFeminino) 1.1f else 1.3f
            when {
                renalFunction.value < limiteBaixo -> { remove(CreatininaAlta); add(CreatininaBaixa) }
                renalFunction.value >= limiteAlto -> { remove(CreatininaBaixa); add(CreatininaAlta) }
                else -> { remove(CreatininaAlta); remove(CreatininaBaixa) }
            }
        }

        if (hepaticTgo.value > 0) {
            when {
                hepaticTgo.value < 10 -> { remove(TGOAlta); add(TGOBaixa) }
                hepaticTgo.value >= 40 -> { remove(TGOBaixa); add(TGOAlta) }
                else -> { remove(TGOAlta); remove(TGOBaixa) }
            }
        }

        if (hepaticTgp.value > 0) {
            when {
                hepaticTgp.value < 8 -> { remove(TGPAlta); add(TGPBaixa) }
                hepaticTgp.value >= 56 -> { remove(TGPBaixa); add(TGPAlta) }
                else -> { remove(TGPAlta); remove(TGPBaixa) }
            }
        }

        if (medList.size >= 5) add(Polifarmacia) else remove(Polifarmacia)
    }

    val uiState: State<PatientUiState> = derivedStateOf {
        PatientUiState(
            name = name,
            genre = genre,
            weight = weight,
            height = height,
            age = age,
            renalFunction = renalFunction,
            hepaticTgo = hepaticTgo,
            hepaticTgp = hepaticTgp,
            hasFallHistory = hasFallHistory,
            hasFragile = hasFragile,
            conditionsList = conditionsList.toSet(),
            medList = medList.toSet(),
            interactions = interactions.value,
            risks = risks.value,
            isEmpty = isEmpty(),
        )
    }

    fun onEvent(event: PatientEvent) {
        when (event) {
            is PatientEvent.UpdateFallHistory -> hasFallHistory = event.value
            is PatientEvent.UpdateFragile -> hasFragile = event.value
            is PatientEvent.AddMed -> add(event.med)
            is PatientEvent.RemoveMed -> remove(event.med)
            is PatientEvent.AddCondition -> add(event.condition)
            is PatientEvent.RemoveCondition -> remove(event.condition)
            is PatientEvent.ClearAll -> clear()
        }
    }

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

    fun add(med: Med) {
        if (!medList.map { it.id }.contains(med.id) && this.medList.add(med))
            onMedChanged(med, PatientStateChangeType.ADD)
    }

    fun remove(med: Med) {
        if (this.medList.remove(med))
            onMedChanged(med, PatientStateChangeType.REMOVE)
    }

    fun add(condition: MedicalCondition) {
        if (!conditionsList.map { it.name }.contains(condition.name) && this.conditionsList.add(condition))
            onConditionChanged(condition, PatientStateChangeType.ADD)
    }

    fun remove(condition: MedicalCondition) {
        if (this.conditionsList.remove(condition))
            onConditionChanged(condition, PatientStateChangeType.REMOVE)
    }
}

data class PatientUiState(
    val name: TextFieldState,
    val genre: TextFieldState,
    val weight: SliderState,
    val height: SliderState,
    val age: SliderState,
    val renalFunction: SliderState,
    val hepaticTgo: SliderState,
    val hepaticTgp: SliderState,
    val hasFallHistory: Boolean,
    val hasFragile: Boolean,
    val conditionsList: Set<MedicalCondition>,
    val medList: Set<Med>,
    val interactions: List<InteractionAlert>,
    val risks: List<RiskAlert>,
    val isEmpty: Boolean,
)

sealed interface PatientEvent {
    data class UpdateFallHistory(val value: Boolean) : PatientEvent
    data class UpdateFragile(val value: Boolean) : PatientEvent
    data class AddMed(val med: Med) : PatientEvent
    data class RemoveMed(val med: Med) : PatientEvent
    data class AddCondition(val condition: MedicalCondition) : PatientEvent
    data class RemoveCondition(val condition: MedicalCondition) : PatientEvent
    data object ClearAll : PatientEvent
}

data class InteractionAlert(
    val medBase: Med,
    val medInteracted: Med,
    val risk: Risk,
    val description: String,
    val alertLevel: InteractionAlertLevel,
)

fun verifyInteractions(meds: Set<Med>): List<InteractionAlert> {
    val alertas = mutableListOf<InteractionAlert>()
    val paresVerificados = mutableSetOf<String>()

    for (med in meds) {
        for (interacao in med.interactions) {
            val medInteragente = meds.find { it.id == interacao.interactingMedId }

            if (medInteragente != null) {
                val id1 = if (med.id < medInteragente.id) med.id else medInteragente.id
                val id2 = if (med.id < medInteragente.id) medInteragente.id else med.id
                val chaveUnica = "${id1}_${id2}"

                if (!paresVerificados.contains(chaveUnica)) {
                    paresVerificados.add(chaveUnica)
                    alertas.add(
                        InteractionAlert(
                            medBase = med,
                            medInteracted = medInteragente,
                            risk = interacao.risk,
                            description = interacao.description,
                            alertLevel = interacao.alertLevel,
                        )
                    )
                }
            }
        }
    }
    return alertas.sortedBy { it.alertLevel }
}

fun verifyRisks(
    medList: Set<Med>,
    conditions: Set<MedicalCondition>,
    interactions: State<List<InteractionAlert>>,
): List<RiskAlert> {
    val risks = mutableListOf<RiskAlert>()

    medList.forEach { med ->
        med.risks.forEach { risk ->
            risks.add(RiskAlert(risk = risk, type = RiskAlertType.MED, description = med.name))
        }
    }

    conditions.forEach { condition ->
        condition.commonRisks.forEach { risk ->
            risks.add(RiskAlert(risk = risk, type = RiskAlertType.CONDITION, description = condition.name))
        }
    }

    interactions.value.forEach { interation ->
        risks.add(
            RiskAlert(
                risk = interation.risk,
                type = RiskAlertType.INTERACTION,
                description = "${interation.medBase.name} e ${interation.medInteracted.name}"
            )
        )
    }

    return risks.sortedByDescending { it.risk.name }
}

enum class RiskAlertType(val iconName: String, val text: String) {
    MED("medication", "Medicamento"),
    CONDITION("medical_information", "Condição"),
    INTERACTION("brightness_alert", "Interação")
}

data class RiskAlert(
    val risk: Risk,
    val type: RiskAlertType,
    val description: String,
)