package io.jimmyjossue.designsystemlibrary.components.slider.model

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystemlibrary.components.slider.config.DSSliderColors
import io.jimmyjossue.designsystemlibrary.components.slider.config.DSSliderConfig
import io.jimmyjossue.designsystemlibrary.components.slider.config.DSSliderUtils
import io.jimmyjossue.designsystemlibrary.components.slider.config.DSSliderUtils.toSliderColors
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension

private val height @Composable get() = dimension.smalled * 2

@Composable
fun DSSlider(
    value: Float,
    isEnabled: Boolean = true,
    colors: DSSliderColors = DSSliderUtils.getColors(),
    config: DSSliderConfig = DSSliderUtils.getConfig(),
    onValueChange: (Float) -> Unit,
) {
    val size = remember { mutableStateOf(IntSize.Zero) }

    @OptIn(ExperimentalMaterial3Api::class)
    Box(
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 0.5.dp)
                .fillMaxWidth()
                .height(height = height)
                .background(
                    brush = Brush.linearGradient(
                        colors = when (isEnabled) {
                            true -> listOf(colors.primary, colors.outline)
                            false -> listOf(colors.primaryDisabled, colors.outlineDisabled)
                        },
                        start = Offset(x = dimension.medium.value, y = 0f),
                        end = Offset(x = size.value.width - dimension.medium.value, y = 0f)
                    )
                )
                .onGloballyPositioned {
                    size.value = it.size
                }
        )
        Slider(
            modifier = Modifier
                .height(dimension.semiLarge)
                .fillMaxWidth(),
            value = value,
            enabled = isEnabled,
            steps = (config.steps - 1).coerceAtLeast(minimumValue = 0),
            colors = colors.toSliderColors(),
            valueRange = 0.0F..1F,
            onValueChange = onValueChange,
            thumb = {
                DSSliderUtils.drawThumb(
                    isEnabled = isEnabled,
                    config = config,
                    colors = colors
                ).invoke()
            },
            track = {
                DSSliderUtils.drawTrack(
                    state = it,
                    isEnabled = isEnabled,
                    colors = colors
                )
            }
        )
    }
}
