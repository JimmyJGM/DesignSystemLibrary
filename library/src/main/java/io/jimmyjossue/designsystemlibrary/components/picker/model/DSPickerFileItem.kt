package io.jimmyjossue.designsystemlibrary.components.picker.model

import android.net.Uri

data class DSPickerFileItem(
    val name: String,
    val size: Long,
    val uri: Uri,
)
