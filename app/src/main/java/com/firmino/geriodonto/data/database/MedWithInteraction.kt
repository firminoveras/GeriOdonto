package com.firmino.geriodonto.data.database

import androidx.room.Embedded
import androidx.room.Relation

data class MedWithInteractions(
    @Embedded val med: MedEntity,

    @Relation(
        entity = InteractionEntity::class,
        parentColumn = "id",
        entityColumn = "ownerMedId"
    )
    val interactions: List<InteractionWithTargetMed>
)

data class InteractionWithTargetMed(
    @Embedded val interaction: InteractionEntity,

    @Relation(
        parentColumn = "interactingMedId",
        entityColumn = "id"
    )
    val targetMed: MedEntity
)