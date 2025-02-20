package io.jimmyjossue.designsystemlibrary.components.slider.config

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.shape

object DSSliderUtils {

    @Composable
    fun getColors(
        primary: Color = color.primary,
        outline: Color = color.surfaceDark,
        primaryDisabled: Color = color.primaryDisabled,
        outlineDisabled: Color = color.surface
    ) = DSSliderColors(
        primary = primary,
        outline = outline,
        primaryDisabled = primaryDisabled,
        outlineDisabled = outlineDisabled,
    )

    @Composable
    fun getConfig(
        thumbType: DSThumbType = DSThumbType.Circle,
        steps: Int = 0,
    ) = DSSliderConfig(
        thumbType = thumbType,
        steps = steps,
    )

    internal fun DSSliderColors.toSliderColors() = SliderColors(
        thumbColor = primary,
        activeTrackColor = primary,
        inactiveTrackColor = outline,
        activeTickColor = Color.Transparent,
        inactiveTickColor = Color.Transparent,
        disabledThumbColor = primaryDisabled,
        disabledActiveTrackColor = primaryDisabled,
        disabledActiveTickColor = primaryDisabled,
        disabledInactiveTickColor = Color.Transparent,
        disabledInactiveTrackColor = outlineDisabled
    )

    fun drawThumb(
        isEnabled: Boolean,
        config: DSSliderConfig,
        colors: DSSliderColors
    ) = @Composable {
        Box(
            modifier = Modifier
                .height(dimension.semiLarge)
                .width(
                    when (config.thumbType) {
                        DSThumbType.Square -> dimension.small
                        DSThumbType.Circle -> dimension.semiLarge
                    }
                )
                .background(
                    shape = when (config.thumbType) {
                        DSThumbType.Square -> shape.smalled
                        DSThumbType.Circle -> CircleShape
                    },
                    color = if (isEnabled) colors.primary else colors.primaryDisabled,
                )
        )
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    fun drawTrack(
        state: SliderState,
        isEnabled: Boolean,
        colors: DSSliderColors
    ) = SliderDefaults.Track(
            sliderState = state,
            modifier = Modifier.height(dimension.smalled * 2),
            trackInsideCornerSize = dimension.none,
            thumbTrackGapSize = dimension.none,
            colors = colors.toSliderColors(),
            enabled = isEnabled,
            drawStopIndicator = null,
        )
}