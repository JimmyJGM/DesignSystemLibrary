package io.jimmyjossue.designsystemlibrary.components.dialog.model

import androidx.compose.ui.graphics.Shape

data class DSDialogConfig(
    val dialogShape: Shape,
    val imageFraction: Float = 1f,
    val isEnableBackHandler: Boolean = false,
)
