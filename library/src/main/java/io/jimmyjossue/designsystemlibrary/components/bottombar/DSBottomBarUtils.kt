package io.jimmyjossue.designsystemlibrary.components.bottombar

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.jimmyjossue.designsystemlibrary.components.bottombar.model.DSBottomBarColors
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.radius

object DSBottomBarUtils {

    internal val iconRadius @Composable get() = radius.medium + radius.small
    internal val spaceCommon @Composable get() = dimension.small + dimension.smalled
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
        iconUnselected = iconUnselected,
        iconSelected = iconSelected,
        selectionMark = selectionMark,
        notification = notification,
        outline = outline,
    )

}