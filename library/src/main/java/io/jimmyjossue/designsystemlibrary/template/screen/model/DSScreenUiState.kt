package io.jimmyjossue.designsystemlibrary.template.screen.model

import io.jimmyjossue.designsystemlibrary.components.dialog.model.DSDialogContent

internal data class DSScreenUiState(
    val isLoading: Boolean = false,
    val dialogContent: DSDialogContent? = null,
)
