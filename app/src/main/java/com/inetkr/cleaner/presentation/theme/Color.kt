package com.junnew.design_system.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val md_primary_light = Color(0xFF0F62FE)
val md_onPrimary_light = Color(0xFFFFFFFF)
val md_background_light = Color(0xFFFFFFFF)
val md_onBackground_light = Color(0xFF111318)

val md_primary_dark = Color(0xFF78A6FF)
val md_onPrimary_dark = Color(0xFF00308C)
val md_background_dark = Color(0xFF0B0B0F)
val md_onBackground_dark = Color(0xFFE6E6E6)

data class AppColors(
    val brand: Color,
    val brandMuted: Color,
    val success: Color,
    val warning: Color,
    val error: Color,
    val appContainer: Color,
    val onAppContainer: Color,
    val purpleBlue: Color,
    val purpleBlueOpa: Color,
    val white: Color,
    val pink : Color,
    val grey : Color,
    val black : Color
)

val AppColorsLight = AppColors(
    brand = md_primary_light,
    brandMuted = Color(0xFFCCD9FF),
    success = Color(0xFF12B669),
    warning = Color(0xFFFFB020),
    error = Color(0xFFDE3B40),
    appContainer = Color(0xFFFFEBEE),
    onAppContainer = Color(0xFF8A1C1C),
    purpleBlue = Color(0xFF3936CD),
    purpleBlueOpa = Color(0xFF9392F6),
    white = Color(0xFFFFFFFF),
    pink = Color(0xFFC7A2F0),
    grey = Color(0xFFB2B2B2),
    black = Color(0xFF000000)
)

val AppColorsDark = AppColors(
    brand = md_primary_dark,
    brandMuted = Color(0xFF325CA8),
    success = Color(0xFF35D487),
    warning = Color(0xFFFFC555),
    error = Color(0xFFFF6B6F),
    appContainer = Color(0xFF0A1E3A),
    onAppContainer = Color(0xFFCCE0FF),
    purpleBlue = Color(0xFF3936CD),
    purpleBlueOpa = Color(0xFF9392F6),
    white = Color(0xFFFFFFFF),
    pink = Color(0xFFC7A2F0),
    grey = Color(0xFFB2B2B2),
    black = Color(0xFF000000)
)