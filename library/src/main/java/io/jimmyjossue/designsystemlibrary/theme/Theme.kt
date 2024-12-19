package io.jimmyjossue.designsystemlibrary.theme

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import io.jimmyjossue.designsystemlibrary.theme.catalog.DSColors
import io.jimmyjossue.designsystemlibrary.theme.catalog.DSDimensions
import io.jimmyjossue.designsystemlibrary.theme.catalog.DSRadius
import io.jimmyjossue.designsystemlibrary.theme.catalog.DSShapes
import io.jimmyjossue.designsystemlibrary.theme.catalog.DSTypography
import io.jimmyjossue.designsystemlibrary.theme.catalog.LocalColors
import io.jimmyjossue.designsystemlibrary.theme.catalog.LocalDimensions
import io.jimmyjossue.designsystemlibrary.theme.catalog.LocalRadius
import io.jimmyjossue.designsystemlibrary.theme.catalog.LocalShapes
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.localTypography

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
        content = {
            SetStatusBarColor(background = Color.Transparent)
            content.invoke()
        },
    )
}

@Composable
private fun SetStatusBarColor(
    background: Color = color.background,
    isLightIcons: Boolean = background.luminance() > 0.5f
) {
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.setDecorFitsSystemWindows(window, false)
            window.statusBarColor = background.toArgb()
            window.navigationBarColor = background.toArgb()
            window.navigationBarDividerColor = background.toArgb()
            window.navigationBarDividerColor = background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isLightIcons
        }
    }
}

@Preview
@Composable
private fun PreviewScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(color = Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Red)
        )
    }
}