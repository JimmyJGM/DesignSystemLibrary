package io.jimmyjossue.designsystem.components.topBar

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.jimmyjossue.designsystem.theme.color
import io.jimmyjossue.designsystem.utils.dsAnimateColorAsState

object DSTopBarUtils {
    @Composable
    fun getColors(
        content: Color = color.typography,
        container: Color = color.background,
        accent: Color = color.accent,
        containerScrolled: Color = color.surface,
        borderScrolled: Color = color.surfaceDark,
    ) = DSTopBarColors(
        content = content,
        container = container,
        accent = accent,
        containerScrolled = containerScrolled,
        borderScrolled = borderScrolled,
    )

    @OptIn(ExperimentalMaterial3Api::class)
    internal fun TopAppBarScrollBehavior.getFraction(): Float {
        return FastOutLinearInEasing.transform(
            fraction = if (state.overlappedFraction > 0.01f) 1f else 0f
        )
    }

    @Composable
    internal fun animateBorderColor(
        colors: DSTopBarColors,
        fraction: Float,
    ) = dsAnimateColorAsState(
        fraction = fraction,
        start = colors.container,
        stop = colors.borderScrolled,
    )

    @Composable
    internal fun animateContainerColor(
        colors: DSTopBarColors,
        fraction: Float,
    ) = dsAnimateColorAsState(
        fraction = fraction,
        start = colors.container,
        stop = colors.containerScrolled,
    )
}