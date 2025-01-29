package io.jimmyjossue.designsystemlibrary.components.picker.image

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import io.jimmyjossue.designsystemlibrary.components.picker.DSPickerFileUtils.toReadableFormat
import io.jimmyjossue.designsystemlibrary.components.picker.model.DSPickerImageConfig
import io.jimmyjossue.designsystemlibrary.components.separator.DSSpacerFilled
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography

@Composable
internal fun DSPickerImageEmptyFooter(
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
