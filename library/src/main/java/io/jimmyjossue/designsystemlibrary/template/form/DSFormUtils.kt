package io.jimmyjossue.designsystemlibrary.template.form

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import io.jimmyjossue.designsystemlibrary.components.button.DSButtonPrimaryColors
import io.jimmyjossue.designsystemlibrary.components.input.DSInputColors
import io.jimmyjossue.designsystemlibrary.components.input.DSInputUtils
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormColors
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormConfig
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaLow
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension

object DSFormUtils {

    @Composable
    fun getColors(
        typography: Color = color.typography,
        primary: Color = color.primary,
        accent: Color = color.accent,
        background: Color = color.surface,
        surface: Color = color.background,
        surfaceDark: Color = color.surfaceDark,
        onPrimary: Color = color.onPrimary,
        primaryDisabled: Color = color.primaryDisabled,
    ) = DSFormColors(
        typography = typography,
        primary = primary,
        onPrimary = onPrimary,
        primaryDisabled = primaryDisabled,
        accent = accent,
        background = background,
        surfaceDark = surfaceDark,
        surface = surface,
    )

    @Composable
    fun getConfig(
        isStickyButtonSubmit: Boolean = false,
        paddingVertical: Dp = dimension.medium,
        paddingHorizontal: Dp = dimension.medium,
        separationSpace: Dp = dimension.semiLarge,
    ) = DSFormConfig(
        isStickyButtonSubmit = isStickyButtonSubmit,
        separationSpace = separationSpace,
        paddingVertical = paddingVertical,
        paddingHorizontal = paddingHorizontal,
    )

    @Composable
    fun DSFormColors.toButtonColors () = DSButtonPrimaryColors(
        background = this.primary,
        content = this.onPrimary,
        backgroundDisabled = this.primaryDisabled,
        contentDisabled = this.surfaceDark,
    )

    @Composable
    fun DSFormColors.toInputColors () = DSInputUtils.getInputColors(
        background = surface.alphaLow,
        typography = typography,
        primary = primary,
        accent = accent,
    )
}