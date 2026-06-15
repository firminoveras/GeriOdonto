package com.firmino.geriodonto.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface MedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeds(meds: List<MedEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInteractions(interactions: List<InteractionEntity>)

    @Transaction
    @Query("SELECT * FROM medications")
    fun getAllMedsWithInteractions(): Flow<List<MedWithInteractions>>

    @Transaction
    @Query("SELECT * FROM medications WHERE id = :medId")
    suspend fun getMedById(medId: String): MedWithInteractions?

    @Transaction
    @Query("SELECT * FROM medications WHERE name LIKE '%' || :searchQuery || '%' OR description LIKE '%' || :searchQuery || '%'")
    fun searchMedsByNameOrDescription(searchQuery: String): Flow<List<MedWithInteractions>>

    @Query("DELETE FROM interactions")
    suspend fun deleteAllInteractions()

    @Query("DELETE FROM medications")
    suspend fun deleteAllMeds()
}