package com.firmino.geriodonto.data.database

import com.firmino.geriodonto.data.MedClass
import com.firmino.geriodonto.viewmodel.Interaction
import com.firmino.geriodonto.viewmodel.Med
import com.firmino.geriodonto.viewmodel.toMed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MedRepository @Inject constructor(private val medDao: MedDao) {
    fun searchMeds(query: String): Flow<List<Med>> {
        val sqlQuery = "%$query%"
        return medDao.searchMeds(sqlQuery).map { processMeds(it) }
    }

    fun searchMedsByClass(className: MedClass): Flow<List<Med>> {
        return medDao.getMedsByClass(className).map { processMeds(it) }
    }

    suspend fun getMedById(id: String): Med? {
        val med = medDao.getMedById(id) ?: return null
        return processMeds(listOf(med)).firstOrNull()
    }

    private suspend fun processMeds(listFromDb: List<MedWithInteractions>): List<Med> {
        if (listFromDb.isEmpty()) return emptyList()

        val allInteractingIds = listFromDb
            .flatMap { it.interactions }
            .map { it.interaction.interactingMedId }
            .distinct()

        val namesMap = medDao.getMedsByIdsList(allInteractingIds).associate { it.med.id to it.med.name }

        return listFromDb.map { medWithInt ->
            val mappedInteractions = medWithInt.interactions.sortedBy { it.interaction.alertLevel } .map { dbInt ->
                Interaction(
                    interactingMedName = namesMap[dbInt.interaction.interactingMedId] ?: "Desconhecido",
                    interactingMedId = dbInt.interaction.interactingMedId,
                    risk = dbInt.interaction.risk,
                    description = dbInt.interaction.description,
                    alertLevel = dbInt.interaction.alertLevel,
                )
            }
            medWithInt.toMed(mappedInteractions)
        }
    }
}