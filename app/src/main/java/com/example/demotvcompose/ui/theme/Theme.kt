package com.example.demotvcompose.ui.theme

import androidx.compose.runtime.Composable
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.darkColorScheme

// TV Apps are predominantly Dark Mode.
@OptIn(ExperimentalTvMaterial3Api::class)
private val VTVDarkColorScheme = darkColorScheme(
    primary = VTVRed,
    secondary = VTVRedDark,
    tertiary = VTVGreen,
    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = TextPrimary,
    onSecondary = TextPrimary,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceDarkHover,
    onSurfaceVariant = TextSecondary,
    border = FocusOutline
)

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun DemoTVComposeTheme(
    content: @Composable () -> Unit,
) {
    // Force dark theme for TV media application
    MaterialTheme(
        colorScheme = VTVDarkColorScheme,
        typography = Typography,
        content = content
    )
}