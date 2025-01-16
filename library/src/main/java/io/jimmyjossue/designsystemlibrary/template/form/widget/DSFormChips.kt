package io.jimmyjossue.designsystemlibrary.template.form.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.jimmyjossue.designsystemlibrary.components.selectors.chips.DSChips
import io.jimmyjossue.designsystemlibrary.components.selectors.chips.config.DSChipsUtils
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormColors
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormElement
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaLower
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaSemiLow
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.shape
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography

@Composable
internal fun DSFormChips(
    model: DSFormElement.InputChips,
    colors: DSFormColors,
    onChangeValue: (String, Any) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimension.small)
    ) {
        DSFormLabel(
            value = model.label.orEmpty(),
            style = typography.body,
            colors = colors,
        )

        DSChips(
            chips = when (model.isEnabled) {
                true -> model.options
                false -> model.options.map { it.copy(isEnabled = false) }
            },
            contentShape = shape.smalled,
            config = DSChipsUtils.getConfig(
                maxSelected = model.maxSelected,
                contentPadding = dimension.none
            ),
            colors = DSChipsUtils.getColors(
                typography = colors.typography,
                background = Color.Transparent,
                primary = colors.primary,
                onPrimary = colors.onPrimary,
                onSurface = colors.typography,
                disabled = colors.primaryDisabled.copy(alpha = 0.35f),
                surface = when (colors.background == colors.surface) {
                    true -> colors.typography.alphaLower
                    false -> colors.background.alphaSemiLow
                },
            ),
            onChangeSelectChip = { id, isSelected ->
                val chip = model.options.find { it.id == id }
                if (chip != null) {
                    onChangeValue(model.key, chip.copy(isSelected = isSelected))
                }
            }
        )
        LabelHelper(
            value = model.helper.orEmpty(),
            colors = colors,
        )
    }
}
