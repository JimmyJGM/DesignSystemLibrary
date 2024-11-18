package io.jimmyjossue.designsystemlibrary.template.onboarding

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.jimmyjossue.designsystemlibrary.template.onboarding.DSOnboardingUtils.indicatorSize
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaLow
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.shape

@Composable
internal fun OnboardingIndicator(
    count: Int,
    current: Int,
    contentColor: Color,
    accentColor: Color,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimension.semiLarge),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(times = count) { indicator ->
            Box(
                modifier = Modifier
                    .padding(horizontal = indicatorSize.div(other = 3))
                    .height(height = if (indicator == current) indicatorSize.plus(dimension.smalled) else indicatorSize)
                    .width(width = if (indicator == current) dimension.semiLarge else indicatorSize)
                    .background(
                        color = if (indicator == current) accentColor else contentColor.alphaLow,
                        shape = shape.small
                    )
                    .animateContentSize()
            )
        }
    }
}
