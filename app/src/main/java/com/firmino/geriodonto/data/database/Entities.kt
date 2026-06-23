package com.firmino.geriodonto.data.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.firmino.geriodonto.companions.InteractionAlertLevel
import com.firmino.geriodonto.companions.MedClass
import com.firmino.geriodonto.companions.Risk

@Entity(tableName = "medications")
data class MedEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val principleActive: String,
    val medClass: MedClass,
    val byDisease: String,
    val risks: List<Risk>,
)
@Entity(
    tableName = "interactions",
    foreignKeys = [
        ForeignKey(
            entity = MedEntity::class,
            parentColumns = ["id"],
            childColumns = ["ownerMedId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = MedEntity::class,
            parentColumns = ["id"],
            childColumns = ["interactingMedId"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [Index("ownerMedId"), Index("interactingMedId")],
)
data class InteractionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val ownerMedId: String,
    val interactingMedId: String,
    val risk: Risk,
    val description: String,
    val alertLevel: InteractionAlertLevel,
)