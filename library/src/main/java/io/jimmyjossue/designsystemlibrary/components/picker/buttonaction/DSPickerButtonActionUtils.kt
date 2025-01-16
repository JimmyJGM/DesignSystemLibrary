package io.jimmyjossue.designsystemlibrary.components.picker.buttonaction

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.jimmyjossue.designsystemlibrary.theme.catalog.color

object DSPickerButtonActionUtils {

    @Composable
    fun getColors(
        content: Color = color.primary,
        contentDisabled: Color = color.primaryDisabled,
        background: Color = color.primarySurface,
        backgroundDisabled: Color = color.surface,
    ) = DSPickerButtonActionColors(
        content = content,
        contentDisabled = contentDisabled,
        background = background,
        backgroundDisabled = backgroundDisabled,
    )

}
