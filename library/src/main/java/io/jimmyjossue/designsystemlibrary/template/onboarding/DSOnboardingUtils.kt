package io.jimmyjossue.designsystemlibrary.template.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension

object DSOnboardingUtils {

    const val RATIO_IMAGE = 0.85f
    val indicatorSize @Composable get() = dimension.small.plus(other = dimension.smalled)

    @Composable
    fun getColors(
        primary: Color = color.primary,
        onPrimary: Color = color.onPrimary,
        background: Color = color.background,
        onBackground: Color = color.typography,
    ) = DSOnboardingColors(
        primary = primary,
        onPrimary = onPrimary,
        background = background,
        onBackground = onBackground,
    )

}
