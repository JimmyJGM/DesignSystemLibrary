package io.jimmyjossue.designsystemlibrary.components.picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.jimmyjossue.designsystemlibrary.R
import io.jimmyjossue.designsystemlibrary.components.picker.buttonaction.DSButtonBorderType
import io.jimmyjossue.designsystemlibrary.components.picker.buttonaction.DSPickerButtonAction
import io.jimmyjossue.designsystemlibrary.components.picker.buttonaction.DSPickerButtonActionUtils
import io.jimmyjossue.designsystemlibrary.components.picker.model.DSPickerFileColors
import io.jimmyjossue.designsystemlibrary.components.picker.model.DSPickerFilesSortType
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaHigh
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaLow
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaSemiLow
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.radius

@Composable
internal fun DSPickerButtonAdd(
    text: String? = null,
    isEnabled: Boolean = true,
    colors: DSPickerFileColors = DSPickerFileUtils.getColors(),
    onClick: () -> Unit,
) {
    DSPickerButtonAction(
        text = text,
        onClick = onClick,
        isEnabled = isEnabled,
        icon = R.drawable.ic_ds_action_addition,
        borderType = DSButtonBorderType.None,
        colors = DSPickerButtonActionUtils.getColors(
            content = colors.primary,
            background = colors.primary.alphaLow,
            contentDisabled = colors.primaryDisabled.alphaHigh,
            backgroundDisabled = colors.primaryDisabled.alphaSemiLow,
        ),
    )
}

@Composable
internal fun DSPickerButtonDelete(
    text: String? = null,
    isEnabled: Boolean = true,
    colors: DSPickerFileColors = DSPickerFileUtils.getColors(),
    onClick: () -> Unit,
) {
    DSPickerButtonAction(
        text = text,
        onClick = onClick,
        isEnabled = isEnabled,
        icon = R.drawable.ic_ds_system_trash_light,
        borderType = when (colors.background != colors.surface) {
            true -> DSButtonBorderType.None
            false -> DSButtonBorderType.Line
        },
        colors = DSPickerButtonActionUtils.getColors(
            content = colors.typography.alphaHigh,
            background = colors.surface,
            contentDisabled = colors.primaryDisabled.alphaHigh,
            backgroundDisabled = colors.primaryDisabled.alphaSemiLow,
        ),
    )
}

@Composable
internal fun DSPickerButtonChange(
    text: String? = null,
    isEnabled: Boolean = true,
    colors: DSPickerFileColors = DSPickerFileUtils.getColors(),
    onClick: () -> Unit,
) {
    DSPickerButtonAction(
        text = text,
        onClick = onClick,
        isEnabled = isEnabled,
        icon = null,
        borderType = DSButtonBorderType.None,
        contentPadding = PaddingValues(
            horizontal = dimension.medium,
            vertical = dimension.small,
        ),
        colors = DSPickerButtonActionUtils.getColors(
            content = colors.onPrimary,
            background = colors.primary,
            contentDisabled = colors.typography.alphaMedium,
            backgroundDisabled = colors.primaryDisabled.alphaSemiLow,
        ),
    )
}

@Composable
internal fun DSPickerButtonSort(
    isEnabled: Boolean = true,
    state: DSPickerFilesSortType = DSPickerFilesSortType.Ascending,
    colors: DSPickerFileColors = DSPickerFileUtils.getColors(),
    onClick: () -> Unit,
) {
    DSPickerButtonAction(
        text = null,
        onClick = onClick,
        isEnabled = isEnabled,
        icon = when (state) {
            DSPickerFilesSortType.Ascending -> R.drawable.ds_ic_action_sort_list_up_light
            DSPickerFilesSortType.Descending -> R.drawable.ds_ic_action_sort_list_down_light
        },
        borderType = when (colors.background != colors.surface) {
            true -> DSButtonBorderType.None
            false -> DSButtonBorderType.Line
        },
        colors = DSPickerButtonActionUtils.getColors(
            content = colors.typography.alphaHigh,
            background = colors.surface,
            contentDisabled = colors.primaryDisabled.alphaHigh,
            backgroundDisabled = colors.primaryDisabled.alphaSemiLow,
        ),
    )
}

@Composable
internal fun DSPickerFileUniqueButtonAdd(
    text: String?,
    isEnabled: Boolean = true,
    colors: DSPickerFileColors = DSPickerFileUtils.getColors(),
    onClick: () -> Unit,
) {
    DSPickerButtonAction(
        text = text,
        onClick = onClick,
        isEnabled = isEnabled,
        icon = R.drawable.ic_ds_action_addition,
        borderType = DSButtonBorderType.Dotted,
        contentPadding = PaddingValues(
            vertical = dimension.small,
            horizontal = dimension.medium - (dimension.smalled * 2),
        ),
        colors = DSPickerButtonActionUtils.getColors(
            content = colors.primary,
            background = colors.primary.alphaLow,
            contentDisabled = colors.primaryDisabled.alphaHigh,
            backgroundDisabled = colors.primaryDisabled.alphaSemiLow,
        ),
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewDSPickerButtonOrderName() {
    val isEnabled = true
    val onClick = { }
    Column(
        verticalArrangement = Arrangement.spacedBy(dimension.smalled)
    ) {
        DSPickerButtonAdd(isEnabled = isEnabled, onClick = onClick)
        DSPickerButtonSort(isEnabled = isEnabled, onClick = onClick)
        DSPickerButtonSort(isEnabled = !isEnabled, onClick = onClick)
        DSPickerFileUniqueButtonAdd(text = "Agregar archivos", isEnabled = isEnabled, onClick = onClick)
        DSPickerButtonChange(text = "Cambiar", onClick = onClick)
    }
}
