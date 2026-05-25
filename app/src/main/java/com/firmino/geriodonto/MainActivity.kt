package com.firmino.geriodonto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalFloatingToolbar
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.material3.rememberSliderState
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.firmino.geriodonto.companions.MaterialSymbol
import com.firmino.geriodonto.companions.MedicalCondition
import com.firmino.geriodonto.companions.rememberAppVersion
import com.firmino.geriodonto.ui.pages.ExamPageDiaseses
import com.firmino.geriodonto.ui.pages.ExamPagePersonal
import com.firmino.geriodonto.ui.theme.GeriOdontoTheme
import com.firmino.geriodonto.ui.theme.fontFamilyBaumans
import com.firmino.geriodonto.ui.theme.fontFamilyPoiret
import kotlinx.coroutines.launch


enum class ExamPages(val text: String, val symbolName: String) {
    PAGE_PERSONAL("Pessoal", "account_box"),
    PAGE_DIASESES("Comorbidades", "medical_information"),
    PAGE_PRESCRIPTION("Prescrição", "admin_meds"),
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeriOdontoTheme {
                Content()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Content() {
    val sheetState = rememberBottomSheetState(
        initialValue = SheetValue.Hidden,
        enabledValues = setOf(SheetValue.Hidden, SheetValue.Expanded),
    )
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    Scaffold { contentPadding ->
        Column(Modifier.padding(contentPadding)) {
            Menu(onAddClick = { showBottomSheet = true })
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState,
            ) {
                Exam {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Menu(onAddClick: () -> Unit) {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onSecondary,
        ),
    )
    val appVersion = rememberAppVersion()

    val rotationAngle by rememberInfiniteTransition(label = "InfiniteRotationTransition").animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "RotationAngleAnimation",
    )

    Box(Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-400).dp, x = 400.dp)
                .requiredSize(800.dp)
                .alpha(.1f)
                .rotate(rotationAngle)
                .clip(MaterialShapes.Cookie6Sided.toShape()),
            painter = ColorPainter(MaterialTheme.colorScheme.primary),
            contentDescription = null,
        )

        Image(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-300).dp, x = 300.dp)
                .requiredSize(600.dp)
                .alpha(.1f)
                .rotate(rotationAngle)
                .clip(MaterialShapes.Cookie7Sided.toShape()),
            painter = ColorPainter(MaterialTheme.colorScheme.primary),
            contentDescription = null,
        )


        Image(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-150).dp, x = 150.dp)
                .requiredSize(300.dp)
                .alpha(.1f)
                .rotate(rotationAngle)
                .clip(MaterialShapes.Cookie9Sided.toShape()),
            painter = ColorPainter(MaterialTheme.colorScheme.primary),
            contentDescription = null,
        )

        Image(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-200).dp, x = 200.dp)
                .requiredSize(400.dp)
                .alpha(.1f)
                .rotate(rotationAngle)
                .clip(MaterialShapes.Cookie12Sided.toShape()),
            painter = ColorPainter(MaterialTheme.colorScheme.primary),
            contentDescription = null,
        )

        Image(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(y = 400.dp, x = (-400).dp)
                .requiredSize(800.dp)
                .alpha(.1f)
                .rotate(rotationAngle)
                .clip(MaterialShapes.Cookie6Sided.toShape()),
            painter = ColorPainter(MaterialTheme.colorScheme.primary),
            contentDescription = null,
        )

        Image(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(y = 300.dp, x = (-300).dp)
                .requiredSize(600.dp)
                .alpha(.1f)
                .rotate(rotationAngle)
                .clip(MaterialShapes.Cookie7Sided.toShape()),
            painter = ColorPainter(MaterialTheme.colorScheme.primary),
            contentDescription = null,
        )


        Image(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(y = 150.dp, x = (-150).dp)
                .requiredSize(300.dp)
                .alpha(.1f)
                .rotate(rotationAngle)
                .clip(MaterialShapes.Cookie9Sided.toShape()),
            painter = ColorPainter(MaterialTheme.colorScheme.primary),
            contentDescription = null,
        )

        Image(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(y = 200.dp, x = (-200).dp)
                .requiredSize(400.dp)
                .alpha(.1f)
                .rotate(rotationAngle)
                .clip(MaterialShapes.Cookie12Sided.toShape()),
            painter = ColorPainter(MaterialTheme.colorScheme.primary),
            contentDescription = null,
        )

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .size(128.dp)
                    .graphicsLayer(alpha = 0.99f)
                    .drawWithContent {
                        drawContent()
                        drawRect(brush = gradientBrush, blendMode = BlendMode.SrcIn)
                    },
                imageVector = ImageVector.vectorResource(R.drawable.ic_icon),
                contentDescription = null,
            )
            Row {
                Text(
                    text = "Geri",
                    fontFamily = fontFamilyBaumans,
                    style = MaterialTheme.typography.displayLarge,
                )
                Text(
                    text = "Odonto",
                    fontFamily = fontFamilyPoiret,
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Text(
                text = "Prescrição Segura em Odontogeriatria",
                style = MaterialTheme.typography.labelMedium,
            )
            Spacer(Modifier.height(128.dp))
        }

        VerticalFloatingToolbar(
            modifier = Modifier.align(Alignment.BottomEnd),
            expanded = true,
            floatingActionButton = {
                FloatingActionButton(onClick = onAddClick) {
                    MaterialSymbol(iconName = "add")
                }
            },
        ) {
            IconButton(onClick = {}) { MaterialSymbol(iconName = "info") }
            IconButton(onClick = {}) { MaterialSymbol(iconName = "policy") }
        }

        Text(
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .align(Alignment.BottomStart),
            text = appVersion,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.labelMediumEmphasized,
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Exam(onDismiss: () -> Unit) {
    val pagerState = rememberPagerState { ExamPages.entries.size }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    val name = rememberTextFieldState("")
    val genre = rememberTextFieldState("")
    val weight = rememberSliderState(value = 0f)
    val age = rememberSliderState(value = 0f)
    val renalFunction = rememberSliderState(value = 0f)
    val hepaticTgo = rememberSliderState(value = 0f)
    val hepaticTgp = rememberSliderState(value = 0f)

    var hasFallHistory by remember { mutableStateOf(false) }
    var hasFragile by remember { mutableStateOf(false) }

    val diaseseList = remember { mutableStateListOf<MedicalCondition>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) { detectTapGestures { focusManager.clearFocus() } },
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "Nova prescrição", style = MaterialTheme.typography.titleLarge)
            Button(onClick = onDismiss) { Text("Fechar") }
        }
        HorizontalDivider()
        ExamMenu(
            currentPage = pagerState.currentPage,
            onChange = { page -> scope.launch { pagerState.animateScrollToPage(page) } },
        )
        HorizontalPager(state = pagerState, userScrollEnabled = false) { index ->
            when (index) {
                ExamPages.PAGE_PERSONAL.ordinal -> {
                    ExamPagePersonal(
                        name = name,
                        genre = genre,
                        age = age,
                        weight = weight,
                        renalFunction = renalFunction,
                        hepaticTgo = hepaticTgo,
                        hepaticTgp = hepaticTgp,
                        hasFallHistory = hasFallHistory,
                        hasFragile = hasFragile,
                        onHasFragileChange = {hasFragile = it},
                        onHasFallHistoryChange = {hasFallHistory = it},
                        onNext = {
                            scope.launch {
                                pagerState.animateScrollToPage(ExamPages.PAGE_DIASESES.ordinal)
                            }
                        },
                    )
                }

                ExamPages.PAGE_DIASESES.ordinal -> {
                    ExamPageDiaseses(
                        medicalConditionList = diaseseList,
                        onAdd = {
                            if (!diaseseList.contains(it)) diaseseList.add(it)
                        },
                        onRemove = { diaseseList.remove(it) },
                    )
                }

                ExamPages.PAGE_PRESCRIPTION.ordinal -> {
                    Text(text = "$index")
                }
            }
        }
    }

}


@Composable
fun ExamMenu(currentPage: Int, onChange: (Int) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ExamPages.entries.forEach { page ->
            val color = if (page.ordinal == currentPage) MaterialTheme.colorScheme.primary else Color.Transparent
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
                    MaterialSymbol(
                        iconName = page.symbolName,
                        filled = page.ordinal == currentPage,
                        colorFilled = MaterialTheme.colorScheme.onPrimary,
                    )
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
