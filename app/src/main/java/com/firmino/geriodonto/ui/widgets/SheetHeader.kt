package com.firmino.geriodonto.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.firmino.geriodonto.companions.MaterialSymbol

@Composable
fun SheetHeader(
    iconName: String,
    title: String,
    subtitle: String,
    content: @Composable RowScope.() -> Unit = {},
) {
    Box {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MaterialSymbol(iconName = iconName, size = 64.sp)
            Text(text = title, style = MaterialTheme.typography.titleLarge)
            if (subtitle.isNotEmpty()) Text(text = subtitle, style = MaterialTheme.typography.titleSmall)
            Spacer(Modifier.height(12.dp))
            HorizontalDivider()
        }

        Row(
            modifier = Modifier.align(Alignment.TopEnd),
            content = content,
        )
    }
}
