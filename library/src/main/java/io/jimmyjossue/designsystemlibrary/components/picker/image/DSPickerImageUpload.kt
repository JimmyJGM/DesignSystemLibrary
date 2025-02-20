package io.jimmyjossue.designsystemlibrary.components.picker.image

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import io.jimmyjossue.designsystemlibrary.R
import io.jimmyjossue.designsystemlibrary.components.picker.DSPickerButtonChange
import io.jimmyjossue.designsystemlibrary.components.picker.DSPickerFileUtils
import io.jimmyjossue.designsystemlibrary.components.picker.model.DSPickerImageColors
import io.jimmyjossue.designsystemlibrary.components.picker.model.DSPickerImageConfig
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaLow
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
internal fun DSPickerImageUpload(
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
                    modifier = Modifier.padding(all = dimension.smalled * 2),
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

