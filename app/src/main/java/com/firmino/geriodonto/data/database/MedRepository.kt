package com.firmino.geriodonto.data.database

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MedRepository @Inject constructor(
    private val medDao: MedDao
) {
    val allMedsFlow: Flow<List<MedWithInteractions>> = medDao.getAllMedsWithInteractions()

    suspend fun getMedById(id: String): MedWithInteractions? {
        return medDao.getMedById(id)
    }

    fun searchMeds(query: String): Flow<List<MedWithInteractions>> {
        return medDao.searchMedsByNameOrDescription(query)
    }
}