package com.junnew.design_system.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

private val LightScheme = lightColorScheme(
    primary = md_primary_light,
    onPrimary = md_onPrimary_light,
    background = md_background_light,
    onBackground = md_onBackground_light
)

private val DarkScheme = darkColorScheme(
    primary = md_primary_dark,
    onPrimary = md_onPrimary_dark,
    background = md_background_dark,
    onBackground = md_onBackground_dark
)

// App custom colors
val LocalAppColors = staticCompositionLocalOf { AppColorsLight }

val MaterialTheme.appColors: AppColors
    @Composable
    get() = LocalAppColors.current

@Composable
fun JunNewTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val materialColors = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkScheme
        else -> LightScheme
    }

    val customColors = if (darkTheme) AppColorsDark else AppColorsLight

    CompositionLocalProvider(LocalAppColors provides customColors,
        LocalDimens provides Dimens()
    ) {
        MaterialTheme(
            colorScheme = materialColors,
            typography = appTypography,
            shapes = AppShapes,
            content = content
        )
    }
}