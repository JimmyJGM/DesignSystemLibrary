package io.jimmyjossue.designsystemlibrary.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystemlibrary.components.button.model.DSButtonIcon
import io.jimmyjossue.designsystemlibrary.components.button.model.DSButtonPrimaryColors
import io.jimmyjossue.designsystemlibrary.components.button.model.DSButtonSecondaryColors
import io.jimmyjossue.designsystemlibrary.components.button.model.getContent
import io.jimmyjossue.designsystemlibrary.theme.catalog.LocalColors
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaHigh
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.shape
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography
import io.jimmyjossue.designsystemlibrary.utils.DSHorizontal
import io.jimmyjossue.designsystemlibrary.utils.textdecorator.decoratedAnnotatedString

object DSButtonUtils {

    @Composable
    fun getPrimaryColors(
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
    fun getSecondaryColors(
        background: Color = Color.Transparent,
        content: Color = LocalColors.current.primary,
        border: Color = LocalColors.current.primary,
        backgroundDisabled: Color = LocalColors.current.surface,
        contentDisabled: Color = LocalColors.current.primaryDisabled,
        borderDisabled: Color = LocalColors.current.primaryDisabled,
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

    @Composable
    internal fun paddingNormal(
        withText: Boolean,
    ) = PaddingValues(
        vertical = dimension.medium - dimension.smalled,
        horizontal = when (withText) {
            true -> dimension.semiLarge
            false -> dimension.medium - dimension.smalled
        }
    )

    @Composable
        internal fun paddingSmall(
        withText: Boolean,
    ) = PaddingValues(
        vertical = dimension.small + dimension.smalled,
        horizontal = when (withText) {
            true -> dimension.medium
            false -> dimension.small + dimension.smalled
        }
    )

    @Composable
    fun getShape() = shape.smalled

    @Composable
    internal fun ButtonIcon(
        buttonIcon: DSButtonIcon,
        size: Dp,
        contentColor: Color,
    ) {
        Icon(
            modifier = Modifier.size(size = size),
            painter = painterResource(id = buttonIcon.icon),
            tint = contentColor.takeIf { buttonIcon.withTint } ?: Color.Unspecified,
            contentDescription = null,
        )
    }

    @Composable
    internal fun ButtonContent(
        text: String?,
        icon: DSButtonIcon?,
        isEnabled: Boolean,
        iconSize: Dp,
        textStyle: TextStyle,
        paddingValues: PaddingValues,
        colors: DSButtonPrimaryColors,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimension.medium),
            modifier = Modifier.padding(paddingValues = paddingValues),
        ) {
            if (icon != null && icon.position == DSHorizontal.LEFT) {
                ButtonIcon(
                    size = iconSize,
                    buttonIcon = icon,
                    contentColor = colors.getContent(isEnabled)
                )
            }
            if (!text.isNullOrBlank()) {
                Box (
                    modifier = Modifier.heightIn(iconSize),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        style = textStyle,
                        color = colors.getContent(isEnabled),
                        text = text.decoratedAnnotatedString(),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                }
            }
            if (icon != null && icon.position == DSHorizontal.RIGHT) {
                ButtonIcon(
                    size = iconSize,
                    buttonIcon = icon,
                    contentColor = colors.getContent(isEnabled)
                )
            }
        }
    }
}
