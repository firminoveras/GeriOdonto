package com.firmino.geriodonto.data.database

import androidx.room.Embedded
import androidx.room.Relation
import com.firmino.geriodonto.data.MedClass
import com.firmino.geriodonto.data.Risk

data class MedWithInteractions(
    @Embedded val med: MedEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "ownerMedId",
    )
    val interactions: List<InteractionEntity>,
) {
    fun toMed(addedBy: String = "") = Med(
        id = this.med.id,
        name = this.med.name,
        description = this.med.description,
        principleActive = this.med.principleActive,
        medClass = this.med.medClass,
        byDisease = this.med.byDisease,
        risks = this.med.risks,
        interactions = this.interactions,
        addedBy = addedBy,
    )
}

data class Med(
    val id: String,
    val name: String,
    val description: String,
    val principleActive: String,
    val medClass: MedClass,
    val byDisease: String,
    val risks: List<Risk>,
    val interactions: List<InteractionEntity>,
    val addedBy: String = "",
)

