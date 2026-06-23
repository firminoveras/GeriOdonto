package com.firmino.geriodonto.data.database

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

data class SeederData(
    val localVersion: Int,
    val jsonVersion: Int,
)

class DatabaseSeeder @Inject constructor(
    @ApplicationContext private val context: Context,
    private val medDao: MedDao,
) {
    suspend fun getSeedData(): SeederData =
        withContext(Dispatchers.IO) {
            val jsonString = context.assets.open("medications.json").bufferedReader().use { it.readText() }
            val payload = Gson().fromJson(jsonString, DatabasePayloadDto::class.java)

            val prefs = context.getSharedPreferences("geriodonto_prefs", Context.MODE_PRIVATE)
            val savedVersion = prefs.getInt("json_version", 0)

            return@withContext SeederData(savedVersion, payload.version)
        }

    suspend fun populateDatabase() =
        withContext(Dispatchers.IO) {
            val jsonString = context.assets.open("medications.json").bufferedReader().use { it.readText() }
            val payload = Gson().fromJson(jsonString, DatabasePayloadDto::class.java)

            val interactionEntities = mutableListOf<InteractionEntity>()
            val medEntities = payload.medications.map { dto ->
                MedEntity(
                    id = dto.id.trim(),
                    name = dto.name,
                    description = dto.description,
                    principleActive = dto.principleActive,
                    medClass = dto.medClass,
                    byDisease = dto.byDisease,
                    risks = dto.risks,
                )
            }

            val validMedIds = medEntities.map { it.id }.toSet()
            payload.interactions.forEach { interaction ->
                val id1 = interaction.id1.trim()
                val id2 = interaction.id2.trim()

                if (validMedIds.contains(id1) && validMedIds.contains(id2)) {
                    interactionEntities.add(
                        InteractionEntity(
                            ownerMedId = id1,
                            interactingMedId = id2,
                            risk = interaction.risk,
                            description = interaction.description,
                            alertLevel = interaction.alertLevel,
                        ),
                    )
                    interactionEntities.add(
                        InteractionEntity(
                            ownerMedId = id2,
                            interactingMedId = id1,
                            risk = interaction.risk,
                            description = interaction.description,
                            alertLevel = interaction.alertLevel,
                        ),
                    )
                }
            }

            medDao.wipeAndSeedDatabase(medEntities, interactionEntities)

            val prefs = context.getSharedPreferences("geriodonto_prefs", Context.MODE_PRIVATE)
            prefs.edit { putInt("json_version", payload.version) }

            delay(2000.milliseconds)
            return@withContext SeederData(payload.version, payload.version)
        }
}