package com.malopieds.simpleweather.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ColorPalette(
    val background: Color,
    val accent: Color,
    val onAccent: Color,
    val text: Color,
    val isDark: Boolean
    )

val DarkColorPalette = ColorPalette(
    background = Color(0xff1f2029),
    text = Color(0xffe1e1e2),
    accent = Color(0xff4046bf),
    onAccent = Color.White,
    isDark = true
)

val LightColorPalette = ColorPalette(
    background = Color(0xfff8f8fc),
    text = Color(0xff212121),
    accent = Color(0xff4046bf),
    onAccent = Color.White,
    isDark = false
)

val TrueDarkColorPalette = DarkColorPalette.copy(
    background = Color.Black
)
