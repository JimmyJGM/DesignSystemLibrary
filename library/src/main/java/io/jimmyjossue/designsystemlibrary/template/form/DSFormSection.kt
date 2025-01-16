package io.jimmyjossue.designsystemlibrary.template.form

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import io.jimmyjossue.designsystemlibrary.components.selectors.chips.staticChips
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormColors
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormElement
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormSection
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormValue
import io.jimmyjossue.designsystemlibrary.template.form.widget.FormElement
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaHigh
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.shape
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography
import io.jimmyjossue.designsystemlibrary.utils.onClick

@Composable
internal fun FormSection(
    modifier: Modifier,
    model: DSFormSection,
    colors: DSFormColors,
    paddingHorizontal: Dp,
    elementsContentPadding: Dp,
    elementsSpace: Dp,
    onFocusDown: () -> Unit,
    onChangeValue: (DSFormValue) -> Unit,
) {
    Column(
        modifier = modifier.padding(
            start = paddingHorizontal, end = paddingHorizontal, bottom = dimension.smalled
        ),
    ) {
        FormLabel(
            value = model.title,
            style = typography.body,
            colors = colors.copy(typography = colors.typography.alphaHigh),
            modifier = Modifier.padding(
                bottom = when (model.description.isNullOrBlank()) {
                    true -> dimension.small
                    false -> dimension.smalled
                }
            ),
        )
        FormLabel(
            value = model.description,
            style = typography.caption,
            colors = colors.copy(typography = colors.typography.alphaMedium),
            modifier = Modifier.padding(bottom = dimension.small),
        )
        FormSectionElement(
            elements = model.elements,
            colors = colors,
            space = elementsSpace,
            withBackground = model.withBackground,
            contentPadding = elementsContentPadding,
            onChangeValue = onChangeValue,
            onFocusDown = onFocusDown,
        )
    }
}

@Composable
internal fun FormSectionElement(
    elements: List<DSFormElement>,
    withBackground: Boolean = false,
    colors: DSFormColors = DSFormUtils.getColors(),
    contentPadding: Dp = DSFormUtils.getConfig().paddingElements,
    space: Dp = DSFormUtils.getConfig().spaceElements,
    onFocusDown: () -> Unit,
    onChangeValue: (DSFormValue) -> Unit,
) {
    if (elements.isNotEmpty()) {
        Column(
            modifier = Modifier
                .background(
                    shape = shape.medium,
                    color = when (withBackground) {
                        true -> colors.surface
                        false -> Color.Transparent
                    }
                )
                .padding(
                    paddingValues = when (withBackground && colors.background != colors.surface) {
                        true -> PaddingValues(all = contentPadding)
                        false -> PaddingValues()
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
private const val empty = ""
private val colors @Composable get() = DSFormUtils.getColors()
private val config @Composable get() = DSFormUtils.getConfig(
    paddingForm = dimension.medium,
    paddingElements = dimension.small+ dimension.smalled,
    spaceElement = dimension.medium,
    spaceSection = dimension.small,
)

@Preview(
    showBackground = true,
    backgroundColor = 0xFFF1F1F1,
    heightDp = 1200
)
@Composable
private fun PreviewFormSection() {
    val helper = "Este valor es requerido"
    val onChangeValue: (DSFormValue) -> Unit = { }
    val onChangeFocus: () -> Unit = onClick
    val chipsOption = staticChips.map { it.copy(isSelected = it.text == "Seleccionado") }
    val elements = listOf(
        DSFormElement.InputChips(empty, empty, helper = helper, options = chipsOption, label = "Categorías",),
        DSFormElement.InputText(empty, empty, helper = null, value = "Escribe aqui",),
        DSFormElement.ToggleSwitch(empty, empty, helper = helper, label = "Activalo si quieres continuar accediendo a todas las funciones ")
    )
    val sectionModel = DSFormSection(
        title = "Titulo de la sección",
        description = "Descricción sobre para que es esta sección,",
        elements = elements,
        withBackground = true,
    )

    Column(
        modifier = Modifier.background(color = colors.background),
        verticalArrangement = Arrangement.spacedBy(space = config.spaceSections)
    ) {
        FormSection(
            modifier = Modifier.padding(top = config.paddingForm),
            model = sectionModel,
            colors = colors,
            elementsContentPadding = config.paddingElements,
            paddingHorizontal = config.paddingForm,
            elementsSpace = config.spaceElements,
            onFocusDown = onChangeFocus,
            onChangeValue = onChangeValue,
        )
        FormSection(
            modifier = Modifier,
            model = DSFormSection(elements =  elements.shuffled()),
            colors = colors,
            elementsContentPadding = config.paddingElements,
            paddingHorizontal = config.paddingForm,
            elementsSpace = config.spaceElements,
            onFocusDown = onChangeFocus,
            onChangeValue = onChangeValue,
        )
        FormSection(
            modifier = Modifier,
            model = DSFormSection(elements =  elements.subList(1, 3)),
            colors = colors,
            elementsContentPadding = config.paddingElements,
            paddingHorizontal = config.paddingForm,
            elementsSpace = config.spaceElements,
            onFocusDown = onChangeFocus,
            onChangeValue = onChangeValue,
        )
    }
}
