package com.example.core.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

import androidx.compose.ui.graphics.Color

private val DarkColorScheme =
    darkColorScheme(
        primary = Purple80,
        onPrimary = AlwaysBlack,
        primaryContainer = Purple40,
        onPrimaryContainer = Purple80,
        secondary = PurpleGrey80,
        onSecondary = AlwaysBlack,
        tertiary = Pink80,
        background = DarkBackground,
        onBackground = DarkOnBackground,
        surface = DarkSurface,
        onSurface = DarkOnSurface,
        surfaceVariant = DarkSurfaceVariant,
        onSurfaceVariant = DarkOnSurfaceVariant,
        outline = DarkOutline,
        error = Danger,
        onError = AlwaysWhite
    )

private val LightColorScheme =
    lightColorScheme(
        primary = Purple,
        onPrimary = AlwaysWhite,
        primaryContainer = PurpleLight,
        onPrimaryContainer = Purple,
        secondary = Azul,
        onSecondary = AlwaysWhite,
        tertiary = Pink40,
        background = Color(0xFFF8F9FA),
        onBackground = TextPrimary,
        surface = AlwaysWhite,
        onSurface = TextPrimary,
        surfaceVariant = Color(0xFFF0F0F0),
        onSurfaceVariant = TextSecondary,
        outline = CardBorder,
        error = Danger,
        onError = AlwaysWhite
    )

@Composable
fun SkedulyComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }

            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
