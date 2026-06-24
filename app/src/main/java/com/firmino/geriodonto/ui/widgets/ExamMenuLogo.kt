package com.firmino.geriodonto.ui.widgets

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.firmino.geriodonto.R
import com.firmino.geriodonto.companions.rememberAppVersion
import com.firmino.geriodonto.ui.theme.fontFamilyBaumans
import com.firmino.geriodonto.ui.theme.fontFamilyPoiret

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BoxScope.ExamMenuLogo(
    medDataVersion: String,
){
    val appVersion = rememberAppVersion()
    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onSecondary,
        ),
    )
    val surfaceColor = MaterialTheme.colorScheme.surface
    val rotationAngle by rememberInfiniteTransition(label = "InfiniteRotationTransition").animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "RotationAngleAnimation",
    )

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
        Box {
            Image(
                modifier = Modifier
                    .size(128.dp)
                    .graphicsLayer(alpha = 0.99f)
                    .drawWithContent {
                        drawContent()
                        drawRect(brush = gradientBrush, blendMode = BlendMode.SrcIn)
                    },
                imageVector = ImageVector.vectorResource(R.drawable.ic_tooth),
                contentDescription = null,
            )
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(32.dp)
                    .offset(y = (-12).dp)
                    .graphicsLayer(alpha = 0.99f)
                    .drawWithContent {
                        drawContent()
                        drawRect(brush = gradientBrush, blendMode = BlendMode.SrcIn)
                    },
                imageVector = ImageVector.vectorResource(R.drawable.ic_icon_head),
                contentDescription = null,
            )

            Image(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(96.dp)
                    .offset(x = 22.dp, y = (2).dp)
                    .graphicsLayer(alpha = 0.99f)
                    .drawWithContent {
                        drawContent()
                        drawRect(color = surfaceColor, blendMode = BlendMode.SrcIn)
                    },
                imageVector = ImageVector.vectorResource(R.drawable.ic_pill_bg),
                contentDescription = null,
            )

            Image(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(84.dp)
                    .offset(x = 16.dp, y = (-4).dp)
                    .graphicsLayer(alpha = 0.99f)
                    .drawWithContent {
                        drawContent()
                        drawRect(brush = gradientBrush, blendMode = BlendMode.SrcIn)
                    },
                imageVector = ImageVector.vectorResource(R.drawable.ic_pill_fg),
                contentDescription = null,
            )
        }

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
        Text(
            modifier = Modifier.padding(vertical = 6.dp, horizontal = 18.dp),
            text = "MedData $medDataVersion • App v$appVersion",
            color = MaterialTheme.colorScheme.outline,
            style = MaterialTheme.typography.labelSmall,
        )
        Spacer(Modifier.height(128.dp))
    }

}
