package io.jimmyjossue.designsystem.components.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystem.theme.alphaHigh
import io.jimmyjossue.designsystem.theme.alphaMedium
import io.jimmyjossue.designsystem.theme.color
import io.jimmyjossue.designsystem.theme.dimension

object DSButtonUtils {

    @Composable
    fun getButtonPrimaryColors(
        background: Color = color.primary,
        content: Color = color.onPrimary,
        backgroundDisabled: Color = color.primaryDisabled,
        contentDisabled: Color = color.typographyDisabled
    ) = DSButtonPrimaryColors(
        background = background,
        content = content,
        backgroundDisabled = backgroundDisabled,
        contentDisabled = contentDisabled,
    )

    @Composable
    fun getButtonSecondaryColors(
        background: Color = Color.Transparent,
        content: Color = color.typography,
        border: Color = color.typography,
        backgroundDisabled: Color = color.primaryDisabled,
        contentDisabled: Color = color.typographyDisabled,
        borderDisabled: Color = color.typographyDisabled,
    ) = DSButtonSecondaryColors(
        background = background,
        content = content,
        border = border,
        backgroundDisabled = backgroundDisabled,
        contentDisabled = contentDisabled,
        borderDisabled = borderDisabled,
    )

    internal fun DSButtonPrimaryColors.toButtonColors() = ButtonColors(
        containerColor = this.background,
        contentColor = content,
        disabledContainerColor = backgroundDisabled,
        disabledContentColor = contentDisabled,
    )

    internal fun DSButtonSecondaryColors.toButtonColors() = ButtonColors(
        containerColor = this.background,
        contentColor = content,
        disabledContainerColor = backgroundDisabled.alphaMedium,
        disabledContentColor = contentDisabled.alphaHigh,
    )

    internal fun DSButtonSecondaryColors.border(isEnabled: Boolean) = when(isEnabled) {
        true -> border
        false -> borderDisabled
    }

    internal fun DSButtonPrimaryColors.content(isEnabled: Boolean) = when(isEnabled) {
        true -> content
        false -> contentDisabled
    }

    internal fun DSButtonSecondaryColors.content(isEnabled: Boolean) = when(isEnabled) {
        true -> content
        false -> contentDisabled
    }

    @Composable
    internal fun getElevation(elevation: Dp = 0.dp) = ButtonDefaults.buttonElevation(
        defaultElevation = elevation,
        pressedElevation = elevation,
        focusedElevation = elevation,
        hoveredElevation = elevation,
        disabledElevation = elevation,
    )

    @Composable
    internal fun getContentPadding() = PaddingValues(
        horizontal = dimension.semiLarge,
        vertical = dimension.medium - dimension.smalled,
    )

}
