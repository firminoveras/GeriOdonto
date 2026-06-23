package com.firmino.geriodonto.data.database

import androidx.room.TypeConverter
import com.firmino.geriodonto.companions.InteractionAlertLevel
import com.firmino.geriodonto.companions.MedClass
import com.firmino.geriodonto.companions.Risk
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GeriodontoConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromRiskList(value: List<Risk>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toRiskList(value: String): List<Risk> {
        val listType = object : TypeToken<List<Risk>>() {}.type
        return gson.fromJson(value, listType) ?: emptyList()
    }

    @TypeConverter
    fun fromMedClass(value: MedClass): String = value.name

    @TypeConverter
    fun toMedClass(value: String): MedClass = enumValueOf(value)

    @TypeConverter
    fun fromRisk(value: Risk): String = value.name

    @TypeConverter
    fun toRisk(value: String): Risk = enumValueOf(value)

    @TypeConverter
    fun fromInteractionAlertLevel(value: InteractionAlertLevel): String = value.name

    @TypeConverter
    fun toInteractionAlertLevel(value: String): InteractionAlertLevel = enumValueOf(value)
}