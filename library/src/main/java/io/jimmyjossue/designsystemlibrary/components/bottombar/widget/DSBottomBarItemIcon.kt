package io.jimmyjossue.designsystemlibrary.components.bottombar.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import io.jimmyjossue.designsystemlibrary.R
import io.jimmyjossue.designsystemlibrary.components.bottombar.DSBottomBarUtils
import io.jimmyjossue.designsystemlibrary.components.bottombar.model.DSBottomBarColors
import io.jimmyjossue.designsystemlibrary.components.bottombar.model.DSBottomBarIcon
import io.jimmyjossue.designsystemlibrary.components.bottombar.model.getIcon
import io.jimmyjossue.designsystemlibrary.components.mark.DSNotificationMark
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension

@Composable
internal fun BottomBarItemIcon(
    sizeIcon: Dp,
    sizeMark: Dp,
    isSelected: Boolean,
    model: DSBottomBarIcon,
    colors: DSBottomBarColors,
) {
    Box(
        modifier = Modifier.graphicsLayer(
            compositingStrategy = CompositingStrategy.Offscreen
        )
    ) {
        Icon(
            contentDescription = null,
            painter = painterResource(id = model.getIcon(isSelected = isSelected)),
            tint = if (isSelected) colors.iconSelected else colors.iconUnselected.alphaMedium,
            modifier = Modifier
                .size(size = sizeIcon)
        )
        if (model.withNotification) {
            DSNotificationMark(
                borderWidth = dimension.smalled,
                sizeMark = sizeMark,
                colorMark = colors.notification,
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewBottomBarItem() {
        val colors = DSBottomBarUtils.getColors()
        val model = DSBottomBarIcon(
            id = "2345678",
            text = "null",
            icon = R.drawable.ds_ic_system_notification_light,
            iconSelected = R.drawable.ds_ic_system_notification_light,
            withNotification = false
        )

    Column {
        Row {
            BottomBarItemExpandable(
                isSelected = false,
                withBorder = true,
                model = model,
                colors = colors,
                onClick = { }
            )
            BottomBarItemExpandable(
                isSelected = true,
                withBorder = true,
                model = model,
                colors = colors,
                onClick = { }
            )
        }
        Row {
            BottomBarItemExpandable(
                isSelected = true,
                withBorder = true,
                model = model,
                colors = colors,
                onClick = { }
            )
            BottomBarItemExpandable(
                isSelected = false,
                withBorder = true,
                model = model,
                colors = colors,
                onClick = { }
            )
        }
    }

}