package io.jimmyjossue.designsystemlibrary.components.button.model

import androidx.compose.ui.graphics.Color
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium

data class DSButtonPrimaryColors(
    val background: Color,
    val content: Color,
    val backgroundDisabled: Color,
    val contentDisabled: Color,
)

internal fun DSButtonPrimaryColors.getBackground(isEnabled: Boolean) = when (isEnabled) {
    true -> background
    false -> backgroundDisabled
}

internal fun DSButtonPrimaryColors.getContent(isEnabled: Boolean) = when (isEnabled) {
    true -> content
    false -> contentDisabled.alphaMedium
}
