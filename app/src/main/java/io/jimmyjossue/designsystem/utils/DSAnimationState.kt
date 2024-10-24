package io.jimmyjossue.designsystem.utils

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp

private val colorAnimationSpec: AnimationSpec<Color> = spring(
    stiffness = Spring.StiffnessMediumLow
)

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
