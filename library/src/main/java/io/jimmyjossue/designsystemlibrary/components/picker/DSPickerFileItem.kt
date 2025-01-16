package io.jimmyjossue.designsystemlibrary.components.picker

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import io.jimmyjossue.designsystemlibrary.R
import io.jimmyjossue.designsystemlibrary.components.picker.DSPickerFileUtils.getIcon
import io.jimmyjossue.designsystemlibrary.components.picker.DSPickerFileUtils.toReadableFormat
import io.jimmyjossue.designsystemlibrary.components.picker.model.DSPickerFileColors
import io.jimmyjossue.designsystemlibrary.components.picker.model.DSPickerFileItem
import io.jimmyjossue.designsystemlibrary.components.separator.DSSpacer
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaHigh
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaLow
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaLower
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.shape
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography
import io.jimmyjossue.designsystemlibrary.utils.extension.fileExtension
import io.jimmyjossue.designsystemlibrary.utils.textdecorator.decoratedAnnotatedString

private val minHeight @Composable get() = dimension.large

@Composable
internal fun DSPickerFileItemWidget(
    modifier: Modifier = Modifier,
    data: DSPickerFileItem,
    contentPadding: Dp = dimension.medium,
    colors: DSPickerFileColors,
    shapeItem: Shape = shape.small,
    onDelete: (Uri) -> Unit,
) {
    val details = stringResource(
        id = R.string.ds_pickerFileItem_details,
        data.name.fileExtension.uppercase(),
        data.size.toReadableFormat(),
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = colors.background, shape = shapeItem)
            .padding(vertical = contentPadding),
        horizontalArrangement = Arrangement.spacedBy(dimension.medium)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(minHeight - dimension.small)
                .border(
                    width = 1.dp,
                    shape = CircleShape,
                    color = when (colors.isBackgroundLight()) {
                        true -> colors.typography.alphaLower
                        false -> colors.typography.alphaLow
                    },
                )
                .background(
                    shape = CircleShape,
                    color = when (colors.isBackgroundLight()) {
                        true -> Color.Transparent
                        false -> colors.typography.alphaLow
                    },
                )
        ) {
            Icon(
                modifier = Modifier.size(minHeight - dimension.semiLarge),
                painter = data.name.getIcon(),
                tint = Color.Unspecified,
                contentDescription = null,
            )
        }
        Column(
            modifier = Modifier
                .weight(1f, false)
                .heightIn(min = minHeight - dimension.small),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = data.name,
                modifier = Modifier.fillMaxWidth(),
                style = typography.body,
                color = colors.typography,
                overflow = Ellipsis,
                maxLines = 1,
            )
            DSSpacer(size = dimension.smalled)
            Text(
                text = details.decoratedAnnotatedString(),
                style = typography.caption,
                color = colors.typography.alphaMedium,
            )
        }
        IconButton(
            onClick = { onDelete(data.uri) },
            modifier = Modifier
                .size(size = minHeight - dimension.medium)
                .align(Alignment.CenterVertically),
            content = {
                IconDelete(tint = Color.White)
                IconDelete()
            },
            colors = IconButtonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = color.typographyDisabled,
                contentColor = when (colors.isBackgroundLight()) {
                    true -> colors.typography.alphaMedium
                    false -> color.error.alphaHigh
                },
            ),
        )
    }
}

@Composable
private fun IconDelete(tint: Color = LocalContentColor.current) {
    Icon(
        painter = painterResource(id = R.drawable.ic_ds_system_trash_light),
        tint = tint,
        contentDescription = null,
        modifier = Modifier
            .size(size = minHeight - dimension.semiLarge),
    )
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun PreviewDSPickerFileItemWidget() {
    val fileItem = DSPickerFileItem(name = "Nombre_del_archivo.png", size = 99948080L, uri = "Uri".toUri())
    val padding = dimension.medium
    val colors = DSPickerFileUtils.getColors()
    val shapeItem = shape.small

    Column {
        DSPickerFileItemWidget(
            modifier = Modifier,
            data = fileItem,
            contentPadding = padding,
            colors= colors,
            shapeItem = shapeItem,
            onDelete = { }
        )
        DSPickerFileItemWidget(
            modifier = Modifier,
            data = fileItem,
            contentPadding = padding,
            colors= colors.copy(
                background = Color.Gray,
                typography = Color.DarkGray
            ),
            shapeItem = shapeItem,
            onDelete = { }
        )
    }
}