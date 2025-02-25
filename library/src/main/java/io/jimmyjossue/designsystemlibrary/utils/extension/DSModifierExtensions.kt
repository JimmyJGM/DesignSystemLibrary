package io.jimmyjossue.designsystemlibrary.utils.extension

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.borderBottom(
    color: Color,
    width: Dp = 1.dp,
): Modifier = composed {
    drawBehind {
        val strokeWidth = width.value * density
        val y = size.height - strokeWidth / 2
        drawLine(
            color = color,
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = strokeWidth
        )
    }
}

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.borderTop(
    color: Color,
    width: Dp = 1.dp,
): Modifier = composed {
    drawBehind {
        val strokeWidth = width.value * density
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = strokeWidth
        )
    }
}

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.borderDashed(
    color: Color,
    width: Dp = 1.dp,
    cornerRadius: Dp = 0.dp
): Modifier = composed {
    val widthPx = with(LocalDensity.current) { width.toPx() }
    val cornerRadiusPx = with(LocalDensity.current) { cornerRadius.toPx() }
    drawBehind {
        val pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(10f, 10f), phase = 0f)
        drawRoundRect(
            color = color,
            style = Stroke(width = widthPx, pathEffect = pathEffect),
            cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
        )
    }
}


@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.dsClick(
    role: Role = Role.Button,
    isEnabled: Boolean,
    onClick: () -> Unit,
): Modifier = composed {
    val interaction = remember { MutableInteractionSource() }
    clickable(
        interactionSource = interaction,
        indication = ripple(),
        enabled = isEnabled,
        onClick = onClick,
        role = role,
    )
}

