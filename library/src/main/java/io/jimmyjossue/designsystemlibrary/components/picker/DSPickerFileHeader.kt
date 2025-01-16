package io.jimmyjossue.designsystemlibrary.components.picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.jimmyjossue.designsystemlibrary.components.picker.model.DSPickerFileColors
import io.jimmyjossue.designsystemlibrary.components.picker.model.DSPickerFileConfig
import io.jimmyjossue.designsystemlibrary.components.picker.model.DSPickerFilesSortType
import io.jimmyjossue.designsystemlibrary.components.separator.DSSpacerFilled
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography

@Composable
internal fun DSPickerFileHeader(
    title: String? = null,
    subtitle: String? = null,
    isNotEmptyList: Boolean = true,
    isEnabledButtonAdd: Boolean = true,
    isEnabledButtonDelete: Boolean = true,
    filesSortState: DSPickerFilesSortType = DSPickerFilesSortType.Ascending,
    colors: DSPickerFileColors = DSPickerFileUtils.getColors(),
    config: DSPickerFileConfig = DSPickerFileUtils.getConfig(),
    onAddItem: () -> Unit,
    onSortItems: () -> Unit,
    onDeleteAllItems: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(config.separationElements)
    ) {
        if (!title.isNullOrBlank() || !subtitle.isNullOrBlank()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimension.smalled * 2)
            ) {
                if (!title.isNullOrBlank()) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = title,
                        style = typography.body,
                        color = colors.typography
                    )
                }
                if (!subtitle.isNullOrBlank()) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = subtitle,
                        style = typography.caption,
                        color = colors.typography.alphaMedium
                    )
                }
            }
        }
        if (isNotEmptyList) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimension.small)
            ) {
                DSPickerButtonSort(
                    colors = colors,
                    state = filesSortState,
                    onClick = onSortItems,
                )
                DSSpacerFilled()
                DSPickerButtonAdd(
                    colors = colors,
                    isEnabled = isEnabledButtonAdd,
                    onClick = onAddItem
                )
                DSPickerButtonDelete(
                    text = config.deleteButtonText,
                    colors = colors,
                    isEnabled = isEnabledButtonDelete,
                    onClick = onDeleteAllItems
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun PreviewDSPickerFileHeader() {
    DSPickerFileHeader(
        title = "Agregar archivos para enviar",
        subtitle = "Los archivos enviados no se compartiran con nadie más ni se usaran para nada que no se autorice por tí",
        onAddItem = {},
        onDeleteAllItems = { },
        onSortItems = { }
    )
}
