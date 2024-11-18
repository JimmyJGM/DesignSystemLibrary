package io.jimmyjossue.designsystemlibrary.components.loading

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.shape
import kotlinx.coroutines.delay

private const val alphaFull = 1f
private const val durationAnimation = 1500
private const val durationDelay = 1500L

@Composable
fun DSLoaderFadeBox(
    modifier: Modifier = Modifier,
    height: Dp,
    fractionWidth: Float = 1f,
    fadeOutValue: Float = 0.25f,
    background: Color = color.surfaceDark,
    shapeBox: Shape = shape.small,
) {
    val animatedFloat = remember { Animatable(alphaFull) }

    LaunchedEffect(animatedFloat) {
        delay(timeMillis = durationDelay)
        animatedFloat.animateTo(
            targetValue = if (animatedFloat.value == alphaFull) fadeOutValue else alphaFull,
            animationSpec = infiniteRepeatable(
                animation = tween(durationAnimation, easing =  FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .graphicsLayer { alpha = animatedFloat.value }
                .fillMaxWidth(fraction = fractionWidth)
                .height(height)
                .background(color = background, shape = shapeBox)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewDSLoaderBox() {
    Column(
        verticalArrangement = Arrangement.spacedBy(space = dimension.medium),
        modifier = Modifier.padding(all = dimension.medium)
    ) {
        DSLoaderFadeBox(height = dimension.medium)
        DSLoaderFadeBox(height = dimension.large)
    }
}