package com.firmino.geriodonto.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.firmino.geriodonto.R

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = 0.5.sp,
    ),
)

val fontFamilyBaumans = FontFamily(Font(R.font.font_baumans))
val fontFamilyPoiret = FontFamily(Font(R.font.font_poiret))