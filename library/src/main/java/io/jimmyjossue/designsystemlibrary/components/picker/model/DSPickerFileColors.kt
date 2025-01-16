package io.jimmyjossue.designsystemlibrary.components.picker.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

data class DSPickerFileColors(
    val primary: Color,
    val primaryDisabled: Color,
    val onPrimary: Color,
    val background: Color,
    val typography: Color,
    val surface: Color,
) {
    fun isBackgroundLight() = background.luminance() > 0.5f
    fun isTypographyLight() = background.luminance() > 0.5f
}
