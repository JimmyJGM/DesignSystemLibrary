package io.jimmyjossue.designsystemlibrary.components.topBar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension

private const val MAX_ACTIONS = 3

data class DSTopBarAction(
    @DrawableRes val icon: Int,
    val hasNotification: Boolean = false,
    val onClick: (() -> Unit)? = null,
)

@Composable
private fun TopBarAction(
    model: DSTopBarAction,
    iconColor: Color,
    contentColor: Color,
    accentColor: Color,
) {
    Box(
        modifier = Modifier, contentAlignment = Alignment.TopEnd
    ) {
        IconButton(
            modifier = Modifier.size(size = dimension.semiLarge),
            enabled = model.onClick != null,
            onClick = { model.onClick?.invoke() },
            content = {
                Icon(
                    painter = painterResource(id = model.icon),
                    tint = contentColor,
                    contentDescription = null
                )
            }
        )
        if (model.hasNotification) {
            Box(
                modifier = Modifier
                    .size(size = 12.dp)
                    .background(accentColor, CircleShape)
                    .border(1.75.dp, iconColor, CircleShape)
            )
        }
    }
}

@Composable
internal fun TopBarActions(
    models: List<DSTopBarAction>,
    withContentTint: Boolean,
    contentColor: Color,
    accentColor: Color,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimension.small)
    ) {
        models.take(MAX_ACTIONS).forEach {
            TopBarAction(
                model = it,
                accentColor = accentColor,
                iconColor = contentColor,
                contentColor = when (withContentTint) {
                    true -> contentColor
                    false -> Color.Unspecified
                }
            )
        }
    }
}
