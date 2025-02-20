package io.jimmyjossue.designsystemlibrary.components.bottombar

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystemlibrary.R
import io.jimmyjossue.designsystemlibrary.components.bottombar.model.DSBottomBarColors
import io.jimmyjossue.designsystemlibrary.components.bottombar.model.DSBottomBarIcon
import io.jimmyjossue.designsystemlibrary.components.bottombar.model.getIcon
import io.jimmyjossue.designsystemlibrary.components.button.DSButtonPrimary
import io.jimmyjossue.designsystemlibrary.components.button.DSButtonUtils
import io.jimmyjossue.designsystemlibrary.components.mark.DSNotificationMark
import io.jimmyjossue.designsystemlibrary.components.separator.DSSpacer
import io.jimmyjossue.designsystemlibrary.components.separator.DSSpacerFilled
import io.jimmyjossue.designsystemlibrary.components.topBar.DSTopBar
import io.jimmyjossue.designsystemlibrary.components.topBar.DSTopBarContent
import io.jimmyjossue.designsystemlibrary.components.topBar.dsTopBarNavigationBack
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaLow
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaLower
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaSemiLow
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.radius
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography

private val iconRadius @Composable get () = radius.medium + radius.small
private val spaceCommon @Composable get () = dimension.small + dimension.smalled

@Composable
fun DSBottomBar(
    selectedItemId: String,
    items: List<DSBottomBarIcon>,
    colors: DSBottomBarColors = DSBottomBarUtils.getColors(),
    onClick: (String) -> Unit,
) {
    BottomAppBar(
        contentColor = colors.typography,
        containerColor = colors.background,
        tonalElevation = dimension.none,
        contentPadding = PaddingValues(horizontal = dimension.medium, vertical = dimension.smalled),
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = item.id == selectedItemId
            BottomBarItem(
                model = item,
                colors = colors,
                isSelected = isSelected,
                onClick = onClick,
            )
            if (index < items.lastIndex) {
                DSSpacerFilled()
            }
        }
    }
}

@Composable
private fun BottomBarItem(
    model: DSBottomBarIcon,
    isSelected: Boolean = false,
    colors: DSBottomBarColors,
    onClick: (String) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = dimension.small / 2),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(
                width = 1.dp,
                color = if (isSelected) colors.selectedSurface else Color.Transparent,
                shape = RoundedCornerShape(iconRadius),
            )
            .clip(
                shape = RoundedCornerShape(iconRadius)
            )
            .animateContentSize()
            .clickable(
                role = Role.Button,
                indication = ripple(
                    color = colors.selected.alphaLower,
                    bounded = false,
                ),
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    if (!isSelected) {
                        onClick(model.id)
                    }
                }
            )
            .padding(
                vertical = spaceCommon,
                horizontal = when (isSelected) {
                    true -> if (!model.text.isNullOrBlank()) dimension.medium else dimension.small
                    false ->  spaceCommon
                }
            )
    ) {
        Box(
            modifier = Modifier.graphicsLayer(
                compositingStrategy = CompositingStrategy.Offscreen
            )
        ) {
            Icon(
                contentDescription = null,
                painter = painterResource(id = model.getIcon(isSelected = isSelected)),
                tint = if (isSelected) colors.selected else colors.typography.alphaMedium,
                modifier = Modifier
                    .size(
                        size = dimension.semiLarge + dimension.smalled
                    )
            )
            if (model.withNotification) {
                DSNotificationMark(
                    borderWidth = dimension.smalled,
                    sizeMark = spaceCommon,
                    colorMark = colors.notification,
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }
        }
        if (!model.text.isNullOrBlank() && isSelected) {
            Text(
                text = model.text,
                maxLines = 1,
                style = typography.caption,
                overflow = TextOverflow.Ellipsis,
                color = colors.selected,
                modifier = Modifier
                    .widthIn(max = dimension.extraLarge)
                    .padding(end = dimension.smalled)
            )
        }
    }
}

@Preview
@Composable
fun PreviewBottomBar(
    title: String = "",
    buttonText: String = "",
    onClick: () -> Unit = {}
) {
    val items = listOf(
        DSBottomBarIcon(
            id = "homeScreen",
            icon = R.drawable.ds_ic_system_home_light,
            iconSelected = R.drawable.ds_ic_system_home_fill_light,
            text = "Inicio"
        ),
        DSBottomBarIcon(
            id = "searchScreen",
            icon = R.drawable.ds_ic_system_search_light,
            iconSelected = null,
            text = "Buscar"
        ),
        DSBottomBarIcon(
            id = "profileScreen",
            icon = R.drawable.ds_ic_system_person_light,
            iconSelected = R.drawable.ds_ic_system_person_fill_light,
            text = "Perfil"
        ),
        DSBottomBarIcon(
            withNotification = true,
            id = "notificationScreen",
            icon = R.drawable.ds_ic_system_notification_light,
            iconSelected = R.drawable.ds_ic_system_notification_fill_light,
            text = "Notificaciones"
        ),
    )

    val selectedItemState = remember { mutableStateOf(items.first().id) }

    @OptIn(ExperimentalMaterial3Api::class)
    Scaffold(
        containerColor = color.surface,
        topBar = {
            DSTopBar(
                navigation = dsTopBarNavigationBack {  },
                content = DSTopBarContent(
                    title = items.firstOrNull { it.id == selectedItemState.value }?.text.orEmpty()
                )
            )
        },
        bottomBar = {
            DSBottomBar(
                selectedItemId = selectedItemState.value,
                items = items,
                onClick = { selectedItemState.value = it },
            )
        }
    ) { pd ->
        Column(
            verticalArrangement = Arrangement.spacedBy(dimension.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(pd)
                .padding(dimension.large),
        ) {
            Text(
                text = title,
                style = typography.title
            )
            DSSpacer(size = dimension.small)

            DSButtonPrimary(
                colors = DSButtonUtils.getButtonPrimaryColors(),
                text = buttonText,
                onClick = onClick
            )
        }
    }
}
