package io.jimmyjossue.designsystemlibrary.components.picker

import android.net.Uri
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
import androidx.core.net.toUri
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
    return "$patternFile/$patternFormat"
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

    fun doOnRemoveImage() {
        uriImage.value = null
        onSelectedImage(null)
    }

    PickerImageContent(
        modifier = modifier,
        imageUri = uriImage.value,
        isEnabled = isEnabled,
        imageFormat = imageFormat,
        colors = colors,
        config = config,
        onValidationImageSelected = onValidationImageSelected,
        onClickRemoveImage = ::doOnRemoveImage,
        onSelectedImage = { uri ->
            uriImage.value = uri
            onSelectedImage.invoke(uri)
        },
    )
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
            val condition = onValidationImageSelected?.invoke(data) ?: true
            if (condition) {
                onSelectedImage(data)
            }
        }
    }

    fun doOnLauncher() = pickerLauncher.launch(imageFormat.toPickerPatter(fileType = "image"))

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
                colorPrimary = colors.getPrimary(isEnabled = isEnabled),
                colorContainer = colors.getContainer(isEnabled = isEnabled),
                colorTypography = colors.getTypography(isEnabled = isEnabled),
                cornerRadius = config.cornerRadius,
                contentPadding = config.contentPadding,
                onClick = ::doOnLauncher,
            )
            DSPickerImageEmptyFooter(
                config = config,
                imageFormat = imageFormat,
                colorTypography = colors.getTypography(isEnabled = isEnabled),
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
    val image = remember { mutableStateOf<Uri?>(null) }
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
            imageUri = image.value,
            modifier = Modifier,
            colors = colors,
            onClickRemoveImage = { },
            config = config,
        ) {
            image.value = "".toUri()
        }
    }
}
