package io.jimmyjossue.designsystemlibrary.components.bottombar.widget

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystemlibrary.components.bottombar.DSBottomBarUtils
import io.jimmyjossue.designsystemlibrary.components.bottombar.model.DSBottomBarColors
import io.jimmyjossue.designsystemlibrary.components.bottombar.model.DSBottomBarIcon
import io.jimmyjossue.designsystemlibrary.components.separator.DSSpacerFilled
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension

@Composable
internal fun RowScope.BottomItemExpandable(
    selectedItemId: String,
    items: List<DSBottomBarIcon>,
    colors: DSBottomBarColors = DSBottomBarUtils.getColors(),
    onClick: (String) -> Unit,
) {
    items.forEachIndexed { index, item ->
        val selected = item.id == selectedItemId
        BottomBarItemExpandable(
            model = item,
            colors = colors,
            withBorder = true,
            isSelected = selected,
            onClick = onClick,
        )
        if (index < items.lastIndex) {
            DSSpacerFilled()
        }
    }
}

@Composable
internal fun BottomTopMark(
    selectedItemId: String,
    items: List<DSBottomBarIcon>,
    colors: DSBottomBarColors = DSBottomBarUtils.getColors(),
    onClick: (String) -> Unit,
) {
    val density = LocalDensity.current
    val width = remember { mutableStateOf(IntSize.Zero) }
    val position = remember { mutableStateOf(Offset.Zero) }
    val positionX by animateFloatAsState(
        targetValue = position.value.x,
        animationSpec = tween(durationMillis = 150, easing = LinearEasing),
        label = "animationSelected"
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(-dimension.smalled)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEachIndexed { index, item ->
                val selected = item.id == selectedItemId
                Box(
                    modifier = Modifier
                        .onGloballyPositioned {
                            if (selected) {
                                position.value = it.positionInRoot()
                            }
                        }
                        .onSizeChanged {
                            if (selected) {
                                width.value = it
                            }
                        }
                ) {
                    BottomBarItemExpandable(
                        model = item,
                        colors = colors,
                        withBorder = false,
                        isSelected = selected,
                        onClick = onClick,
                    )
                }
                if (index < items.lastIndex) {
                    DSSpacerFilled()
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            content = {
                Box(
                    modifier = Modifier
                        .graphicsLayer { translationX = positionX }
                        .background(color = colors.selectionMark, CircleShape)
                        .size(
                            height = 6.dp,
                            width = with(density) {
                                val padding = DSBottomBarUtils.itemPadding * 4
                                width.value.width.toDp() - padding
                            },
                        )
                )
            }
        )
    }
}
