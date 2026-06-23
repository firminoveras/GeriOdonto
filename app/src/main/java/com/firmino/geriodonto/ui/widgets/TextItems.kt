package com.firmino.geriodonto.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.firmino.geriodonto.companions.roundedCornerListShape

fun LazyListScope.textParagraph(text: String) {
    item {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                textAlign = TextAlign.Justify,
                hyphens = Hyphens.Auto,
                letterSpacing = TextUnit.Unspecified,
                lineBreak = LineBreak.Paragraph.copy(
                    strategy = LineBreak.Strategy.HighQuality,
                    strictness = LineBreak.Strictness.Strict,
                    wordBreak = LineBreak.WordBreak.Phrase,
                ),
            ),
        )
    }
}

fun LazyListScope.textTitle(text: String) {
    item {
        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = text,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

fun LazyListScope.textBox(title: String, text: String) {
    item {
        val shape: @Composable (index: Int) -> CornerBasedShape = { index ->
            roundedCornerListShape(index, 2, MaterialTheme.shapes.extraSmall, MaterialTheme.shapes.medium)
        }
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surfaceContainerHigh, shape = shape(0))
                    .padding(12.dp),
                text = title,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surfaceContainerHigh, shape = shape(1))
                    .padding(12.dp),
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    textAlign = TextAlign.Justify,
                    hyphens = Hyphens.Auto,
                    letterSpacing = TextUnit.Unspecified,
                    lineBreak = LineBreak.Paragraph.copy(
                        strategy = LineBreak.Strategy.HighQuality,
                        strictness = LineBreak.Strictness.Strict,
                        wordBreak = LineBreak.WordBreak.Phrase,
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                ),
            )
        }
    }
}

fun LazyListScope.textBoxTiple(title: String, text: String, text2: String) {
    item {
        val shape: @Composable (index: Int) -> CornerBasedShape = { index ->
            roundedCornerListShape(index, 3, MaterialTheme.shapes.extraSmall, MaterialTheme.shapes.medium)
        }
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surfaceContainerHigh, shape = shape(0))
                    .padding(12.dp),
                text = title,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surfaceContainerHigh, shape = shape(1))
                    .padding(12.dp),
                text = text,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surfaceContainerHigh, shape = shape(2))
                    .padding(12.dp),
                text = text2,
                style = MaterialTheme.typography.bodySmall.copy(
                    textAlign = TextAlign.Justify,
                    hyphens = Hyphens.Auto,
                    letterSpacing = TextUnit.Unspecified,
                    lineBreak = LineBreak.Paragraph.copy(
                        strategy = LineBreak.Strategy.HighQuality,
                        strictness = LineBreak.Strictness.Strict,
                        wordBreak = LineBreak.WordBreak.Phrase,
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                ),
            )
        }
    }
}
