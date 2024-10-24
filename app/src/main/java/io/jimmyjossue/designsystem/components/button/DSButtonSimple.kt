package io.jimmyjossue.designsystem.components.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystem.R
import io.jimmyjossue.designsystem.components.button.DSButtonUtils.border
import io.jimmyjossue.designsystem.components.button.DSButtonUtils.content
import io.jimmyjossue.designsystem.components.button.DSButtonUtils.toButtonColors
import io.jimmyjossue.designsystem.components.separator.DSSpacer
import io.jimmyjossue.designsystem.theme.AppTheme
import io.jimmyjossue.designsystem.theme.DSColors
import io.jimmyjossue.designsystem.theme.dimension
import io.jimmyjossue.designsystem.theme.shape
import io.jimmyjossue.designsystem.theme.typography
import io.jimmyjossue.designsystem.utils.DSHorizontal
import io.jimmyjossue.designsystem.utils.parseDecoratedAnnotatedString

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
    colors: DSButtonSecondaryColors = DSButtonUtils.getButtonSecondaryColors(),
    contentPadding: PaddingValues = DSButtonUtils.getContentPadding(),
    onClick: () -> Unit
) {
    val buttonPadding = contentPadding.calculateForButton(withText = !text.isNullOrBlank())
    if (!text.isNullOrBlank() || icon != null) {
        Button(
            onClick = onClick,
            modifier = modifier,
            enabled = isEnabled,
            shape = shape.small,
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
    val layoutDirection = LocalLayoutDirection.current
    val minusValue = if (withText) 0.dp else dimension.small
    fun Dp.cal() = minus(minusValue).coerceAtLeast(0.dp)

    return PaddingValues(
        start = this.calculateStartPadding(layoutDirection = layoutDirection).cal(),
        top = this.calculateTopPadding().cal(),
        end = this.calculateEndPadding(layoutDirection = layoutDirection).cal(),
        bottom = this.calculateBottomPadding().cal(),
    )
}

@Composable
private fun PaddingValues.withVertical(): Boolean {
    val layoutDirection = LocalLayoutDirection.current
    return calculateTopPadding() > dimension.small
            || calculateStartPadding(layoutDirection) > dimension.small
            || calculateEndPadding(layoutDirection) > dimension.small
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
            style = typography.title,
            text = text.parseDecoratedAnnotatedString(),
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

@Preview(showBackground = true)
@Composable
private fun Preview() {
    val text = "Click <sb>me</sb>!!!"
    val onClick = { }
    val isEnabledState = remember { mutableStateOf(true) }

    AppTheme() {
        Column(
            modifier = Modifier.padding(dimension.small),
            verticalArrangement = Arrangement.spacedBy(space = dimension.small)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Enabled")
                Checkbox(
                    checked = isEnabledState.value,
                    onCheckedChange = { isEnabledState.value = !isEnabledState.value }
                )
            }
            DSButtonPrimary(
                contentPadding = PaddingValues(0.dp),
                icon = DSButtonIcon(R.drawable.ic_navigation_back),
                isEnabled = isEnabledState.value,
                onClick = onClick
            )
            DSButtonPrimary(
                text = text,
                icon = DSButtonIcon(R.drawable.ic_navigation_back, position = DSHorizontal.RIGHT),
                isEnabled = isEnabledState.value,
                onClick = onClick
            )
            DSButtonPrimary(
                text = text,
                isEnabled = isEnabledState.value,
                icon = DSButtonIcon(R.drawable.ic_navigation_back),
                onClick = onClick
            )
            DSButtonSecondary(
                text = text,
                isEnabled = isEnabledState.value,
                onClick = onClick,
            )
        }
    }
}
