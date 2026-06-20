package com.firmino.geriodonto.data.database

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class DatabaseSeeder @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val medDao: MedDao,
) {
    private val jsonVersion = 13
    suspend fun checkAndSeedDatabase() {
        val prefs = context.getSharedPreferences("geriodonto_prefs", Context.MODE_PRIVATE)
        val savedVersion = prefs.getInt("json_version", 0)

        if (jsonVersion > savedVersion) {
            populateDatabase()
            prefs.edit { putInt("json_version", jsonVersion) }
        }
    }

    private suspend fun populateDatabase() {
        withContext(Dispatchers.IO) {
            try {
                val jsonString = context.assets.open("medications.json").bufferedReader().use { it.readText() }
                val listType = object : TypeToken<List<MedSeedDto>>() {}.type
                val seedData: List<MedSeedDto> = Gson().fromJson(jsonString, listType)

                val medEntities = mutableListOf<MedEntity>()
                val interactionEntities = mutableListOf<InteractionEntity>()

                seedData.forEach { dto ->
                    medEntities.add(
                        MedEntity(
                            id = dto.id, name = dto.name, description = dto.description,
                            principleActive = dto.principleActive, medClass = dto.medClass,
                            byDisease = dto.byDisease, risks = dto.risks,
                        ),
                    )
                    dto.interactions.forEach { interaction ->
                        interactionEntities.add(
                            InteractionEntity(
                                ownerMedId = dto.id, interactingMedId = interaction.interactingMedId,
                                risk = interaction.risk, description = interaction.description, alertLevel = interaction.alertLevel,
                            ),
                        )
                    }
                }
                medDao.deleteAllInteractions()
                medDao.deleteAllMeds()
                medDao.insertMeds(medEntities)
                medDao.insertInteractions(interactionEntities)
                delay(2000.milliseconds)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
