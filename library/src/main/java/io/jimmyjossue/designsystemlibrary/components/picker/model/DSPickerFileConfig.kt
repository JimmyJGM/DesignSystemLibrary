package io.jimmyjossue.designsystemlibrary.components.picker.model

import androidx.compose.ui.unit.Dp

data class DSPickerFileConfig(
    val addButtonText: String? = null,
    val deleteButtonText: String? = null,
    val contentPaddingParent: Dp,
    val contentPaddingItem: Dp,
    val separationElements: Dp,
    val maxFiles: Int = Int.MAX_VALUE,
    val sizeOfEachFileInBytes: Long = Long.MAX_VALUE,
)
