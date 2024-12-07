package io.jimmyjossue.designsystemlibrary.template.form.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.jimmyjossue.designsystemlibrary.components.input.DSInputConfig
import io.jimmyjossue.designsystemlibrary.components.input.DSInputText
import io.jimmyjossue.designsystemlibrary.components.input.DSInputUtils
import io.jimmyjossue.designsystemlibrary.template.form.DSFormUtils.toInputColors
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormColors
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormElement
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension

@Composable
internal fun DSFormInputText(
    model: DSFormElement.InputText,
    colors: DSFormColors,
    onChangeValue: (String, String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimension.small)
    ) {
        DSInputText(
            modifier = Modifier.fillMaxWidth(),
            value = model.value,
            colors = colors.toInputColors(),
            config = DSInputConfig(
                placeholder = model.hint,
                label = model.label,
                maxLines = model.maxLines,
                isSingleLine = model.isSingleLine,
                isReadOnly = model.isReadOnly,
            ),
            onChangeValue = {
                onChangeValue(model.key, it)
            }
        )
        LabelHelper(
            value = model.helper.orEmpty(),
            colors = colors,
        )
    }
}
