package io.jimmyjossue.designsystemlibrary.theme.catalog

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

data class DSShapes(
    val smalled: CornerBasedShape = RoundedCornerShape(2.dp),
    val small: CornerBasedShape = RoundedCornerShape(8.dp),
    val medium: CornerBasedShape = RoundedCornerShape(16.dp),
    val semiLarge: CornerBasedShape = RoundedCornerShape(24.dp),
    val large: CornerBasedShape = RoundedCornerShape(48.dp),
    val extraLarge: CornerBasedShape = RoundedCornerShape(96.dp),
)

val LocalShapes = staticCompositionLocalOf { DSShapes() }

val shape: DSShapes
    @Composable
    @ReadOnlyComposable
    get() = LocalShapes.current
