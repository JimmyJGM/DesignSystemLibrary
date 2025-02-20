package io.jimmyjossue.designsystemlibrary.components.step.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.jimmyjossue.designsystemlibrary.components.step.config.DSStepsColors
import io.jimmyjossue.designsystemlibrary.theme.catalog.color

object DSStepUtils {

    @Composable
    fun getColors(
        primary: Color = color.primary,
        onPrimary: Color = color.onPrimary,
        typography: Color = color.typography,
        outline: Color = color.surfaceDark,
    ) = DSStepsColors(
        primary = primary,
        onPrimary = onPrimary,
        typography = typography,
        outline = outline,
    )
}