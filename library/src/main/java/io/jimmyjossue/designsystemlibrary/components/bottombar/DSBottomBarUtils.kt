package io.jimmyjossue.designsystemlibrary.components.bottombar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystemlibrary.components.bottombar.model.DSBottomBarColors
import io.jimmyjossue.designsystemlibrary.components.bottombar.model.DSBottomBarConfig
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.radius

object DSBottomBarUtils {

    internal val iconRadius @Composable get() = radius.medium + radius.small
    internal val iconSize @Composable get() = dimension.semiLarge + dimension.smalled
    internal val itemPadding @Composable get() = dimension.small
    internal val itemMarkSize @Composable get() = dimension.small + dimension.smalled

    @Composable
    fun getColors(
        background: Color = color.background,
        iconUnselected: Color = color.typography,
        iconSelected: Color = color.primary,
        selectionMark: Color = color.primary,
        notification: Color = color.accent,
        outline: Color = color.surface,
    ) = DSBottomBarColors(
        background = background,
        itemUnselected = iconUnselected,
        itemSelected = iconSelected,
        selectionMark = selectionMark,
        notification = notification,
        outline = outline,
    )

    @Composable
    fun getConfig(
        shape: Shape = RectangleShape,
        elevation: Dp = dimension.none,
        margin: PaddingValues = PaddingValues(),
    ) = DSBottomBarConfig(
        shape = shape,
        elevation = elevation,
        margin = margin,
    )

    @Composable
    internal fun getBarMinHeight() = 76.dp

}