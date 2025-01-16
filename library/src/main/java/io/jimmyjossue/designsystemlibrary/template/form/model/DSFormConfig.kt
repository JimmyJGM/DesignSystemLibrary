package io.jimmyjossue.designsystemlibrary.template.form.model

import androidx.compose.ui.unit.Dp

data class DSFormConfig(
    val isStickyButtonSubmit: Boolean = false,
    val paddingForm: Dp,
    val paddingElements: Dp,
    val spaceSections: Dp,
    val spaceElements: Dp,
)
