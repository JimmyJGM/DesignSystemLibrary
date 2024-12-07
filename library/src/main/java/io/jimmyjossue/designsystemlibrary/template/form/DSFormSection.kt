package io.jimmyjossue.designsystemlibrary.template.form

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormColors
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormElement
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormValue
import io.jimmyjossue.designsystemlibrary.template.form.widget.FormElement
import io.jimmyjossue.designsystemlibrary.theme.catalog.shape

@Composable
internal fun FormSection(
    elements: List<DSFormElement>,
    colors: DSFormColors = DSFormUtils.getColors(),
    contentPadding: Dp = DSFormUtils.getConfig().paddingVertical,
    space: Dp = DSFormUtils.getConfig().separationSpace,
    onChangeValue: (DSFormValue) -> Unit,
) {
    if (elements.isNotEmpty()) {
        Column(
            modifier = Modifier
                .background(color = colors.surface, shape = shape.small)
                .padding(all = contentPadding)
                .animateContentSize(),
            verticalArrangement = Arrangement.spacedBy(space = space),
            content = {
                elements.forEach {
                    if (it.isVisible) {
                        FormElement(
                            model = it,
                            colors = colors,
                            onChangeValue = onChangeValue,
                        )
                    }
                }
            }
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFF1F1F1
)
@Composable
private fun PreviewFormSection() {
    val h = DSFormUtils.getConfig().paddingHorizontal
    val v = DSFormUtils.getConfig().paddingVertical
    val separationSpace = DSFormUtils.getConfig().separationSpace
    val onChangeValue: (DSFormValue) -> Unit = { }
    val elements = listOf(
        DSFormElement.LabelBody(value = "LabelBody"),
        DSFormElement.LabelCaption(value = "LabelCaption"),
        DSFormElement.ToggleSwitch(key = "ToggleSwitch", displayName = "")
    )

    Column(
        modifier = Modifier.padding(horizontal = h, vertical = v),
        verticalArrangement = Arrangement.spacedBy(space = separationSpace)
    ) {
        FormSection(elements = elements, onChangeValue = onChangeValue)
        FormSection(elements = elements, onChangeValue = onChangeValue)
        FormSection(elements = elements, onChangeValue = onChangeValue)
        FormSection(elements = elements, onChangeValue = onChangeValue)
    }
}
