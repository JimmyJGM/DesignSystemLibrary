package io.jimmyjossue.designsystemlibrary.components.bottombar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import io.jimmyjossue.designsystemlibrary.components.bottombar.model.DSBottomBarColors
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
    colors: DSBottomBarColors = DSBottomBarUtils.getColors(),
    onClick: (String) -> Unit,
) {
    BottomAppBar(
        contentColor = colors.iconUnselected,
        containerColor = colors.background,
        tonalElevation = dimension.none,
        contentPadding = PaddingValues(
            horizontal = dimension.medium,
            vertical = dimension.smalled
        ),
    ) {
        when (type) {
            DSBottomBarType.BottomMark -> {
                BottomTopMark(
                    selectedItemId = selectedItemId,
                    items = items,
                    colors = colors,
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
