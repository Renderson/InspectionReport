package com.example.inspectionreport.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = Blue200,
    primaryVariant = Purple700,
    secondary = Blue200,
    background = backgroundDarkColor,
    surface = Color.Black,
    onPrimary = colorPrimaryDark,
    onSecondary = colorDarkText,
    onBackground = colorDarkText,
    onSurface = colorDarkText,
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = Blue500,
    primaryVariant = Purple700,
    secondary = Blue500,
    background = backgroundColor,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = colorTextHeading,
    onBackground = colorTextHeading,
    onSurface = colorTextHeading,
)

@Composable
fun InspectionReportTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}