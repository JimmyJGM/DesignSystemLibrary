package io.jimmyjossue.designsystemlibrary.template.form.widget

import androidx.compose.runtime.Composable
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormColors
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormElement
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormValue
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography

@Composable
internal fun FormElement(
    model: DSFormElement,
    colors: DSFormColors,
    onChangeValue: (DSFormValue) -> Unit,
) {
    when (model) {
        is DSFormElement.ToggleSwitch -> DSFormToggleSwitch(
            model = model,
            colors = colors,
            onChangeValue = { key, value ->
                onChangeValue(DSFormValue(key, value))
            }
        )

        is DSFormElement.InputText -> DSFormInputText(
            model = model,
            colors = colors,
            onChangeValue = { key, value ->
                onChangeValue(DSFormValue(key, value))
            }
        )

        is DSFormElement.LabelBody -> DSFormLabel(
            value = model.value,
            style = typography.body,
            align = model.align,
            colors = colors,
        )

        is DSFormElement.LabelCaption -> DSFormLabel(
            value = model.value,
            style = typography.caption,
            align = model.align,
            colors = colors,
        )

        is DSFormElement.InputDropdown -> DSFormInputDropdown(
            model = model,
            colors = colors,
            onChangeValue = { key, value ->
                onChangeValue(DSFormValue(key, value))
            }
        )
    }
}
