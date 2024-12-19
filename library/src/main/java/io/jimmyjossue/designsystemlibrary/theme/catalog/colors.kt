package io.jimmyjossue.designsystemlibrary.theme.catalog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

data class DSColors(
    val onPrimary: Color = Color(0xFFFFFFFF),
    val primary: Color = Color(0xFF0086E8),
    val primarySurface: Color = Color(0xFFCAE8FF),
    val primaryDisabled: Color = Color(0xFFB0B4B8),
    val accent: Color = Color(0xFF585CC1),
    val accentSurface: Color = Color(0xFFC9CBFF),
    val background: Color = Color(0xFFFFFFFF),
    val surface: Color = Color(0xFFEFEFEF),
    val surfaceDark: Color = Color(0xFFCFD2D2),
    val typography: Color = Color(0xFF060708),
    val typographyDisabled: Color = Color(0xFF55585E),
    val success: Color = Color(0xFF28D570),
    val successSurface: Color = Color(0xFFABFCBF),
    val successContent: Color = Color(0xFF006F22),
    val warning: Color = Color(0xFFE9A827),
    val warningSurface: Color = Color(0xFFFFE8BB),
    val warningContent: Color = Color(0xFF9D6800),
    val error: Color = Color(0xFFE52A2A),
    val errorSurface: Color = Color(0xFFFFCECE),
    val errorContent: Color = Color(0xFF8A0000),
)

val LocalColors = staticCompositionLocalOf { DSColors() }

val color: DSColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current

val Color.alphaHigh get() = this.copy(alpha = 0.75f)
val Color.alphaMedium get() = this.copy(alpha = 0.50f)
val Color.alphaSemiLow get() = this.copy(alpha = 0.35f)
val Color.alphaLow get() = this.copy(alpha = 0.15f)
val Color.alphaLower get() = this.copy(alpha = 0.07f)

val Color.isLuminanceMore get() = this.luminance() > 0.5f
