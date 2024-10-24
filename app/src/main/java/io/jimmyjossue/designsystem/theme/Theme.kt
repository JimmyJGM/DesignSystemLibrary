package io.jimmyjossue.designsystem.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import io.jimmyjossue.designsystem.theme.color

@Composable
fun AppTheme(
    colors: DSColors = DSColors(),
    shapes: DSShapes = DSShapes(),
    radius: DSRadius = DSRadius(),
    dimensions: DSDimensions = DSDimensions(),
    typographies: DSTypography = DSTypography(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides colors,
        LocalShapes provides shapes,
        LocalRadius provides radius,
        LocalDimensions provides dimensions,
        localTypography provides typographies,
        content = content,
    )
}

@Composable
fun SetStatusBarColor(
    background: Color = color.background,
    isLightIcons: Boolean = isSystemInDarkTheme()
) {
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isLightIcons
        }
    }
}