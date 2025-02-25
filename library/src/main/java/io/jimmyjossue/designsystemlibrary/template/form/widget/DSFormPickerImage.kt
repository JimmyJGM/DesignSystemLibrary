package io.jimmyjossue.designsystemlibrary.template.form.widget

import android.net.Uri
import androidx.compose.runtime.Composable
import io.jimmyjossue.designsystemlibrary.components.picker.DSPickerImage
import io.jimmyjossue.designsystemlibrary.components.picker.DSPickerImageUtils
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormColors
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormElement
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaSemiLow

@Composable
internal fun DSFormPickerImage(
    model: DSFormElement.PickerImage,
    colors: DSFormColors,
    onChangeValue: (String, Uri?) -> Unit
) {
    DSPickerImage(
        imageFormat = model.imageFormat,
        isEnabled = model.isEnabled,
        onSelectedImage = {
            onChangeValue(model.key, it)
        },
        config = DSPickerImageUtils.getConfig(
            upLoadText = model.upLoadText,
            changeText = model.changeText,
            supportedExtensionsLabelText = model.supportedExtensionsLabelText,
            maxSizeLabelText = model.maxSizeLabelText,
            maxSizeFile = model.maxSizeFile,
            withRemoveButton = model.withRemoveButton,
            imageShapeType = model.imageShapeType,
            imageContentScale = model.imageContentScale,
            imageAspectRatio = model.imageAspectRatio,
        ),
        colors = DSPickerImageUtils.getColors(
            primary = colors.primary,
            primaryDisabled = colors.primaryDisabled,
            onPrimary = colors.onPrimary,
            container = colors.surface,
            typography = colors.typography,
            typographyDisabled = colors.typography.alphaSemiLow,
            uploadedImageBorder = colors.accent,
        ),
    )
}
