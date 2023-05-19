package com.example.foodflix.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = OffBlackBlueHintDarker,
    primaryVariant = OffBlackBlueHint,
    secondary = OffBlackBlueHintLighter,

    background = OffBlackBlueHint,
    surface = androidx.compose.ui.graphics.Color.White,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    onSecondary = OffBlackBlueHintLighter,
    onBackground = androidx.compose.ui.graphics.Color.White,
    onSurface = OffBlackBlueHintLighter
)

private val LightColorPalette = lightColors(
    primary = OffBlackBlueHintDarker,
    primaryVariant = OffBlackBlueHint,
    secondary = OffBlackBlueHintLighter,

    secondaryVariant = OffBlackBlueHintLighter,
    background = OffBlackBlueHint,
    surface = OffBlackBlueHint,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    onBackground = androidx.compose.ui.graphics.Color.White,
    onSurface = androidx.compose.ui.graphics.Color.White
)

@Composable
fun FoodflixTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
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