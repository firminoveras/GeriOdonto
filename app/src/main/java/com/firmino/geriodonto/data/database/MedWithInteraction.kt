package com.firmino.geriodonto.data.database

import androidx.room.Embedded
import androidx.room.Relation

data class MedWithInteractions(
    @Embedded val med: MedEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "ownerMedId",
    )
    val interactions: List<InteractionEntity>,
)