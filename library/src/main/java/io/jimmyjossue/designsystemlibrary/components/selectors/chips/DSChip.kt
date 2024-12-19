package io.jimmyjossue.designsystemlibrary.components.selectors.chips

import java.util.UUID

data class DSChip(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val isSelected: Boolean = false,
    val isEnabled: Boolean = true,
)

