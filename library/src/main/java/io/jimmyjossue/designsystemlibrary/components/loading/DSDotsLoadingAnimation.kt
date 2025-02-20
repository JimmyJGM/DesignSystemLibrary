package io.jimmyjossue.designsystemlibrary.components.loading

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography
import kotlinx.coroutines.delay

@Composable
fun DSDotsLoadingAnimation(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = color.typography,
    textStyle: TextStyle = typography.body,
    dotsColor: Color = color.primary,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        if (text.isNotBlank()) {
            Text(
                text = text,
                style = textStyle,
                color = textColor,
            )
        }
        DSDotsLoadingAnimation(
            dotsColor = dotsColor,
            dotsSize = dimension.smalled + 1.dp,
            dotsSpace = dimension.smalled,
            travelDistance = dimension.small,
            modifier = Modifier.padding(
                all = dimension.smalled.times(other = 2)
            )
        )
    }
}

@Composable
fun DSDotsLoadingAnimation(
    modifier: Modifier = Modifier,
    dotsSize: Dp = dimension.small,
    dotsSpace: Dp = dimension.small,
    dotsColor: Color = color.primary,
    travelDistance: Dp = dimension.large,
    durationMillis: Int = 4000,
) {
    val distance = with(LocalDensity.current) { travelDistance.toPx() }
    val circleValuesY = listOf(
        remember { Animatable(initialValue = 0F) },
        remember { Animatable(initialValue = 0F) },
        remember { Animatable(initialValue = 0F) },
    )
    val circles = circleValuesY.map { it.value }

    val animFractionDuration = durationMillis / circleValuesY.count()
    val animDotDuration = durationMillis / (circleValuesY.count() * 2)
    val delayDotDuration = animDotDuration / 2

    circleValuesY.forEachIndexed { index, circle ->
        LaunchedEffect(null) {
            delay(timeMillis = (index * delayDotDuration).toLong())
            circle.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        this.durationMillis = animFractionDuration
                        0.0f at 0 with LinearOutSlowInEasing
                        1.0f at (animFractionDuration/3) with LinearOutSlowInEasing
                        0.0f at (animFractionDuration/2) with LinearOutSlowInEasing
                        0.0f at animFractionDuration with LinearOutSlowInEasing
                    }
                )
            )
        }
    }

    Row(
        modifier = modifier.padding(0.dp),
        horizontalArrangement = Arrangement.spacedBy(space = dotsSpace),
    ) {
        circles.forEach { circle ->
            Box(
                modifier = Modifier
                    .size(size = dotsSize)
                    .graphicsLayer { translationY = -circle * distance }
                    .background(color = dotsColor, shape = CircleShape)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDotsLoadingAnimation() {
    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxSize()
    ) {
        DSDotsLoadingAnimation()
        DSDotsLoadingAnimation(text = "Cargando")
    }
}
