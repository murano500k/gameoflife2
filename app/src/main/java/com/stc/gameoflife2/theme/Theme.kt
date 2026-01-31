package com.stc.gameoflife2.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

// Custom Game Colors
data class GameColors(
    val cellAlive: Color,
    val cellGlow: Color,
    val cellCore: Color,
    val cellPreview: Color,
    val gridBackground: Color,
    val gridLines: Color,
    val categoryStillLife: Color,
    val categoryOscillator: Color,
    val categorySpaceship: Color,
    val categoryMethuselah: Color,
    val categoryGun: Color,
    val categoryOther: Color
)

val LocalGameColors = staticCompositionLocalOf {
    GameColors(
        cellAlive = CellAliveNeon,
        cellGlow = CellAliveGlow,
        cellCore = CellAliveCore,
        cellPreview = CellPreview,
        gridBackground = GridDarkBackground,
        gridLines = GridDarkLines,
        categoryStillLife = CategoryStillLife,
        categoryOscillator = CategoryOscillator,
        categorySpaceship = CategorySpaceship,
        categoryMethuselah = CategoryMethuselah,
        categoryGun = CategoryGun,
        categoryOther = CategoryOther
    )
}

private val DarkColorScheme = darkColorScheme(
    primary = NeonGreen,
    onPrimary = Color(0xFF003920),
    primaryContainer = NeonGreenSubtle,
    onPrimaryContainer = NeonGreenLight,
    secondary = ElectricCyan,
    onSecondary = Color(0xFF003640),
    secondaryContainer = Color(0xFF004D5C),
    onSecondaryContainer = ElectricCyanLight,
    tertiary = PlasmaPurple,
    onTertiary = Color(0xFF3B1A5C),
    tertiaryContainer = Color(0xFF523275),
    onTertiaryContainer = PlasmaPurpleLight,
    error = NeonRed,
    onError = Color(0xFF690014),
    errorContainer = Color(0xFF930020),
    onErrorContainer = NeonRedLight,
    background = DarkBackground,
    onBackground = Color(0xFFE1E3E1),
    surface = DarkSurface,
    onSurface = Color(0xFFE1E3E1),
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = Color(0xFFC1C9C4),
    outline = Color(0xFF8B938E),
    outlineVariant = Color(0xFF414942),
    inverseSurface = Color(0xFFE1E3E1),
    inverseOnSurface = DarkSurface,
    inversePrimary = NeonGreenDark,
    surfaceTint = NeonGreen
)

private val LightColorScheme = lightColorScheme(
    primary = NeonGreenDark,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB4F5D0),
    onPrimaryContainer = Color(0xFF002111),
    secondary = ElectricCyanDark,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFB0EEFF),
    onSecondaryContainer = Color(0xFF001F26),
    tertiary = PlasmaPurpleDark,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFEBDDFF),
    onTertiaryContainer = Color(0xFF250059),
    error = NeonRedDark,
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD9),
    onErrorContainer = Color(0xFF410008),
    background = LightBackground,
    onBackground = Color(0xFF191C1A),
    surface = LightSurface,
    onSurface = Color(0xFF191C1A),
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = Color(0xFF414942),
    outline = Color(0xFF717972),
    outlineVariant = Color(0xFFC1C9C2),
    inverseSurface = Color(0xFF2E312E),
    inverseOnSurface = LightSurfaceVariant,
    inversePrimary = NeonGreenLight,
    surfaceTint = NeonGreenDark
)

private val DarkGameColors = GameColors(
    cellAlive = CellAliveNeon,
    cellGlow = CellAliveGlow,
    cellCore = CellAliveCore,
    cellPreview = CellPreview,
    gridBackground = GridDarkBackground,
    gridLines = GridDarkLines,
    categoryStillLife = CategoryStillLife,
    categoryOscillator = CategoryOscillator,
    categorySpaceship = CategorySpaceship,
    categoryMethuselah = CategoryMethuselah,
    categoryGun = CategoryGun,
    categoryOther = CategoryOther
)

private val LightGameColors = GameColors(
    cellAlive = NeonGreenDark,
    cellGlow = Color(0x4000C96A),
    cellCore = NeonGreenDark,
    cellPreview = Color(0x6000C96A),
    gridBackground = GridLightBackground,
    gridLines = GridLightLines,
    categoryStillLife = CategoryStillLife,
    categoryOscillator = CategoryOscillator,
    categorySpaceship = CategorySpaceship,
    categoryMethuselah = CategoryMethuselah,
    categoryGun = CategoryGun,
    categoryOther = CategoryOther
)

val Shapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

@Composable
fun GameOfLife2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Disabled to use our custom colors
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val gameColors = if (darkTheme) DarkGameColors else LightGameColors

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    CompositionLocalProvider(LocalGameColors provides gameColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

// Extension for easy access
object GameTheme {
    val gameColors: GameColors
        @Composable
        get() = LocalGameColors.current
}
