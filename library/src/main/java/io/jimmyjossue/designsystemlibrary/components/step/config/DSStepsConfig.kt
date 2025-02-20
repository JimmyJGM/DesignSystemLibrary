package io.jimmyjossue.designsystemlibrary.components.step.config

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.jimmyjossue.designsystemlibrary.theme.catalog.color

object DSStepsConfig {
    @Composable
    fun getColors(
        primary: Color = color.primary,
        onPrimary: Color = color.onPrimary,
        outline: Color = color.typography,
        typography: Color = color.typography,
    ) = DSStepsColors(
        primary = primary,
        onPrimary = onPrimary,
        outline = outline,
        typography = typography,
    )
}
