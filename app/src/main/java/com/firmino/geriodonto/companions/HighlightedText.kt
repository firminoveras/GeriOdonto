package com.firmino.geriodonto.companions

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun highlightedText(
    fullText: String,
    target: String,
): AnnotatedString {
    return buildAnnotatedString {
        var startIndex = 0
        while (startIndex < fullText.length) {
            val index = fullText.indexOf(target, startIndex, ignoreCase = true)
            if (index == -1) {
                append(fullText.substring(startIndex))
                break
            }
            append(fullText.substring(startIndex, index))
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)) {
                append(fullText.substring(index, index + target.length))
            }
            startIndex = index + target.length
        }
    }
}
