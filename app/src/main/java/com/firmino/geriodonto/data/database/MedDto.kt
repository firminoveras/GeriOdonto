package com.firmino.geriodonto.data.database

import com.firmino.geriodonto.data.InteractionAlertLevel
import com.firmino.geriodonto.data.MedClass
import com.firmino.geriodonto.data.Risk

data class MedSeedDto(
    val id: String,
    val name: String,
    val description: String,
    val principleActive: String,
    val medClass: MedClass,
    val byDisease: String,
    val risks: List<Risk>,
    val interactions: List<InteractionSeedDto>
)

data class InteractionSeedDto(
    val interactingMedId: String,
    val risk: Risk,
    val description: String,
    val alertLevel: InteractionAlertLevel
)