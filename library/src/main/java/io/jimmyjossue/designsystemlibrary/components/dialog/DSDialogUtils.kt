package io.jimmyjossue.designsystemlibrary.components.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import io.jimmyjossue.designsystemlibrary.components.dialog.model.DSDialogColors
import io.jimmyjossue.designsystemlibrary.components.dialog.model.DSDialogConfig
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.shape

object DSDialogUtils {

    @Composable
    fun getConfig(
        dialogShape: Shape = shape.medium,
        imageFraction: Float = 1f,
        isEnableBackHandler: Boolean = false,
    ) = DSDialogConfig(
        dialogShape = dialogShape,
        imageFraction = imageFraction,
        isEnableBackHandler = isEnableBackHandler,
    )

    @Composable
    fun getColors(
        scrim: Color = color.scrim,
        background: Color = color.background,
        surface: Color = color.surface,
        typography: Color = color.typography,
        primary: Color = color.primary,
        onPrimary: Color = color.onPrimary,
        primaryDisabled: Color = color.primaryDisabled,
        accent: Color = color.accent,
    ) = DSDialogColors(
        scrim = scrim,
        background = background,
        surface = surface,
        typography = typography,
        primary = primary,
        onPrimary = onPrimary,
        primaryDisabled = primaryDisabled,
        accent = accent,
    )

}
