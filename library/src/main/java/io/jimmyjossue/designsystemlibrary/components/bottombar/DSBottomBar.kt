package io.jimmyjossue.designsystemlibrary.components.bottombar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import io.jimmyjossue.designsystemlibrary.R
import io.jimmyjossue.designsystemlibrary.components.bottombar.model.DSBottomBarColors
import io.jimmyjossue.designsystemlibrary.components.bottombar.model.DSBottomBarConfig
import io.jimmyjossue.designsystemlibrary.components.bottombar.model.DSBottomBarIcon
import io.jimmyjossue.designsystemlibrary.components.bottombar.model.DSBottomBarType
import io.jimmyjossue.designsystemlibrary.components.bottombar.widget.BottomItemExpandable
import io.jimmyjossue.designsystemlibrary.components.bottombar.widget.BottomTopMark
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension

@Composable
fun DSBottomBar(
    selectedItemId: String,
    items: List<DSBottomBarIcon>,
    type: DSBottomBarType = DSBottomBarType.ItemExpandable,
    config: DSBottomBarConfig = DSBottomBarUtils.getConfig(),
    colors: DSBottomBarColors = DSBottomBarUtils.getColors(),
    onClick: (String) -> Unit,
) {
    Surface(
        shape = config.shape,
        tonalElevation = config.elevation,
        contentColor = colors.itemUnselected,
        color = colors.background,
        modifier = Modifier.padding(paddingValues = config.margin),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = DSBottomBarUtils.getBarMinHeight())
                .padding(
                    horizontal = dimension.medium,
                    vertical = dimension.small + dimension.smalled
                )
        ) {
            when (type) {
                DSBottomBarType.BottomMark -> {
                    BottomTopMark(
                        selectedItemId = selectedItemId,
                        items = items,
                        colors = colors,
                        config = config,
                        onClick = onClick,
                    )
                }

                DSBottomBarType.ItemExpandable -> {
                    BottomItemExpandable(
                        selectedItemId = selectedItemId,
                        items = items,
                        colors = colors,
                        onClick = onClick,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewBottomBar() {
    val items = listOf(
        DSBottomBarIcon(
            id = "123456",
            text = "Inicio",
            icon = R.drawable.ds_ic_system_home_light
        ),
        DSBottomBarIcon(
            id = "987697",
            text = "Buscar",
            icon = R.drawable.ds_ic_system_search_light
        ),
        DSBottomBarIcon(
            id = "432130",
            text = "Perfil",
            icon = R.drawable.ds_ic_system_person_light
        ),
        DSBottomBarIcon(
            id = "987658",
            text = "Notificatciones",
            icon = R.drawable.ds_ic_system_notification_light,
            withNotification = true
        ),
    )
    DSBottomBar(
        items = items,
        onClick = { },
        selectedItemId = items.first().id,
        type = DSBottomBarType.ItemExpandable,
        config = DSBottomBarUtils.getConfig(),
        colors = DSBottomBarUtils.getColors(
            background = Color.White,
            iconSelected = Color.Black,
            iconUnselected = Color.Black,
            notification = Color.Magenta
        ),
    )
}
