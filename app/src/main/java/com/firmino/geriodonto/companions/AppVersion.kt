package com.firmino.geriodonto.companions

import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberAppVersion(): String {
    val context = LocalContext.current
    return remember {
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            pInfo.versionName ?: "Beta"
        } catch (e: PackageManager.NameNotFoundException) {
            "Beta"
        }
    }
}