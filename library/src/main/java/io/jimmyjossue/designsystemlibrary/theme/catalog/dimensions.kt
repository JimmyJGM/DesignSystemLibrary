package io.jimmyjossue.designsystemlibrary.theme.catalog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class DSDimensions(
    /** size: 0 dp */
    val none: Dp = 0.dp,
    /** size: 2 dp */
    val smalled: Dp = 2.dp,
    /** size: 8 dp */
    val small: Dp = 8.dp,
    /** size: 16 dp */
    val medium: Dp = 16.dp,
    /** size: 24 dp */
    val semiLarge: Dp = 24.dp,
    /** size: 48 dp */
    val large: Dp = 48.dp,
    /** size: 96 dp */
    val extraLarge: Dp = 96.dp,
)

val LocalDimensions = staticCompositionLocalOf { DSDimensions() }

val dimension
    @Composable
    @ReadOnlyComposable
    get() = LocalDimensions.current
