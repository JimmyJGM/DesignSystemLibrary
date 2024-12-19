package io.jimmyjossue.designsystemlibrary.components.selectors.chips.config

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.radius

object DSChipsUtils {

    @Composable
    fun getColors(
        typography: Color = color.typography,
        background: Color = color.background,
        primary: Color = color.primary,
        onPrimary: Color = color.onPrimary,
        surface: Color = color.surface,
        onSurface: Color = color.typography,
        disabled: Color = color.primaryDisabled,
    ) = DSChipColors(
        typography = typography,
        background = background,
        primary = primary,
        onPrimary = onPrimary,
        surface = surface,
        onSurface = onSurface,
        disabled = disabled,
    )

    @Composable
    fun getConfig(
        maxSelected: Int = Int.MAX_VALUE,
        chipSeparation: Dp = dimension.small,
        contentSeparation: Dp = dimension.semiLarge,
        contentPadding: Dp = dimension.semiLarge,
        chipsRadiusShape: Dp = radius.medium,
    ) = DSChipsConfig(
        maxSelected = maxSelected,
        chipSeparation = chipSeparation,
        contentSeparation = contentSeparation,
        contentPadding = contentPadding,
        chipsRadiusShape = chipsRadiusShape,
    )
}
