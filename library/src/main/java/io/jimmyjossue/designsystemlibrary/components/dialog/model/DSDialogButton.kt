package io.jimmyjossue.designsystemlibrary.components.dialog.model

data class DSDialogButton(
    val text: String,
    val isEnabled: Boolean = true,
    val onClick: () -> Unit,
)
