package io.jimmyjossue.designsystemlibrary.components.mark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension

@Composable
fun DSNotificationMark(
    modifier: Modifier = Modifier,
    borderWidth: Dp = dimension.smalled,
    colorMark: Color = color.primary,
    colorText: Color = color.onPrimary,
    sizeMark: Dp = dimension.small,
) {
    val density = LocalDensity.current
    val stroke = remember(borderWidth) {
        val strokeValue = with(density) { borderWidth.toPx() }
        Stroke(strokeValue)
    }

    Box(
        modifier = modifier
            .drawWithContent {
                drawContent()
                drawCircle(
                    color = Color.Black,
                    radius = size.minDimension / 2,
                    center = size.center,
                    style = stroke,
                    blendMode = BlendMode.Clear,
                )
            }
            .clip(shape = CircleShape)
    ) {
        Box(
            modifier = Modifier
                .widthIn(min = sizeMark)
                .height(height = sizeMark)
                .background(color = colorMark)
        )
    }
}
