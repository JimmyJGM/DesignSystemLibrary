package io.jimmyjossue.designsystemlibrary.components.toggle

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystemlibrary.components.button.DSButtonPrimary
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.utils.onClick

@Composable
internal fun DSToggleSwitch(
    isSelected: Boolean = false,
    size: Dp = 24.dp,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    val width = (size.value * 1.7F).dp
    val borderWidth: Dp = 2.dp

    val offset by animateDpAsState(
        targetValue = if (isSelected) width - size else 0.dp,
        animationSpec = tween(durationMillis = 300),
        label = ""
    )

    val iconColor = when (isEnabled) {
        true -> if (isSelected) color.primarySurface else color.onPrimary
        false -> if (isSelected) color.surface.alphaMedium else color.onPrimary.alphaMedium
    }

    val backgroundColor = when (isEnabled) {
        true -> if (isSelected) color.primary else color.surfaceDark
        false -> if (isSelected) color.primaryDisabled else color.surfaceDark
    }


    Box(
        modifier = Modifier
            .size(width = width, height = size)
            .clip(shape = CircleShape)
            .clickable(onClick = onClick, enabled = isEnabled)
            .background(color = backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .offset(x = offset, y = 0.dp)
                .padding(all = borderWidth)
                .clip(shape = CircleShape)
                .background(color = iconColor),
        )
    }
}

@Preview
@Composable
private fun PreviewDSToggleSwitch() {
    val selectedState = remember { mutableStateOf(false) }
    val backgroundState = remember { mutableStateOf(Color.White) }

    fun getRandomColor(): Int {
        return (1..250).random()
    }

    fun cambiarColor() {
        backgroundState.value = Color(getRandomColor(),getRandomColor(),getRandomColor())
    }

    Column(
        modifier = Modifier
            .background(color = backgroundState.value)
            .padding(dimension.medium),
        verticalArrangement = Arrangement.spacedBy(dimension.small)
    ) {
        DSToggleSwitch(
            isSelected = selectedState.value,
            onClick = { selectedState.value = !selectedState.value }
        )
        DSToggleSwitch(
            isSelected = !selectedState.value,
            onClick = onClick
        )
        DSToggleSwitch(
            isSelected = selectedState.value,
            isEnabled = false,
            onClick = onClick
        )
        DSToggleSwitch(
            isSelected = !selectedState.value,
            isEnabled = false,
            onClick = onClick
        )
        DSButtonPrimary(
            text = "Cambiar",
            onClick = ::cambiarColor
        )
    }
}