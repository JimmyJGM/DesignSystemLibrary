package io.jimmyjossue.designsystemlibrary.template.form.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.jimmyjossue.designsystemlibrary.components.input.DSInputDropdown
import io.jimmyjossue.designsystemlibrary.template.form.DSFormUtils.toInputColors
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormColors
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormElement
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension

private data class DSFormInputDropdownValue(
    val data: String
): DSInputDropdown<DSFormInputDropdownValue> {
    override fun display() = data
    override fun value() = this
}

private fun String?.toInputValue() = this?.let { DSFormInputDropdownValue(data = it) }
private fun  List<String>.toInputValues() = mapNotNull { it.toInputValue() }

@Composable
internal fun DSFormInputDropdown(
    model: DSFormElement.InputDropdown,
    colors: DSFormColors,
    onChangeValue: (String, String) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimension.small)
    ) {
        DSInputDropdown(
            modifier = Modifier.fillMaxWidth(),
            value = model.value.toInputValue(),
            options = model.options.toInputValues(),
            placeHolder = model.hint,
            colors = colors.toInputColors(),
            onChangeSelectOption = {
                onChangeValue(model.key, it.data)
            }
        )
        LabelHelper(
            value = model.helper.orEmpty(),
            colors = colors,
        )
    }
}