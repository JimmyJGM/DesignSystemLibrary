package io.jimmyjossue.designsystemlibrary.components.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystemlibrary.components.button.DSButtonUtils.border
import io.jimmyjossue.designsystemlibrary.components.button.DSButtonUtils.content
import io.jimmyjossue.designsystemlibrary.components.button.DSButtonUtils.toButtonColors
import io.jimmyjossue.designsystemlibrary.components.separator.DSSpacer
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.shape
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography
import io.jimmyjossue.designsystemlibrary.utils.DSHorizontal
import io.jimmyjossue.designsystemlibrary.utils.textdecorator.decoratedAnnotatedString

data class DSButtonIcon(
    @DrawableRes val icon: Int,
    val withTint: Boolean = true,
    val position: DSHorizontal = DSHorizontal.LEFT,
)

data class DSButtonPrimaryColors(
    val background: Color,
    val content: Color,
    val backgroundDisabled: Color,
    val contentDisabled: Color,
)

data class DSButtonSecondaryColors(
    val background: Color,
    val content: Color,
    val border: Color,
    val backgroundDisabled: Color,
    val contentDisabled: Color,
    val borderDisabled: Color,
)

/** Syntax to decorate text:
 *
 *  <b> = bold,
 *  <i> = italic,
 *  <t> = strikethrough,
 *  <sb> = semi bold,
 *  <primary> = color primary,
 *  <accent> = color accent,
 *  <typography> = color typography,
 *  <success> = color success,
 *  <warning> = color warning,
 *  <error> = color error,
 *
 *  Example: "<t>Example</t> text <b><i>button</i></b>"
 */
@Composable
fun DSButtonPrimary(
    modifier: Modifier = Modifier,
    text: String? = null,
    isEnabled: Boolean = true,
    icon: DSButtonIcon? = null,
    shapeButton: Shape = shape.small,
    colors: DSButtonPrimaryColors = DSButtonUtils.getButtonPrimaryColors(),
    contentPadding: PaddingValues = DSButtonUtils.getContentPadding(),
    onClick: () -> Unit
) {
    val buttonPadding = contentPadding.calculateForButton(withText = !text.isNullOrBlank())

    if (!text.isNullOrBlank() || icon != null) {
        Button(
            onClick = onClick,
            modifier = modifier,
            enabled = isEnabled,
            shape = shapeButton,
            colors = colors.toButtonColors(),
            elevation = DSButtonUtils.getElevation(),
            contentPadding = contentPadding,
        ) {
            ButtonContent(
                text = text,
                icon = icon,
                isWithPadding = buttonPadding.withVertical(),
                contentColor = colors.content(
                    isEnabled = isEnabled
                )
            )
        }
    }
}

@Composable
fun DSButtonSecondary(
    modifier: Modifier = Modifier,
    text: String? = null,
    isEnabled: Boolean = true,
    icon: DSButtonIcon? = null,
    shapeButton: Shape = shape.small,
    colors: DSButtonSecondaryColors = DSButtonUtils.getButtonSecondaryColors(),
    contentPadding: PaddingValues = DSButtonUtils.getContentPadding(),
    onClick: () -> Unit,
) {
    val buttonPadding = contentPadding.calculateForButton(withText = !text.isNullOrBlank())
    if (!text.isNullOrBlank() || icon != null) {
        Button(
            onClick = onClick,
            modifier = modifier,
            enabled = isEnabled,
            shape = shapeButton,
            colors = colors.toButtonColors(),
            elevation = DSButtonUtils.getElevation(),
            contentPadding = buttonPadding,
            border = BorderStroke(width = 1.dp, color = colors.border(isEnabled)),
        ) {
            ButtonContent(
                text = text,
                icon = icon,
                isWithPadding = buttonPadding.withVertical(),
                contentColor = colors.content(
                    isEnabled = isEnabled
                )
            )
        }
    }
}

@Composable
private fun PaddingValues.calculateForButton(withText: Boolean): PaddingValues {
    val minusValue = if (withText) 0.dp else dimension.small
    fun Dp.cal() = minus(minusValue).coerceAtLeast(0.dp)

    return PaddingValues(
        start = this.calculateStartPadding(layoutDirection = LayoutDirection.Ltr).cal(),
        top = this.calculateTopPadding().cal(),
        end = this.calculateEndPadding(layoutDirection = LayoutDirection.Ltr).cal(),
        bottom = this.calculateBottomPadding().cal(),
    )
}

@Composable
private fun PaddingValues.withVertical(): Boolean {
    return calculateTopPadding() > dimension.small
            || calculateStartPadding(LayoutDirection.Ltr) > dimension.small
            || calculateEndPadding(LayoutDirection.Ltr) > dimension.small
            || calculateBottomPadding() > dimension.small
}

@Composable
private fun ButtonContent(
    text: String?,
    contentColor: Color,
    icon: DSButtonIcon?,
    isWithPadding: Boolean,
) {
    ButtonIconLeft(
        buttonIcon = icon,
        contentColor = contentColor,
        isVisibleSpace = !text.isNullOrBlank(),
        isWithPadding = isWithPadding
    )

    if (!text.isNullOrBlank()) {
        Text(
            style = typography.button,
            text = text.decoratedAnnotatedString(),
        )
    }

    ButtonIconRight(
        buttonIcon = icon,
        contentColor = contentColor,
        isVisibleSpace = !text.isNullOrBlank(),
        isWithPadding = isWithPadding
    )
}

@Composable
private fun ButtonIcon(buttonIcon: DSButtonIcon, contentColor: Color, withPadding: Boolean) {
    Icon(
        modifier = Modifier
            .padding(vertical = if (withPadding) dimension.medium.minus(dimension.smalled) else 0.dp)
            .size(size = dimension.semiLarge),
        painter = painterResource(id = buttonIcon.icon),
        tint = contentColor.takeIf { buttonIcon.withTint } ?: Color.Unspecified,
        contentDescription = null,
    )
}

@Composable
private fun ButtonIconRight(
    buttonIcon: DSButtonIcon?,
    contentColor: Color,
    isVisibleSpace: Boolean,
    isWithPadding: Boolean,
    space: Dp = dimension.small + dimension.smalled,
) {
    if (buttonIcon?.position == DSHorizontal.RIGHT) {
        if (isVisibleSpace) {
            DSSpacer(size = space)
        }
        ButtonIcon(
            buttonIcon = buttonIcon,
            contentColor = contentColor,
            withPadding = !isVisibleSpace && !isWithPadding,
        )
    }
}

@Composable
private fun ButtonIconLeft(
    buttonIcon: DSButtonIcon?,
    contentColor: Color,
    isVisibleSpace: Boolean,
    isWithPadding: Boolean,
    space: Dp = dimension.small + dimension.smalled,
) {
    if ( buttonIcon?.position == DSHorizontal.LEFT) {
        ButtonIcon(
            buttonIcon = buttonIcon,
            contentColor = contentColor,
            withPadding = !isVisibleSpace && !isWithPadding,
        )
        if (isVisibleSpace) {
            DSSpacer(size = space)
        }
    }
}
