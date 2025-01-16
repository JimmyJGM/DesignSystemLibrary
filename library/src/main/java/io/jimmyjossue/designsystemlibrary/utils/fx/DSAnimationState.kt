package io.jimmyjossue.designsystemlibrary.utils.fx

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.LinearEasing
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
private const val durationMillisMedium = 3500
private const val durationMillisShort = 1000
private val repeatModeRestart = RepeatMode.Restart
private val repeatModeReverse = RepeatMode.Reverse
internal val floatAnimationSpec: TweenSpec<Float> = tween(durationMillisMedium, easing = FastOutSlowInEasing)
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
    label: String = "animatedRotate",
    initialValue: Float = angleZero,
    targetValue: Float = angleFull,
    duration: Int = durationMillisMedium,
    easing: Easing = LinearEasing,
    repeatMode: RepeatMode = repeatModeRestart,
) = this.animateFloat(
    label = label,
    initialValue = initialValue,
    targetValue = targetValue,
    animationSpec = infiniteRepeatable(
        repeatMode = repeatMode,
        animation = tween(
            durationMillis = duration,
            easing = easing
        ),
    ),
)

@Composable
fun InfiniteTransition.dsAnimateAlphaState(
    label: String = "animatedAlpha",
    initialValue: Float = alphaMin,
    targetValue: Float = alphaFull,
    duration: Int = durationMillisShort,
    easing: Easing = LinearEasing,
    repeatMode: RepeatMode = repeatModeReverse,
) = this.animateFloat(
    label = label,
    initialValue = initialValue,
    targetValue = targetValue,
    animationSpec = infiniteRepeatable(
        repeatMode = repeatMode,
        animation = tween(
            durationMillis = duration,
            easing = easing
        ),
    ),
)
