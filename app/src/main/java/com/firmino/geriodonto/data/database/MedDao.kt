package com.firmino.geriodonto.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.firmino.geriodonto.data.MedClass
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
    @Query("""
    SELECT * FROM medications
    WHERE name LIKE :query OR description LIKE :query
    ORDER BY 
        CASE 
            WHEN name LIKE :query THEN 1 
            ELSE 2 
        END ASC,
        name ASC
    LIMIT 20
    """)
    fun searchMeds(query: String): Flow<List<MedWithInteractions>>

    @Transaction
    @Query("SELECT * FROM medications WHERE id IN (:medIds)")
    suspend fun getMedsByIdsList(medIds: List<String>): List<MedWithInteractions>

    @Transaction
    @Query("SELECT * FROM medications WHERE id = :id")
    suspend fun getMedById(id: String): MedWithInteractions?

    @Transaction
    @Query("SELECT * FROM medications WHERE medClass = :medClass")
    fun getMedsByClass(medClass: MedClass): Flow<List<MedWithInteractions>>

    @Transaction
    suspend fun wipeAndSeedDatabase(meds: List<MedEntity>, interactions: List<InteractionEntity>) {
        deleteAllInteractions()
        deleteAllMeds()
        if (meds.isNotEmpty()) insertMeds(meds)
        if (interactions.isNotEmpty()) insertInteractions(interactions)
    }

    @Query("DELETE FROM interactions")
    suspend fun deleteAllInteractions()

    @Query("DELETE FROM medications")
    suspend fun deleteAllMeds()
}