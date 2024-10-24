package io.jimmyjossue.designsystem.components.topBar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import io.jimmyjossue.designsystem.theme.dimension

data class DSTopBarNavigation(
    @DrawableRes val icon: Int,
    val onClick: () -> Unit,
)

@Composable
internal fun TopBarNavigation(
    model: DSTopBarNavigation?,
    contentColor: Color
) {
    if (model != null) {
        IconButton(
            modifier = Modifier.size(size = dimension.semiLarge),
            onClick = model.onClick,
            content = {
                Icon(
                    painter = painterResource(id = model.icon),
                    tint = contentColor,
                    contentDescription = null
                )
            }
        )
    }
}
