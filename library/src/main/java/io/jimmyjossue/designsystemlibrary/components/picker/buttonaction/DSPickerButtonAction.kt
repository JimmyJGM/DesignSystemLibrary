package io.jimmyjossue.designsystemlibrary.components.picker.buttonaction

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.medium
import io.jimmyjossue.designsystemlibrary.theme.catalog.radius
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography
import io.jimmyjossue.designsystemlibrary.utils.extension.borderDashed

@Composable
internal fun DSPickerButtonAction(
    text: String? = null,
    isEnabled: Boolean = true,
    cornerRadius: Dp = radius.medium,
    @DrawableRes icon: Int? = null,
    colors: DSPickerButtonActionColors = DSPickerButtonActionUtils.getColors(),
    borderType: DSButtonBorderType = DSButtonBorderType.Line,
    contentPadding: PaddingValues = PaddingValues(all = dimension.small),
    onClick: () -> Unit,
) {
    fun getContentColor() = if (isEnabled) colors.content else colors.contentDisabled
    fun getBackgroundColor() = if (isEnabled) colors.background else colors.backgroundDisabled

    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(size = cornerRadius))
            .clickable(
                enabled = isEnabled,
                onClick = onClick
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = dimension.smalled * 2),
            modifier = Modifier
                .then(
                    other = when (borderType) {
                        DSButtonBorderType.Line -> Modifier.border(
                            width = 1.dp,
                            color = getContentColor(),
                            shape = RoundedCornerShape(size = cornerRadius)
                        )
                        DSButtonBorderType.Dotted -> Modifier.borderDashed(
                            width = 2.dp,
                            color = getContentColor(),
                            cornerRadius = cornerRadius
                        )
                        DSButtonBorderType.None -> Modifier
                    }
                )
                .clip(shape = RoundedCornerShape(size = cornerRadius))
                .background(
                    color = getBackgroundColor(),
                    shape = RoundedCornerShape(size = cornerRadius)
                )
                .padding(paddingValues = contentPadding),
        ) {
            icon?.let {
                Icon(
                    painter = painterResource(id = icon),
                    modifier = Modifier.size(20.dp),
                    tint = getContentColor(),
                    contentDescription = null
                )
            }
            if (!text.isNullOrBlank()) {
                Text(
                    text = text,
                    style = typography.caption.medium,
                    color = getContentColor()
                )
            }
        }
    }
}
