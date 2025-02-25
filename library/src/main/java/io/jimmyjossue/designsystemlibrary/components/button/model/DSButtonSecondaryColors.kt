package io.jimmyjossue.designsystemlibrary.components.button.model

import androidx.compose.ui.graphics.Color
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaLow
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium

data class DSButtonSecondaryColors(
    val background: Color,
    val content: Color,
    val border: Color,
    val backgroundDisabled: Color,
    val contentDisabled: Color,
    val borderDisabled: Color,
)


internal fun DSButtonSecondaryColors.getBackground(isEnabled: Boolean) = when (isEnabled) {
    true -> background
    false -> backgroundDisabled
}

internal fun DSButtonSecondaryColors.getBorder(isEnabled: Boolean) = when (isEnabled) {
    true -> border
    false -> borderDisabled
}

internal fun DSButtonSecondaryColors.toPrimaryColors() = DSButtonPrimaryColors(
    background = background,
    content = content,
    backgroundDisabled = backgroundDisabled,
    contentDisabled = contentDisabled,
)