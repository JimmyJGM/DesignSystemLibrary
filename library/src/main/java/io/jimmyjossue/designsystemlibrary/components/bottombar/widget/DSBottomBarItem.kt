package io.jimmyjossue.designsystemlibrary.components.bottombar.widget

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystemlibrary.components.bottombar.DSBottomBarUtils
import io.jimmyjossue.designsystemlibrary.components.bottombar.model.DSBottomBarColors
import io.jimmyjossue.designsystemlibrary.components.bottombar.model.DSBottomBarIcon
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaLower
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography

@Composable
internal fun BottomBarItemExpandable(
    withBorder: Boolean = false,
    model: DSBottomBarIcon,
    isSelected: Boolean = false,
    colors: DSBottomBarColors,
    onClick: (String) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = dimension.small / 2),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .then(
                other = when (withBorder) {
                    true -> Modifier.border(
                        width = 1.dp,
                        color = if (isSelected) colors.selectionMark else Color.Transparent,
                        shape = RoundedCornerShape(DSBottomBarUtils.iconRadius),
                    )
                    false -> Modifier
                }
            )
            .clip(
                shape = RoundedCornerShape(DSBottomBarUtils.iconRadius)
            )
            .animateContentSize()
            .clickable(
                role = Role.Tab,
                indication = ripple(
                    color = colors.iconSelected.alphaLower,
                    bounded = false,
                ),
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    if (!isSelected) {
                        onClick(model.id)
                    }
                }
            )
            .padding(all = DSBottomBarUtils.itemPadding)
    ) {
        BottomBarItemIcon(
            model = model,
            isSelected = isSelected,
            colors = colors,
            sizeIcon = DSBottomBarUtils.iconSize,
            sizeMark = DSBottomBarUtils.itemMarkSize,
        )

        if (!model.text.isNullOrBlank() && isSelected) {
            Box(
                modifier = Modifier.heightIn(min = DSBottomBarUtils.iconSize),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = model.text,
                    maxLines = 1,
                    style = typography.caption,
                    overflow = TextOverflow.Ellipsis,
                    color = colors.iconSelected,
                    modifier = Modifier
                        .widthIn(max = dimension.extraLarge)
                        .padding(end = dimension.small)
                )
            }
        }
    }
}
