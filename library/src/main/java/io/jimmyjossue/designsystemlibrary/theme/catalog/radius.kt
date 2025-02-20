package io.jimmyjossue.designsystemlibrary.theme.catalog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class DSRadius(
    val smalled: Dp = 2.dp,
    val small: Dp = 4.dp,
    val medium: Dp = 8.dp,
    val large: Dp = 16.dp,
    val extraLarge: Dp = 24.dp
)

val LocalRadius = staticCompositionLocalOf { DSRadius() }

val radius
    @Composable
    @ReadOnlyComposable
    get() = LocalRadius.current
