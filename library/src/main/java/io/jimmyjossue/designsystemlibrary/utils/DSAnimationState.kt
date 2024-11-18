package io.jimmyjossue.designsystemlibrary.utils

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp

private const val alphaMin = 0.35f
private const val alphaFull = 1f
private const val angleZero = 0f
private const val angleFull = 360f
private const val durationMillis = 3500
private val repeatModeRestart = RepeatMode.Restart
private val repeatModeReverse = RepeatMode.Reverse
internal val animationTween: TweenSpec<Float> = tween(durationMillis, easing = FastOutSlowInEasing)
private val colorAnimationSpec: AnimationSpec<Color> = spring(stiffness = Spring.StiffnessMediumLow)


@Composable
fun dsAnimateColorAsState(
    start: Color,
    stop: Color,
    fraction: Float,
    label: String = "animatedColor",
) = animateColorAsState(
    animationSpec = colorAnimationSpec,
    targetValue = lerp(start, stop, fraction),
    label = label,
)

@Composable
fun InfiniteTransition.dsAnimateRotatedState(
    initialValue: Float = angleZero,
    targetValue: Float = angleFull,
    repeatMode: RepeatMode = repeatModeRestart,
    label: String = "animatedRotate",
) = this.animateFloat(
    initialValue = initialValue,
    targetValue = targetValue,
    animationSpec = infiniteRepeatable(animationTween, repeatMode),
    label = label
)

@Composable
fun InfiniteTransition.dsAnimateAlphaState(
    initialValue: Float = alphaMin,
    targetValue: Float = alphaFull,
    repeatMode: RepeatMode = repeatModeReverse,
    label: String = "animatedAlpha",
) = this.animateFloat(
    initialValue = initialValue,
    targetValue = targetValue,
    animationSpec = infiniteRepeatable(animationTween, repeatMode),
    label = label
)
