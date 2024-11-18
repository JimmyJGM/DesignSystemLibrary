package io.jimmyjossue.designsystemlibrary.template.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.jimmyjossue.designsystemlibrary.components.topBar.DSTopBarColors
import io.jimmyjossue.designsystemlibrary.template.screen.model.DSScreenColors
import io.jimmyjossue.designsystemlibrary.theme.catalog.color

object DSScreenUtils {

    @Composable
    fun getColors(
        primary: Color = color.primary,
        accent: Color = color.accent,
        background: Color = color.background,
        content: Color = color.typography,
        topBarBackground: Color = color.background,
        topBarBackgroundScrolled: Color = color.surface,
        topBarContentScrolled: Color = color.typography,
        topBarContent: Color = color.typography,
        topBarAccent: Color = color.accent,
        topBarBorderScrolled: Color = color.surfaceDark,
    ) = DSScreenColors(
        primary = primary,
        accent = accent,
        background = background,
        content = content,
        topBarBackground = topBarBackground,
        topBarContent = topBarContent,
        topBarAccent = topBarAccent,
        topBarBackgroundScrolled = topBarBackgroundScrolled,
        topBarContentScrolled = topBarContentScrolled,
        topBarBorderScrolled = topBarBorderScrolled,
    )

    internal fun DSScreenColors.toTopBarColors() = DSTopBarColors(
        container = topBarBackground,
        containerScrolled = topBarBackgroundScrolled,
        contentScrolled = topBarContentScrolled,
        borderScrolled = topBarBorderScrolled,
        content = topBarContent,
        accent = topBarAccent,
    )

}