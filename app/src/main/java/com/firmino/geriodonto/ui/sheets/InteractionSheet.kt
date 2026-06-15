package com.firmino.geriodonto.ui.sheets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.firmino.geriodonto.companions.MaterialSymbol
import com.firmino.geriodonto.data.PatientState
import com.firmino.geriodonto.ui.pages.InteractionnsPageInteractions
import com.firmino.geriodonto.ui.pages.InteractionnsPageRisks
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InteractionSheet(
    patient: PatientState,
    onClose: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState { InteractionPages.entries.size }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart),
            ) {
                Text(
                    text = "Interações e Riscos",
                    style = MaterialTheme.typography.titleLarge,
                )
                if (patient.name.text.isNotEmpty()) {
                    Text(
                        text = patient.name.text.toString().split(" ").joinToString(" ", limit = 2, truncated = ""),
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1,
                    )
                }

            }
            Row(Modifier.align(Alignment.CenterEnd)) {
                IconButton(
                    onClick = { onClose() },
                    content = { MaterialSymbol(iconName = "close") },
                )
            }
        }
        HorizontalDivider()
        InteractionMenu(
            currentPage = pagerState.currentPage,
            patient = patient,
            onChange = { page -> scope.launch { pagerState.animateScrollToPage(page) } },
        )
        HorizontalPager(state = pagerState, userScrollEnabled = false) { index ->
            when (index) {
                InteractionPages.PAGE_INTERACTIONS.ordinal -> {
                    InteractionnsPageInteractions(patient)
                }

                InteractionPages.PAGE_RISKS.ordinal -> {
                    InteractionnsPageRisks(patient)
                }
            }
        }
    }
}

enum class InteractionPages(val text: String, val symbolName: String) {
    PAGE_INTERACTIONS("Interações", "brightness_alert"),
    PAGE_RISKS("Riscos", "warning"),
}

@Composable
fun InteractionMenu(
    currentPage: Int,
    patient: PatientState,
    onChange: (Int) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        InteractionPages.entries.forEach { page ->
            val current = page.ordinal == currentPage
            val color = if (current) MaterialTheme.colorScheme.primary else Color.Transparent
            val size = when (page) {
                InteractionPages.PAGE_INTERACTIONS -> patient.interactions.value.size
                InteractionPages.PAGE_RISKS -> patient.risks.value.size
            }
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                color = color,
                onClick = { onChange(page.ordinal) },
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    BadgedBox(badge = { if (size > 0) Badge { Text("$size") } }) {
                        MaterialSymbol(
                            iconName = page.symbolName,
                            filled = page.ordinal == currentPage,
                            colorFilled = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                    AnimatedVisibility(page.ordinal == currentPage) {
                        Text(
                            text = page.text,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                }
            }
        }
    }
}