package io.jimmyjossue.designsystemlibrary.components.picker

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.jimmyjossue.designsystemlibrary.components.input.DSInputText
import io.jimmyjossue.designsystemlibrary.components.picker.image.DSPickerImageEmptyFooter
import io.jimmyjossue.designsystemlibrary.components.picker.image.DSPickerImageEmptySelector
import io.jimmyjossue.designsystemlibrary.components.picker.image.DSPickerImageUpload
import io.jimmyjossue.designsystemlibrary.components.picker.model.DSPickerImageColors
import io.jimmyjossue.designsystemlibrary.components.picker.model.DSPickerImageConfig
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.utils.DSAlignHorizontal
import io.jimmyjossue.designsystemlibrary.utils.DSShapeType

private fun String?.toPickerPatter(fileType: String? = null): String {
    val patternFile = fileType.takeIf { !it.isNullOrBlank() } ?: "*"
    val patternFormat = this.takeIf { !it.isNullOrBlank() } ?: "*"
    return "$patternFile/$patternFormat".also { Log.d("filePatternPicker", it) }
}

@Composable
fun DSPickerImage(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    imageFormat: String? = null,
    colors: DSPickerImageColors = DSPickerImageUtils.getColors(),
    config: DSPickerImageConfig = DSPickerImageUtils.getConfig(),
    onValidationImageSelected: ((Uri) -> Boolean)? = null,
    onSelectedImage: (Uri?) -> Unit,
) {

    val uriImage = remember { mutableStateOf<Uri?>(null) }
    val pickerLauncher = rememberLauncherForActivityResult(contract = GetContent()) { data ->
        data?.let {
            if (onValidationImageSelected?.invoke(data) != false) {
                uriImage.value = data
                onSelectedImage(uriImage.value)
            }
        }
    }

    fun doOnLauncher() = pickerLauncher.launch(imageFormat.toPickerPatter(fileType = "image"))

    fun getColorPrimary() = if (isEnabled) colors.primary else colors.primaryDisabled
    fun getColorContainer() = if (isEnabled) colors.container else colors.primaryDisabled
    fun getColorTypography() = if (isEnabled) colors.typography else colors.typographyDisabled
    fun getImageShape() = when (config.imageShapeType) {
        DSShapeType.Circle -> CircleShape
        DSShapeType.Rectangle -> RoundedCornerShape(size = config.cornerRadius)
    }


    fun doOnRemoveImage() {
        uriImage.value = null
        onSelectedImage(null)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimension.small),
        horizontalAlignment = when (config.align) {
            DSAlignHorizontal.LEFT -> Alignment.Start
            DSAlignHorizontal.CENTER -> Alignment.CenterHorizontally
            DSAlignHorizontal.RIGHT -> Alignment.End
        }
    ) {
        if (uriImage.value == null) {
            DSPickerImageEmptySelector(
                config = config,
                isEnabled = isEnabled,
                colorPrimary = getColorPrimary(),
                colorContainer = getColorContainer(),
                colorTypography = getColorTypography(),
                cornerRadius = config.cornerRadius,
                contentPadding = config.contentPadding,
                onClick = ::doOnLauncher,
            )
            DSPickerImageEmptyFooter(
                config = config,
                imageFormat = imageFormat,
                colorTypography = getColorTypography(),
            )
        } else {
            DSPickerImageUpload(
                uriImage = uriImage.value!!,
                colors = colors,
                config = config,
                isEnabled = isEnabled,
                imageShape = getImageShape(),
                onRemoveImage = ::doOnRemoveImage,
                onAdd = ::doOnLauncher,
            )
        }
    }
}

@Composable
private fun PickerImageContent(
    modifier: Modifier = Modifier,
    imageUri: Uri? = null,
    isEnabled: Boolean = true,
    imageFormat: String? = null,
    colors: DSPickerImageColors = DSPickerImageUtils.getColors(),
    config: DSPickerImageConfig = DSPickerImageUtils.getConfig(),
    onValidationImageSelected: ((Uri) -> Boolean)? = null,
    onClickRemoveImage: () -> Unit,
    onSelectedImage: (Uri?) -> Unit,
) {
    val pickerLauncher = rememberLauncherForActivityResult(contract = GetContent()) { data ->
        data?.let {
            if (onValidationImageSelected?.invoke(data) != false) {
                onSelectedImage(data)
            }
        }
    }

    fun doOnLauncher() = pickerLauncher.launch(imageFormat.toPickerPatter(fileType = "image"))
    fun getColorPrimary() = if (isEnabled) colors.primary else colors.primaryDisabled
    fun getColorContainer() = if (isEnabled) colors.container else colors.primaryDisabled
    fun getColorTypography() = if (isEnabled) colors.typography else colors.typographyDisabled
    fun getImageShape() = when (config.imageShapeType) {
        DSShapeType.Circle -> CircleShape
        DSShapeType.Rectangle -> RoundedCornerShape(size = config.cornerRadius)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimension.small),
        horizontalAlignment = when (config.align) {
            DSAlignHorizontal.LEFT -> Alignment.Start
            DSAlignHorizontal.CENTER -> Alignment.CenterHorizontally
            DSAlignHorizontal.RIGHT -> Alignment.End
        }
    ) {
        if (imageUri == null) {
            DSPickerImageEmptySelector(
                config = config,
                isEnabled = isEnabled,
                colorPrimary = getColorPrimary(),
                colorContainer = getColorContainer(),
                colorTypography = getColorTypography(),
                cornerRadius = config.cornerRadius,
                contentPadding = config.contentPadding,
                onClick = ::doOnLauncher,
            )
            DSPickerImageEmptyFooter(
                config = config,
                imageFormat = imageFormat,
                colorTypography = getColorTypography(),
            )
        } else {
            DSPickerImageUpload(
                uriImage = imageUri,
                colors = colors,
                config = config,
                isEnabled = isEnabled,
                imageShape = getImageShape(),
                onRemoveImage = onClickRemoveImage,
                onAdd = ::doOnLauncher,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun PreviewDSPickerImage() {
    val colors = DSPickerImageUtils.getColors(
        primary = color.primary,
        typography = color.typography,
        primaryDisabled = color.primaryDisabled,
        typographyDisabled = color.typographyDisabled,
    )
    val config = DSPickerImageUtils.getConfig(
        upLoadText = "Agrega tu imagen",
        supportedExtensionsLabelText = "Formatos soportados",
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(dimension.small),
        modifier = Modifier.padding(dimension.small)
    ) {
        PickerImageContent(
            imageUri = null,
            modifier = Modifier,
            colors = colors,
            onClickRemoveImage = { },
            config = config,
        ) {

        }
    }
}
