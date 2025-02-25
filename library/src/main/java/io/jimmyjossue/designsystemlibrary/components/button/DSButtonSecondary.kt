package io.jimmyjossue.designsystemlibrary.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystemlibrary.components.button.model.DSButtonIcon
import io.jimmyjossue.designsystemlibrary.components.button.model.DSButtonSecondaryColors
import io.jimmyjossue.designsystemlibrary.components.button.model.getBackground
import io.jimmyjossue.designsystemlibrary.components.button.model.getBorder
import io.jimmyjossue.designsystemlibrary.components.button.model.toPrimaryColors
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.medium
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography
import io.jimmyjossue.designsystemlibrary.utils.extension.dsClick

@Composable
private fun DSButton(
    modifier: Modifier,
    text: String?,
    icon: DSButtonIcon?,
    isEnabled: Boolean,
    elevation: Dp,
    shape: Shape,
    colors: DSButtonSecondaryColors,
    padding: PaddingValues,
    iconSize: Dp,
    textStyle: TextStyle,
    onClick: () -> Unit,
) {
    Surface(
        shape = shape,
        color = colors.getBackground(isEnabled),
        border = BorderStroke(1.dp, colors.getBorder(isEnabled)),
        contentColor = Color.Unspecified,
        tonalElevation = elevation,
        modifier = modifier.dsClick(
            role = Role.Button,
            isEnabled = isEnabled,
            onClick = onClick,
        ),
    ) {
        Box(contentAlignment = Alignment.Center) {
            DSButtonUtils.ButtonContent(
                paddingValues = padding,
                isEnabled = isEnabled,
                textStyle = textStyle,
                iconSize = iconSize,
                colors = colors.toPrimaryColors(),
                text = text,
                icon = icon,
            )
        }
    }
}

@Composable
fun DSButtonSecondary(
    modifier: Modifier = Modifier,
    text: String? = null,
    icon: DSButtonIcon? = null,
    isEnabled: Boolean = true,
    elevation: Dp = dimension.none,
    shape: Shape = DSButtonUtils.getShape(),
    colors: DSButtonSecondaryColors = DSButtonUtils.getSecondaryColors(),
    onClick: () -> Unit,
) {
    DSButton(
        modifier = modifier,
        text = text,
        icon = icon,
        shape = shape,
        colors = colors,
        onClick = onClick,
        elevation = elevation,
        isEnabled = isEnabled,
        iconSize = dimension.semiLarge,
        textStyle = typography.button,
        padding = DSButtonUtils.paddingNormal(withText = !text.isNullOrBlank()),
    )
}

@Composable
fun DSButtonSecondarySmall(
    modifier: Modifier = Modifier,
    text: String? = null,
    icon: DSButtonIcon? = null,
    isEnabled: Boolean = true,
    elevation: Dp = dimension.none,
    shape: Shape = DSButtonUtils.getShape(),
    colors: DSButtonSecondaryColors = DSButtonUtils.getSecondaryColors(),
    onClick: () -> Unit,
) {
    DSButton(
        modifier = modifier,
        text = text,
        icon = icon,
        shape = shape,
        colors = colors,
        onClick = onClick,
        elevation = elevation,
        isEnabled = isEnabled,
        iconSize = dimension.medium,
        textStyle = typography.body.medium,
        padding = DSButtonUtils.paddingSmall(withText = !text.isNullOrBlank()),
    )
}
