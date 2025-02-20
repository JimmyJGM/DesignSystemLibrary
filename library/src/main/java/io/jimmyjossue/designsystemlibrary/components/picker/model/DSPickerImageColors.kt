package io.jimmyjossue.designsystemlibrary.components.picker.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.Unspecified

data class DSPickerImageColors(
    val primary: Color,
    val onPrimary: Color,
    val primaryDisabled: Color,
    val container: Color,
    val typography: Color,
    val typographyDisabled: Color,
    val uploadedImageBorder: Color? = null,
) {
    fun isAvailableBorderColor(): Boolean {
        return uploadedImageBorder != null
                && uploadedImageBorder != Transparent
                && uploadedImageBorder != Unspecified
    }

    fun getPrimary(isEnabled: Boolean): Color {
        return if (isEnabled) primary else primaryDisabled
    }

    fun getContainer(isEnabled: Boolean): Color {
        return if (isEnabled) container else primaryDisabled
    }

    fun getTypography(isEnabled: Boolean): Color {
        return if (isEnabled) typography else typographyDisabled
    }
}
