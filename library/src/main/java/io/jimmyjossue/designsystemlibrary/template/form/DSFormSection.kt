package io.jimmyjossue.designsystemlibrary.template.form

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    withBackground: Boolean = false,
    colors: DSFormColors = DSFormUtils.getColors(),
    contentPadding: Dp = DSFormUtils.getConfig().paddingVertical,
    space: Dp = DSFormUtils.getConfig().separationSpace,
    onFocusDown: () -> Unit,
    onChangeValue: (DSFormValue) -> Unit,
) {
    if (elements.isNotEmpty()) {
        Column(
            modifier = Modifier
                .background(
                    shape = shape.small,
                    color = when (withBackground) {
                        true -> colors.surface
                        false -> Color.Transparent
                    }
                )
                .padding(
                    paddingValues = when (withBackground && colors.background != colors.surface) {
                        true -> PaddingValues(all = contentPadding)
                        false -> PaddingValues(bottom = contentPadding)
                    }
                )
                .animateContentSize(),
            verticalArrangement = Arrangement.spacedBy(space = space),
            content = {
                elements.forEach {
                    if (it.isVisible) {
                        FormElement(
                            model = it,
                            colors = colors,
                            onChangeValue = onChangeValue,
                            onFocusDown = onFocusDown,
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
    val selectedState = remember { mutableStateOf(false) }
    val onChangeValue: (DSFormValue) -> Unit = { selectedState.value = !selectedState.value }
    val onChangeFocus: () -> Unit = { }
    val elements = listOf(
        DSFormElement.LabelBody(value = "LabelBody"),
        DSFormElement.LabelCaption(value = "LabelCaption"),
        DSFormElement.ToggleSwitch(
            key = "ToggleSwitch",
            displayName = "",
            isSelected = selectedState.value
        )
    )

    Column(
        modifier = Modifier.padding(horizontal = h, vertical = v),
        verticalArrangement = Arrangement.spacedBy(space = separationSpace)
    ) {
        FormSection(elements = elements, onChangeValue = onChangeValue, onFocusDown = onChangeFocus)
        FormSection(elements = elements, onChangeValue = onChangeValue, onFocusDown = onChangeFocus)
        FormSection(elements = elements, onChangeValue = onChangeValue, onFocusDown = onChangeFocus)
        FormSection(elements = elements, onChangeValue = onChangeValue, onFocusDown = onChangeFocus)
    }
}
