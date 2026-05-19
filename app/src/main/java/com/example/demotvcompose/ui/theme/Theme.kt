package com.example.demotvcompose.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.darkColorScheme

// 1. Define a data class for your custom colors
data class CustomColors(
    val warning: Color,
    val success: Color,
    val vip: Color,
    val premium: Color
)

// 2. Define a default instance of your custom colors
val defaultCustomColors = CustomColors(
    warning = WarningYellow,
    success = SuccessGreen,
    vip = VipGold,
    premium = PremiumPurple
)

// 3. Create a CompositionLocal to hold the custom colors
val LocalCustomColors = staticCompositionLocalOf { defaultCustomColors }

// 4. Create an extension property on MaterialTheme for easy access
val MaterialTheme.customColors: CustomColors
    @Composable
    @ReadOnlyComposable
    get() = LocalCustomColors.current

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
    // 5. Wrap your MaterialTheme with CompositionLocalProvider
    CompositionLocalProvider(
        LocalCustomColors provides defaultCustomColors
    ) {
        // Force dark theme for TV media application
        MaterialTheme(
            colorScheme = VTVDarkColorScheme,
            typography = Typography,
            content = content
        )
    }
}