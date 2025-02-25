package io.jimmyjossue.designsystemlibrary.components.picker

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import io.jimmyjossue.designsystemlibrary.components.picker.model.DSPickerImageColors
import io.jimmyjossue.designsystemlibrary.components.picker.model.DSPickerImageConfig
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.utils.DSAlignHorizontal
import io.jimmyjossue.designsystemlibrary.utils.DSShapeType

object DSPickerImageUtils {
    @Composable
    fun getConfig(
        upLoadText: String? = null,
        changeText: String? = null,
        supportedExtensionsLabelText: String? = null,
        maxSizeLabelText: String? = null,
        maxSizeFile: Long = Long.MAX_VALUE,
        cornerRadius: Dp = dimension.small,
        contentPadding: Dp = dimension.large,
        imageShapeType: DSShapeType = DSShapeType.Rectangle,
        imageContentScale: ContentScale = ContentScale.Crop,
        withRemoveButton: Boolean = true,
        imageAspectRatio: Float = 1f,
        align: DSAlignHorizontal = DSAlignHorizontal.CENTER,
    ) = DSPickerImageConfig(
        upLoadText = upLoadText,
        changeText = changeText,
        supportedExtensionsLabelText = supportedExtensionsLabelText,
        maxSizeLabelText = maxSizeLabelText,
        maxSizeFile = maxSizeFile,
        cornerRadius = cornerRadius,
        contentPadding = contentPadding,
        withRemoveButton = withRemoveButton,
        imageShapeType = imageShapeType,
        imageContentScale = imageContentScale,
        imageAspectRatio = imageAspectRatio,
        align = align,
    )

    @Composable
    fun getColors(
        primary: Color = color.primary,
        onPrimary: Color = color.onPrimary,
        primaryDisabled: Color = color.primaryDisabled,
        container: Color = color.surfaceDark,
        typography: Color = color.typography,
        typographyDisabled: Color = color.typographyDisabled,
        uploadedImageBorder: Color? = null,
    ) = DSPickerImageColors(
        primary = primary,
        primaryDisabled = primaryDisabled,
        onPrimary = onPrimary,
        container = container,
        typography = typography,
        typographyDisabled = typographyDisabled,
        uploadedImageBorder = uploadedImageBorder,
    )
}
