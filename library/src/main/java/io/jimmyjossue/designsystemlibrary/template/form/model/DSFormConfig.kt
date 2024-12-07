package io.jimmyjossue.designsystemlibrary.template.form.model

import androidx.compose.ui.unit.Dp

data class DSFormConfig(
    val isStickyButtonSubmit: Boolean = false,
    val paddingVertical: Dp,
    val paddingHorizontal: Dp,
    val separationSpace: Dp,
)
