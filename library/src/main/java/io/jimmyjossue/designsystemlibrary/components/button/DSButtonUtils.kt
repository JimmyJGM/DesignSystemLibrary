package io.jimmyjossue.designsystemlibrary.components.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystemlibrary.theme.catalog.LocalColors
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaHigh
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension

object DSButtonUtils {

    @Composable
    fun getButtonPrimaryColors(
        background: Color = LocalColors.current.primary,
        content: Color = LocalColors.current.onPrimary,
        backgroundDisabled: Color = LocalColors.current.primaryDisabled,
        contentDisabled: Color = LocalColors.current.typographyDisabled
    ) = DSButtonPrimaryColors(
        background = background,
        content = content,
        backgroundDisabled = backgroundDisabled,
        contentDisabled = contentDisabled,
    )

    @Composable
    fun getButtonSecondaryColors(
        background: Color = Color.Transparent,
        content: Color = LocalColors.current.typography,
        border: Color = LocalColors.current.typography,
        backgroundDisabled: Color = LocalColors.current.primaryDisabled,
        contentDisabled: Color = LocalColors.current.typographyDisabled,
        borderDisabled: Color = LocalColors.current.typographyDisabled,
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
