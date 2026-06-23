package com.firmino.geriodonto.data.database

import com.firmino.geriodonto.companions.InteractionAlertLevel
import com.firmino.geriodonto.companions.MedClass
import com.firmino.geriodonto.companions.Risk
import com.google.gson.annotations.SerializedName

data class DatabasePayloadDto(
    @SerializedName("version") val version: Int,
    @SerializedName("medications") val medications: List<MedSeedDto>,
    @SerializedName("interactions") val interactions: List<InteractionSeedDto>
)

data class MedSeedDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("principleActive") val principleActive: String,
    @SerializedName("medClass") val medClass: MedClass,
    @SerializedName("byDisease") val byDisease: String,
    @SerializedName("risks") val risks: List<Risk>
)

data class InteractionSeedDto(
    @SerializedName("id1") val id1: String,
    @SerializedName("id2") val id2: String,
    @SerializedName("risk") val risk: Risk,
    @SerializedName("description") val description: String,
    @SerializedName("alertLevel") val alertLevel: InteractionAlertLevel
)