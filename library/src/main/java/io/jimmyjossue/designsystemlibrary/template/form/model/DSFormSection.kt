package io.jimmyjossue.designsystemlibrary.template.form.model

import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormElement.InputChips
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormElement.InputDropdown
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormElement.InputText
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormElement.LabelBody
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormElement.LabelCaption
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormElement.ToggleSwitch

data class DSFormSection(
    val title: String? = null,
    val description: String? = null,
    val elements: List<DSFormElement>,
    val withBackground: Boolean = true,
)

fun List<DSFormSection>.getValues(): List<DSFormValue> {
    return flatMap { section ->
        section.elements.mapNotNull { element ->
            when (element) {
                is InputText -> DSFormValue(element.key, element.value,)
                is InputDropdown -> DSFormValue(element.key, element.value,)
                is ToggleSwitch -> DSFormValue(element.key, element.isSelected,)
                is LabelCaption -> null
                is LabelBody -> null
                is InputChips -> DSFormValue(element.key, element.options.filter { it.isSelected })
                is DSFormElement.PickerImage -> DSFormValue(element.key, "element.")
            }
        }
    }
}
