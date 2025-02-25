package io.jimmyjossue.designsystemlibrary.components.picker.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystemlibrary.R
import io.jimmyjossue.designsystemlibrary.components.picker.model.DSPickerImageConfig
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaHigh
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography
import io.jimmyjossue.designsystemlibrary.utils.extension.borderDashed

@Composable
internal fun DSPickerImageEmptySelector(
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
                width = 4.dp,
                color = colorContainer,
                cornerRadius = cornerRadius
            )
            .background(
                color = colorContainer.alphaHigh,
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
