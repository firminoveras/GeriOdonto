package com.firmino.geriodonto.data.database

import com.firmino.geriodonto.viewmodel.Interaction
import com.firmino.geriodonto.viewmodel.Med
import com.firmino.geriodonto.viewmodel.toMed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MedRepository @Inject constructor(
    private val medDao: MedDao,
) {
    fun searchMeds(query: String): Flow<List<Med>> {
        val sqlQuery = "%$query%"

        return medDao.searchMeds(sqlQuery).map { listFromDb ->

            val allInteractingIds = listFromDb
                .flatMap { it.interactions }
                .map { it.interactingMedId }
                .toSet()

            val interactingMeds = if (allInteractingIds.isNotEmpty()) {
                medDao.getMedsByIdsList(allInteractingIds.toList())
            } else {
                emptyList()
            }

            val namesMap = interactingMeds.associate { it.med.id to it.med.name }

            listFromDb.map { medWithInt ->
                val mappedInteractions = medWithInt.interactions.map { dbInteraction ->
                    Interaction(
                        interactingMedName = namesMap[dbInteraction.interactingMedId] ?: "Desconhecido",
                        interactingMedId = dbInteraction.interactingMedId,
                        risk = dbInteraction.risk,
                        description = dbInteraction.description,
                        alertLevel = dbInteraction.alertLevel
                    )
                }
                medWithInt.toMed(mappedInteractions)
            }
        }
    }
}