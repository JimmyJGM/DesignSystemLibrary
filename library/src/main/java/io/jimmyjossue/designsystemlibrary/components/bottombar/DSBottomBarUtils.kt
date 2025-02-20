package io.jimmyjossue.designsystemlibrary.components.bottombar

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.jimmyjossue.designsystemlibrary.components.bottombar.model.DSBottomBarColors
import io.jimmyjossue.designsystemlibrary.theme.catalog.color

object DSBottomBarUtils {

    @Composable
    fun getColors(
        background: Color = color.background,
        surface: Color = color.surface,
        typography: Color = color.typography,
        selected: Color = color.primary,
        selectedSurface: Color = color.primarySurface,
        notification: Color = color.accent,
        outline: Color = color.surface,
    ) = DSBottomBarColors(
        background = background,
        surface = surface,
        typography = typography,
        selected = selected,
        selectedSurface = selectedSurface,
        notification = notification,
        outline = outline,
    )

}