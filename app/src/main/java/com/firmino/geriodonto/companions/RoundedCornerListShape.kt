package com.firmino.geriodonto.companions

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun roundedCornerListShape(
    index: Int,
    total: Int,
    smallBorderShape: CornerBasedShape = MaterialTheme.shapes.small,
    bigBorderShape: CornerBasedShape = MaterialTheme.shapes.extraLarge,
): CornerBasedShape {
    var shape: CornerBasedShape = smallBorderShape
    val bigShape  = bigBorderShape.topStart

    if (index == 0) shape = shape.copy(topStart = bigShape, topEnd = bigShape)
    if (index == (total-1)) shape = shape.copy(bottomStart = bigShape, bottomEnd = bigShape)

    return shape
}
