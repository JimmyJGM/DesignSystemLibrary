package io.jimmyjossue.designsystemlibrary.components.picker

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import io.jimmyjossue.designsystemlibrary.R
import io.jimmyjossue.designsystemlibrary.components.input.DSInputText
import io.jimmyjossue.designsystemlibrary.components.picker.DSPickerFileUtils.toReadableFormat
import io.jimmyjossue.designsystemlibrary.components.picker.model.DSPickerImageColors
import io.jimmyjossue.designsystemlibrary.components.picker.model.DSPickerImageConfig
import io.jimmyjossue.designsystemlibrary.components.separator.DSSpacerFilled
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaHigh
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaLow
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography
import io.jimmyjossue.designsystemlibrary.utils.DSAlignHorizontal
import io.jimmyjossue.designsystemlibrary.utils.DSShapeType
import io.jimmyjossue.designsystemlibrary.utils.extension.borderDashed

@Composable
fun DSPickerImage(
    modifier: Modifier = Modifier,
    colors: DSPickerImageColors = DSPickerImageUtils.getColors(),
    config: DSPickerImageConfig = DSPickerImageUtils.getConfig(),
    onSelectedImage: ((Uri) -> Boolean)? = null,
    imageFormat: String? = null,
    isEnabled: Boolean = true,
) {

    val uriImage = remember { mutableStateOf<Uri?>(null) }
    val imagePatternFormatState = remember { mutableStateOf("") }
    val pickerLauncher = rememberLauncherForActivityResult(contract = GetContent()) { data ->
        data?.let {
            if (onSelectedImage?.invoke(data) != false) {
                uriImage.value = data
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
        DSInputText(value = imagePatternFormatState.value) {
            imagePatternFormatState.value = it
        }

        if (uriImage.value == null) {
            EmptySelectorImage(
                config = config,
                isEnabled = isEnabled,
                colorPrimary = getColorPrimary(),
                colorContainer = getColorContainer(),
                colorTypography = getColorTypography(),
                cornerRadius = config.cornerRadius,
                contentPadding = config.contentPadding,
                onClick = ::doOnLauncher,
            )
            EmptyFooter(
                config = config,
                imageFormat = imageFormat,
                colorTypography = getColorTypography(),
            )
        } else {
            UploadImage(
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
private fun EmptySelectorImage(
    isEnabled: Boolean,
    colorPrimary: Color,
    colorContainer: Color,
    colorTypography: Color,
    config: DSPickerImageConfig,
    contentPadding: Dp,
    cornerRadius: Dp,
    onClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimension.small),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = cornerRadius))
            .clickable(enabled = isEnabled, onClick = onClick)
            .borderDashed(
                width = 2.dp,
                color = colorContainer.alphaHigh,
                cornerRadius = cornerRadius
            )
            .background(
                color = colorContainer.alphaLow,
                shape = RoundedCornerShape(size = cornerRadius)
            )
            .padding(all = contentPadding)
    ) {
        Image(
            modifier = Modifier.size(dimension.large),
            painter = painterResource(id = R.drawable.ds_im_image_upload),
            colorFilter = ColorFilter.tint(color = colorPrimary.alphaHigh),
            contentDescription = null,
        )
        if (!config.upLoadText.isNullOrBlank()) {
            Text(
                text = config.upLoadText,
                style = typography.caption,
                color = colorTypography.alphaMedium,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun EmptyFooter(
    colorTypography: Color,
    imageFormat: String?,
    config: DSPickerImageConfig,
) {
    if (
        (!config.supportedExtensionsLabelText.isNullOrBlank() && !imageFormat.isNullOrBlank())
        || config.maxSizeFile != Long.MAX_VALUE
    ) {
        Row {
            if (!config.supportedExtensionsLabelText.isNullOrBlank() && !imageFormat.isNullOrBlank()) {
                Text(
                    text = "${config.supportedExtensionsLabelText}: ${imageFormat.toLowerCase(Locale.current)}",
                    color = colorTypography.alphaMedium,
                    style = typography.caption,
                )
            }
            DSSpacerFilled()
            if (config.maxSizeFile != Long.MAX_VALUE) {
                Text(
                    text = "${config.maxSizeLabelText ?: "Max"}: ${config.maxSizeFile.toReadableFormat()}",
                    color = colorTypography.alphaMedium,
                    style = typography.caption,
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun UploadImage(
    uriImage: Uri,
    isEnabled: Boolean,
    config: DSPickerImageConfig,
    imageShape: Shape,
    colors: DSPickerImageColors,
    onRemoveImage: () -> Unit,
    onAdd: () -> Unit,
) {
    @Composable
    fun getBorderModifier() = when (colors.isAvailableBorderColor()) {
        true -> Modifier.border(
            width = dimension.smalled,
            color = colors.uploadedImageBorder!!,
            shape = imageShape,
        )
        false -> Modifier
    }

    Box(
        contentAlignment = Alignment.TopEnd,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(ratio = 1f)
    ) {
        GlideImage(
            model = uriImage,
            contentDescription = null,
            contentScale = config.imageContentScale,
            modifier = Modifier
                .clip(shape = imageShape)
                .then(other = getBorderModifier())
                .matchParentSize(),
        )
        if (config.withRemoveButton) {
            IconButton(
                modifier = Modifier
                    .padding(all = dimension.small)
                    .size(dimension.semiLarge),
                onClick = onRemoveImage,
                colors = IconButtonColors(
                    containerColor = color.surfaceDark.alphaMedium,
                    contentColor = Color.White,
                    disabledContainerColor = color.surfaceDark.alphaLow,
                    disabledContentColor = Color.White.alphaLow,
                )
            ) {
                Icon(
                    modifier = Modifier.padding(all = dimension.smalled*2),
                    painter = painterResource(id = R.drawable.ds_ic_action_close),
                    contentDescription = null,
                )
            }
        }
    }
    DSPickerButtonChange(
        text = config.changeText,
        isEnabled = isEnabled,
        onClick = onAdd,
        colors = DSPickerFileUtils.getColors(
            primary = colors.primary,
            primaryDisabled = colors.primaryDisabled,
            onPrimary = colors.onPrimary,
            typography = colors.typography,
        )
    )
}

private fun List<String>.toFormatLabel() = joinToString(separator = ", ") { it }

private fun String?.toPickerPatter(fileType: String? = null): String {
    val patternFile = fileType.takeIf { !it.isNullOrBlank() } ?: "*"
    val patternFormat = this.takeIf { !it.isNullOrBlank() } ?: "*"
    return "$patternFile/$patternFormat".also { Log.d("filePatternPicker", it) }
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
        maxSizeFile = 12345L,
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(dimension.small),
        modifier = Modifier.padding(dimension.small)
    ) {
        DSPickerImage(
            colors = colors,
            config = config,
            imageFormat = "jpg",
            onSelectedImage = { uri ->

                true
            }
        )
    }
}
