package io.jimmyjossue.designsystemlibrary.components.picker.model

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import io.jimmyjossue.designsystemlibrary.utils.DSAlignHorizontal
import io.jimmyjossue.designsystemlibrary.utils.DSShapeType

data class DSPickerImageConfig(
    val upLoadText: String? = null,
    val supportedExtensionsLabelText: String? = null,
    val changeText: String? = null,
    val maxSizeLabelText: String? = null,
    val maxSizeFile: Long,
    val cornerRadius: Dp,
    val contentPadding: Dp,
    val withRemoveButton: Boolean,
    val imageShapeType: DSShapeType,
    val imageContentScale: ContentScale,
    val imageAspectRatio: Float,
    val align: DSAlignHorizontal = DSAlignHorizontal.CENTER,
)
