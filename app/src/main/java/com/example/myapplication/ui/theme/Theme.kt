package com.example.myapplication.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = GrayLight,
    surface = Gray,
    background = Black,
    onSurface = Light,
    onBackground = Color.White,
    onPrimary = Teal,
    secondary = Black08
)

@Composable
fun NewsTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}