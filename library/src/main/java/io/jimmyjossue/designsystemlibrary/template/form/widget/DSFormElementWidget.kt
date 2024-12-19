package io.jimmyjossue.designsystemlibrary.template.form.widget

import androidx.compose.runtime.Composable
import io.jimmyjossue.designsystemlibrary.components.input.config.DSInputImeAction
import io.jimmyjossue.designsystemlibrary.components.selectors.chips.DSChip
import io.jimmyjossue.designsystemlibrary.components.selectors.chips.DSChips
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormColors
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormElement
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormValue
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography

@Composable
internal fun FormElement(
    model: DSFormElement,
    colors: DSFormColors,
    onFocusDown: () -> Unit,
    onChangeValue: (DSFormValue) -> Unit,
) {
    when (model) {
        is DSFormElement.InputText -> DSFormInputText(
            model = model.copy(
                imeAction = model.imeAction ?: DSInputImeAction.Nex(onFocusDown)
            ),
            colors = colors,
            onChangeValue = { key, value ->
                onChangeValue(DSFormValue(key, value))
            }
        )

        is DSFormElement.ToggleSwitch -> DSFormToggleSwitch(
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

        is DSFormElement.InputChips -> DSFormChips(
            model = model,
            colors = colors,
            onChangeValue = { key, value ->
                onChangeValue(DSFormValue(key, value))
            }
        )
    }
}
