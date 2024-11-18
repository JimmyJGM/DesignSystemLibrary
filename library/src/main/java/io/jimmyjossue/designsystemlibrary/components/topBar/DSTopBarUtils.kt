package io.jimmyjossue.designsystemlibrary.components.topBar

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.utils.dsAnimateColorAsState

object DSTopBarUtils {
    @Composable
    fun getColors(
        content: Color = color.typography,
        container: Color = color.background,
        accent: Color = color.accent,
        containerScrolled: Color = color.surface,
        contentScrolled: Color = color.typography,
        borderScrolled: Color = color.surfaceDark,
    ) = DSTopBarColors(
        content = content,
        container = container,
        accent = accent,
        containerScrolled = containerScrolled,
        contentScrolled = contentScrolled,
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

    @Composable
    internal fun animateContentColor(
        colors: DSTopBarColors,
        fraction: Float,
    ) = dsAnimateColorAsState(
        fraction = fraction,
        start = colors.content,
        stop = colors.contentScrolled,
    )

    @Composable
    internal fun  getStatusBarMinHeight(): Dp {
        return with(LocalDensity.current) { WindowInsets.statusBars.getTop(density = this).toDp() }
            .plus(other = 62.dp)
    }
}
